package com.it.vh.common.util.jwt;

import static com.it.vh.common.exception.ExceptionList.NON_EXIST_USER_ID;

import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRespository;
import com.it.vh.user.exception.NonExistUserIdException;
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
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("[권한 설정] userId: {}", userId);

        User findUser = userRespository.findById(Long.valueOf(userId)).orElseThrow(
            () -> new NonExistUserIdException(NON_EXIST_USER_ID.getMessage())
        );

        CustomUserDetails userDetails = new CustomUserDetails(findUser);
        log.info("userDetails: {}", userDetails);
        return userDetails;
    }

//    private UserDetails createUserDetails(User user) {
//        return org.springframework.security.core.userdetails.User.builder()
//            .username(user.getUserId().toString())
//            .password(passwordEncoder.encode(user.getUserId().toString()))
//            .roles(user.getNickname())
//            .build();
//    }
}
