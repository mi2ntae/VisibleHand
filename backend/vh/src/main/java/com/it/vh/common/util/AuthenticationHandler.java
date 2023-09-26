package com.it.vh.common.util;

import com.it.vh.common.exception.AuthenticationAccessForbiddenException;
import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRespository;
import com.it.vh.user.exception.NonExistUserIdException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationHandler {
    private final UserRespository userRespository;

    public void checkUserAuthenticate(long userId) throws NonExistUserIdException, AuthenticationAccessForbiddenException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        long loginId = Long.parseLong(auth.getName());
        Optional<User> loginUser = userRespository.findUserByUserId(loginId);
        if(!loginUser.isPresent()) throw new NonExistUserIdException();
        if(loginUser.get().getUserId() != userId) throw new AuthenticationAccessForbiddenException();
    }

    public long getLoginUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(auth.getName());
    }

}
