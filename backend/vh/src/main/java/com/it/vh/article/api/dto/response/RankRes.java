package com.it.vh.article.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankRes {
    private long articleId;
    private String title;
    private String company;
    private String issueDate;
    private String thumbnail;
}
