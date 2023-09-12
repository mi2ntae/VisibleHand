package com.it.vh.article.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity(name = "cloud_article")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloudArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordNewsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(nullable = false)
    private double score;

    @Column(nullable = false, length = 20)
    private String word;
}
