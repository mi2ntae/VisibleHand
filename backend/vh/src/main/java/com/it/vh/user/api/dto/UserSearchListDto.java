package com.it.vh.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchListDto {
    private long userId;
    private String userName;
    private String statusMsg;
    private String imageUrl;
    private int isFollow;
}
