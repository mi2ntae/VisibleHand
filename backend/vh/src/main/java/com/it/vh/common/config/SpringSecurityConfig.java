package com.it.vh.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.vh.common.util.jwt.JwtTokenProvider;
import com.it.vh.common.util.jwt.filter.JwtAccessDeniedHandler;
import com.it.vh.common.util.jwt.filter.JwtAuthenticationEntryPoint;
import com.it.vh.user.service.UserRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                //TODO: 허용할 주소 추가
                .authorizeRequests()
                .regexMatchers("/api/user/profile/\\d+").authenticated()
                .regexMatchers(HttpMethod.DELETE, "/api/user/\\d+").authenticated()
                .antMatchers(HttpMethod.GET, "/api/user/auth/nickname").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user/profile").permitAll()
                .anyRequest().permitAll()
//                .anyRequest().authenticated()

                .and()
                .exceptionHandling()
                .accessDeniedHandler(new JwtAccessDeniedHandler(objectMapper))
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint(objectMapper))
                .and();

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}