package com.it.vh.feed.domain.entity;

import com.it.vh.article.domain.entity.Article;
import com.it.vh.common.baseEntity.BaseTimeEntity;
import com.it.vh.feed.api.dto.FeedResDto;
import com.it.vh.user.domain.entity.User;
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
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword",
                query = "SELECT F.feed_id as feedId, F.content, A.article_id as articleId, A.title, F.create_at as createAt, " +
                        "       IF((select count(*) from Heart " +
                        "       where Heart.user_id = :myId and Heart.feed_id = F.feed_id) <= 0, false, true) as isHeart, " +
                        "       (select count(*) from Heart where Heart.feed_id = F.feed_id) as heart " +
                        "       from Feed as F " +
                        "       join Article as A " +
                        "       on F.article_id = A.article_id " +
                        "       where A.title like :keyword and F.share >= :isOther and F.user_id = :userId " +
                        "       order by createAt desc",
                resultSetMapping = "findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword"
        ),
        @NamedNativeQuery(
                name = "findFeedsAndHeartAndIsHeartByUserIdWhereContentIsKeyword",
                query = "SELECT F.feed_id as feedId, F.content, A.article_id as articleId, A.title, F.create_at as createAt, " +
                        "       IF((select count(*) from Heart " +
                        "       where Heart.user_id = :myId and Heart.feed_id = F.feed_id) <= 0, false, true) as isHeart, " +
                        "       (select count(*) from Heart where Heart.feed_id = F.feed_id) as heart " +
                        "       from Feed as F " +
                        "       join Article as A " +
                        "       on F.article_id = A.article_id " +
                        "       where F.content like :keyword and F.share >= :isOther and F.user_id = :userId " +
                        "       order by createAt desc",

                resultSetMapping = "findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword"
        ),
        @NamedNativeQuery(
                name = "findFeedsByFollowingUserIdAndContent",
                query = "SELECT F.feed_id as feedId, F.content, A.article_id as articleId, A.title, F.create_at as createAt, " +
                        "       IF((select count(*) from Heart " +
                        "       where Heart.user_id = :userId and Heart.feed_id = F.feed_id) <= 0, false, true) as isHeart, " +
                        "       (select count(*) from Heart where Heart.feed_id = F.feed_id) as heart " +
                        "       from Feed as F " +
                        "       join Article as A " +
                        "       on F.article_id = A.article_id " +
                        "       join Follow as FF on F.user_id = FF.to_id " +
                        "       where F.content like :keyword and F.share >= 1 and FF.from_id = :userId " +
                        "       order by createAt desc",
                resultSetMapping = "findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword"
        ),
        @NamedNativeQuery(
                name = "findFeedsByFollowingUserIdAndTitle",
                query = "SELECT F.feed_id as feedId, F.content, A.article_id as articleId, A.title, F.create_at as createAt, " +
                        "       IF((select count(*) from Heart " +
                        "       where Heart.user_id = :userId and Heart.feed_id = F.feed_id) <= 0, false, true) as isHeart, " +
                        "       (select count(*) from Heart where Heart.feed_id = F.feed_id) as heart " +
                        "       from Feed as F " +
                        "       join Article as A " +
                        "       on F.article_id = A.article_id " +
                        "       join Follow as FF on F.user_id = FF.to_id " +
                        "       where A.title like :keyword and F.share >= 1 and FF.from_id = :userId " +
                        "       order by createAt desc",
                resultSetMapping = "findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword"
        ),
})
@SqlResultSetMapping(
        name = "findFeedsAndHeartAndIsHeartByUserIdWhereTitleIsKeyword",
        classes = @ConstructorResult(
                targetClass = FeedResDto.class,
                columns = {
                        @ColumnResult(name = "feedId", type = Integer.class),
                        @ColumnResult(name = "content", type = String.class),
                        @ColumnResult(name = "heart", type = Integer.class),
                        @ColumnResult(name = "isHeart", type = Integer.class),
                        @ColumnResult(name = "articleId", type = Long.class),
                        @ColumnResult(name = "title", type = String.class),
                        @ColumnResult(name = "createAt", type = LocalDateTime.class)
                }
        )
)

public class Feed extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private boolean share;

}
