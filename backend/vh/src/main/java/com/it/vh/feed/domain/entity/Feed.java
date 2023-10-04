package com.it.vh.feed.domain.entity;

import com.it.vh.article.domain.entity.Article;
import com.it.vh.common.baseEntity.BaseTimeEntity;
import com.it.vh.feed.api.dto.FeedListOfArticleRes;
import com.it.vh.feed.api.dto.FeedListRes;
import com.it.vh.feed.api.dto.FeedRes;
import com.it.vh.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword",
                query = "SELECT u.user_id AS userId, u.nickname, u.profile_img AS profileImg, F.feed_id as feedId, F.content, A.article_id as articleId, A.title, F.create_at as createAt, A.kind, " +
                        "       IF((select count(*) from heart " +
                        "       where heart.user_id = :myId and heart.feed_id = F.feed_id) <= 0, false, true) as isHeart, " +
                        "       (select count(*) from heart where heart.feed_id = F.feed_id) as heart " +
                        "       from feed as F " +
                        "       join article as A " +
                        "       on F.article_id = A.article_id " +
                        "       JOIN user AS u ON F.user_id = u.user_id " +
                        "       where A.title like :keyword and F.share >= :isOther and F.user_id = :userId " +
                        "       order by createAt desc",
                resultSetMapping = "feedListDto"
        ),
        @NamedNativeQuery(
                name = "findFeedsAndHeartAndIsHeartByUserIdWhereContentIsKeyword",
                query = "SELECT u.user_id AS userId, u.nickname, u.profile_img AS profileImg, F.feed_id as feedId, F.content, A.article_id as articleId, A.title, F.create_at as createAt, A.kind, " +
                        "       IF((select count(*) from heart " +
                        "       where heart.user_id = :myId and heart.feed_id = F.feed_id) <= 0, false, true) as isHeart, " +
                        "       (select count(*) from heart where heart.feed_id = F.feed_id) as heart " +
                        "       from feed as F " +
                        "       join article as A " +
                        "       on F.article_id = A.article_id " +
                        "       JOIN user AS u ON F.user_id = u.user_id " +
                        "       where F.content like :keyword and F.share >= :isOther and F.user_id = :userId " +
                        "       order by createAt desc",

                resultSetMapping = "feedListDto"
        ),
        @NamedNativeQuery(
                name = "findFeedsByContent",
                query = "SELECT u.user_id AS userId, u.nickname, u.profile_img AS profileImg, " +
                        "f.feed_id AS feedId, f.content, f.create_at AS createAt, " +
                        "IFNULL((SELECT COUNT(*) FROM heart AS h WHERE h.feed_id = f.feed_id), 0) AS heart, " +
                        "IF((SELECT COUNT(*) FROM heart AS h WHERE h.user_id = :userId AND h.feed_id = f.feed_id) > 0, 1, 0) AS isHeart, " +
                        "a.article_id AS articleId, a.title, a.kind " +
                        "FROM feed AS f " +
                        "JOIN article AS a ON f.article_id = a.article_id " +
                        "JOIN user AS u ON f.user_id = u.user_id " +
                        "WHERE f.content LIKE :keyword AND f.share > 0 " +
                        "ORDER BY f.create_at DESC",
                resultSetMapping = "feedListDto"
        ),
        @NamedNativeQuery(
                name = "findFeedsByTitle",
                query = "SELECT u.user_id AS userId, u.nickname, u.profile_img AS profileImg, " +
                        "f.feed_id AS feedId, f.content, f.create_at AS createAt, " +
                        "IFNULL((SELECT COUNT(*) FROM heart AS h WHERE h.feed_id = f.feed_id), 0) AS heart, " +
                        "IF((SELECT COUNT(*) FROM heart AS h WHERE h.user_id = :userId AND h.feed_id = f.feed_id) > 0, true, false) AS isHeart, " +
                        "a.article_id AS articleId, a.title " +
                        "FROM feed AS f " +
                        "JOIN article AS a ON f.article_id = a.article_id " +
                        "JOIN user AS u ON f.user_id = u.user_id " +
                        "WHERE a.title LIKE :keyword AND f.share > 0 " +
                        "ORDER BY f.create_at DESC",
                resultSetMapping = "feedListDto"
        ),
        @NamedNativeQuery(
                name = "findFeedsByFollowerUserId",
                query = "SELECT u.user_id AS userId, u.nickname, u.profile_img AS profileImg, " +
                        "f.feed_id AS feedId, f.content, f.create_at AS createAt, " +
                        "IFNULL((SELECT COUNT(*) FROM heart AS h WHERE h.feed_id = f.feed_id), 0) AS heart, " +
                        "IF((SELECT COUNT(*) FROM heart AS h WHERE h.user_id = :userId AND h.feed_id = f.feed_id) > 0, 1, 0) AS isHeart, " +
                        "a.article_id AS articleId, a.title, a.kind " +
                        "FROM feed AS f " +
                        "JOIN article AS a ON f.article_id = a.article_id " +
                        "JOIN follow AS ff ON f.user_id = ff.to_id " +
                        "JOIN user AS u ON f.user_id = u.user_id " +
                        "WHERE f.share > 0 AND ff.from_id = :userId " +
                        "ORDER BY f.create_at DESC",
                resultSetMapping = "feedListDto"
        ),
        @NamedNativeQuery(
                name = "findFeedsByArticle",
                query = "SELECT " +
                        "    u.user_id AS userId " +
                        "    f.feed_id AS feedId, " +
                        "    u.profile_img AS profileImg, " +
                        "    u.nickname," +
                        "    f.content," +
                        "    IFNULL(COUNT(h.feed_id), 0) AS heart, " +
                        "    IF(COUNT(h2.feed_id) > 0, 1, 0) AS isHeart, " +
                        "    f.create_at AS createdAt " +
                        "FROM " +
                        "    feed AS f " +
                        "JOIN " +
                        "    user AS u ON f.user_id = u.user_id " +
                        "LEFT JOIN " +
                        "    heart AS h ON f.feed_id = h.feed_id " +
                        "LEFT JOIN " +
                        "    heart AS h2 ON f.feed_id = h2.feed_id AND h2.user_id = :userId " +
                        "WHERE " +
                        "    f.article_id = :articleId " +
                        "AND f.share = 1 " +
                        "GROUP BY " +
                        "    f.feed_id " +
                        "ORDER BY " +
                        "    f.create_at DESC ",
                resultSetMapping = "findFeedsByArticle"
        ),
})
@SqlResultSetMapping(
        name = "findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword",
        classes = @ConstructorResult(
                targetClass = FeedRes.class,
                columns = {
                        @ColumnResult(name = "feedId", type = Long.class),
                        @ColumnResult(name = "content", type = String.class),
                        @ColumnResult(name = "heart", type = Integer.class),
                        @ColumnResult(name = "isHeart", type = Integer.class),
                        @ColumnResult(name = "articleId", type = Long.class),
                        @ColumnResult(name = "title", type = String.class),
                        @ColumnResult(name = "createAt", type = LocalDateTime.class)
                }
        )
)

