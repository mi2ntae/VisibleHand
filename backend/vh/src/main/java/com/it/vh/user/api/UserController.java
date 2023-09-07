package com.it.vh.user.api;

import com.it.vh.user.api.dto.UserProfileRes;
import com.it.vh.user.domain.dto.UserDto;
import com.it.vh.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "유저 API", tags = {"User"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "유저 프로필 조회", notes = "유저 프로필 조회")
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileRes> getUserProfileByUserId(@PathVariable long userId) {
        UserDto userDto = userService.getUserProfileByUserId(userId);
        return ResponseEntity.ok().body(UserProfileRes.from(userDto));
    }
}