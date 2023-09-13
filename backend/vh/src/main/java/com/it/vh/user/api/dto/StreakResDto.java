package com.it.vh.user.api.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StreakResDto {
    private LocalDate createAt;
    private int weight;
}
