package com.it.vh.user.api.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NicknameResDto {
    private int isDuplicated;
}
