package com.it.vh.article.domain.entity;

import com.it.vh.article.api.dto.response.RankRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "getArticleRanking",
                query = "SELECT IFNULL(a.article_id, -1) AS articleId, a.title, a.company, a.issue_date AS issueDate, thumbnail " +
                        "FROM Article a " +
                        "JOIN (SELECT f.article_id AS articleId, COUNT(f.feed_id) AS feedCount FROM Feed f " +
                        "WHERE f.create_at >= NOW() - INTERVAL 1 HOUR " +
                        "GROUP BY articleId ORDER BY feedCount DESC LIMIT 5) " +
                        "subquery ON a.article_id = subquery.articleId " +
                        "ORDER BY subquery.feedCount DESC",
                resultSetMapping = "articleRankingDto"
        ),
})
@SqlResultSetMapping(
        name = "articleRankingDto",
        classes = @ConstructorResult(
                targetClass = RankRes.class,
                columns = {
                        @ColumnResult(name = "articleId", type = Long.class),
                        @ColumnResult(name = "title", type = String.class),
                        @ColumnResult(name = "company", type = String.class),
                        @ColumnResult(name = "issueDate", type = String.class),
                        @ColumnResult(name = "thumbnail", type = String.class),
                }
        )
)
@Data
@Entity(name="article")
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
