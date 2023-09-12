package com.it.vh.quiz.domain.entity;

import com.it.vh.common.baseEntity.BaseTimeEntity;
import com.it.vh.dict.domain.entity.Dictionary;
import com.it.vh.feed.api.dto.FeedResDto;
import com.it.vh.user.api.dto.ReviewnoteResDto;
import com.it.vh.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "solved_quiz")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@NamedNativeQueries(
//        @NamedNativeQuery(
//                name = "findReviewNotesByUserId",
//                query = "SELECT (CASE WHEN s.newsquiz_id is NULL THEN d.meaning " +
//                        "ELSE n.question END) as question, " +
//                        "(CASE WHEN s.newsquiz_id is NULL THEN d.word " +
//                        "ELSE n.answer END) as answer " +
//                        "FROM solved_quiz s " +
//                        "RIGHT JOIN news_quiz n ON s.newsquiz_id = n.newsquiz_id " +
//                        "RIGHT JOIN dictionary d ON s.word_id = d.word_id " +
//                        "WHERE s.user_id = :userId AND s.question = 0 " +
//                        "ORDER BY create_at DESC",
//                resultSetMapping = "findReviewNotesByUserId"
//        )
//
//)
//@SqlResultSetMapping(
//        name = "findReviewNotesByUserId",
//        classes = @ConstructorResult(
//                targetClass = ReviewnoteResDto.class,
//                columns = {
//                        @ColumnResult(name = "question", type = String.class),
//                        @ColumnResult(name = "answer", type = String.class),
//                }
//        )
//)
public class SolvedQuiz extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long solvedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "word_id")
    private Dictionary word;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "newsquiz_id")
    private NewsQuiz newsquiz;

    @Column(nullable = false)
    private boolean correct;
}
