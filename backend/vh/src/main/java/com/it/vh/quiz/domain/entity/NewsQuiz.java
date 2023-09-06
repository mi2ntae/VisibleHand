package com.it.vh.quiz.domain.entity;

import com.it.vh.article.domain.entity.Article;
import com.it.vh.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "news_quiz")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsquizId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(nullable = false, length = 50)
    private String answer;
}
