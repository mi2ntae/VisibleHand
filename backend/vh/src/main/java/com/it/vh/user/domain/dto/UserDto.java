package com.it.vh.user.domain.dto;

import com.it.vh.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Builder
public class UserDto {
    private Long userId;
    private String nickname;
    private String statusMsg;
    private String profileImg;
    private String snsEmail;

    public static UserDto from(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .statusMsg(user.getStatusMsg())
                .profileImg(user.getProfileImg())
                .snsEmail(user.getSnsEmail())
                .build();
    }
}
