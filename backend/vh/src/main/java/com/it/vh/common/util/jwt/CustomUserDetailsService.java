package com.it.vh.common.util.jwt;

import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRespository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRespository userRespository;

    @Override
    public UserDetails loadUserByUsername(String snsEmail) throws UsernameNotFoundException {
        Optional<User> findUser = userRespository.findBySnsEmail(snsEmail);
        if (findUser.isEmpty()) {
            return null;
        }

        CustomUserDetails userDetails = new CustomUserDetails(findUser.get());
        log.info("custom: {}", userDetails);
        return userDetails;
    }
}
