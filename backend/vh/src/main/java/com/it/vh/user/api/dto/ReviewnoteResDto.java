package com.it.vh.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
public class ReviewnoteResDto {
    private String question;
    private String answer;
}