@SqlResultSetMapping(
        name = "feedListDto",
        classes = @ConstructorResult(
                targetClass = FeedListRes.class,
                columns = {
                        @ColumnResult(name = "userId", type = Long.class),
                        @ColumnResult(name = "nickname", type = String.class),
                        @ColumnResult(name = "profileImg", type = String.class),
                        @ColumnResult(name = "feedId", type = Long.class),
                        @ColumnResult(name = "content", type = String.class),
                        @ColumnResult(name = "heart", type = Integer.class),
                        @ColumnResult(name = "isHeart", type = Integer.class),
                        @ColumnResult(name = "articleId", type = Long.class),
                        @ColumnResult(name = "title", type = String.class),
                        @ColumnResult(name = "createAt", type = LocalDateTime.class),
                        @ColumnResult(name = "kind", type = String.class),
                }
        )
)

@SqlResultSetMapping(
        name = "findFeedsByArticle",
        classes = @ConstructorResult(
                targetClass = FeedListOfArticleRes.class,
                columns = {
                        @ColumnResult(name = "userId", type = Long.class),
                        @ColumnResult(name = "feedId", type = Long.class),
                        @ColumnResult(name = "profileImg", type = String.class),
                        @ColumnResult(name = "nickname", type = String.class),
                        @ColumnResult(name = "content", type = String.class),
                        @ColumnResult(name = "heart", type = Integer.class),
                        @ColumnResult(name = "isHeart", type = Integer.class),
                        @ColumnResult(name = "createdAt", type = LocalDateTime.class)
                }
        )
)
@Data
@Entity(name = "feed")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feed extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private boolean share;

}
