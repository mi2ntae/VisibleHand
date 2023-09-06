package com.it.vh.quiz.domain.entity;

import com.it.vh.common.baseEntity.BaseTimeEntity;
import com.it.vh.dict.domain.entity.Dictionary;
import com.it.vh.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "solved_quiz")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolvedQuiz extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long solvedId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "word_id")
    private Dictionary word;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "newsquiz_id")
    private NewsQuiz newsquiz;

    @Column(nullable = false)
    private boolean question;
}
