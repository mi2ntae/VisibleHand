package com.it.vh.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
public class FollowResDto {

    @NotNull
    long fromId;

    @NotNull
    long toId;
}
