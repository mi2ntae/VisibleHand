package com.it.vh.user.domain.entity;

import com.it.vh.user.api.dto.StreakResDto;
import com.it.vh.user.api.dto.UserFollowListResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "findStreakByUserId",
                query = "SELECT DATE(s.create_at) as createAt, count(s.solved_id) as weight " +
                        "FROM solved_quiz s " +
                        "WHERE s.correct = true AND s.user_id = :userId "+
                        "group by DATE(s.create_at)",
                resultSetMapping = "findStreakByUserId"
        ),
        @NamedNativeQuery(
                name = "findRecommendUserByUserId",
                query = "SELECT user_id AS userId, nickname AS userName, status_msg AS statusMsg, IFNULL(profile_img, 'https://visiblehand-bucket.s3.ap-northeast-2.amazonaws.com/user_default.png') AS imageUrl " +
                        "FROM User " +
                        "WHERE user_id IN (" +
                        "SELECT to_id FROM Follow " +
                        "WHERE to_id NOT IN (SELECT to_id FROM Follow WHERE from_id = :userId) " +
                        "AND from_id IN (SELECT to_id FROM Follow WHERE from_id = :userId) " +
                        "GROUP BY to_id ORDER BY COUNT(*)" +
                        ") OR user_id IN (SELECT to_id FROM Follow GROUP BY to_id ORDER BY count(*) DESC) LIMIT 3",
                resultSetMapping = "userFollowListDto"
        ),
        @NamedNativeQuery(
                name = "findUsersByNickname",
                query = "SELECT user_id AS userId, nickname AS userName, status_msg AS statusMsg, IFNULL(profile_img, 'https://visiblehand-bucket.s3.ap-northeast-2.amazonaws.com/user_default.png') AS imageUrl " +
                        "FROM User " +
                        "WHERE nickname LIKE :keyword",
                resultSetMapping = "userFollowListDto"
        )
})

@SqlResultSetMapping(
        name = "findStreakByUserId",
        classes = @ConstructorResult(
                targetClass = StreakResDto.class,
                columns = {
                        @ColumnResult(name = "createAt", type = LocalDate.class),
                        @ColumnResult(name = "weight", type = Integer.class),
                }
        )
)

@SqlResultSetMapping(
        name = "userFollowListDto",
        classes = @ConstructorResult(
                targetClass = UserFollowListResDto.class,
                columns = {
                        @ColumnResult(name = "userId", type = Long.class),
                        @ColumnResult(name = "userName", type = String.class),
                        @ColumnResult(name = "statusMsg", type = String.class),
                        @ColumnResult(name = "imageUrl", type = String.class),
                }
        )
)
@Data
@Entity(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 32)
    private String nickname;

    @Column(length = 128)
    @ColumnDefault("''")
    private String statusMsg;

    @Column(length = 255)
    private String profileImg;

    @Column(nullable = false, length = 48)
    private String snsEmail;

    @Column(length = 10)
    private String provider;
}
