package com.it.vh.article.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 255)
    private String thumbnail;

    @Column(nullable = false)
    private LocalDateTime issueDate;

    @Column(nullable = false, length = 50)
    private String company;

    @Column(nullable = false, length = 20)
    private String editor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArticleKind kind;

    @Column(nullable = false, length = 255)
    private String url;

    @Column(nullable = false, length = 255)
    private String summary;
}
