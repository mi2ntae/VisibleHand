package com.it.vh.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.vh.common.util.jwt.JwtAuthenticationFilter;
import com.it.vh.common.util.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .headers()
            .frameOptions()
            .sameOrigin().and()
            .cors().and()
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            //TODO: 허용할 주소 추가
            .authorizeRequests()
//            .anyRequest().authenticated()
            .anyRequest().permitAll()
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, objectMapper),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}