package com.it.vh.user.domain.entity;

import com.it.vh.user.api.dto.StreakResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedNativeQueries(
        @NamedNativeQuery(
                name = "findStreaByUserId",
                query = "SELECT DATE(s.create_at) as createAt, count(s.solved_id) as weight " +
                        "FROM solved_quiz s " +
                        "WHERE s.correct = true " +
                        "group by DATE(s.create_at)",
                resultSetMapping = "findStreaByUserId"
        )
)

@SqlResultSetMapping(
        name = "findStreaByUserId",
        classes = @ConstructorResult(
                targetClass = StreakResDto.class,
                columns = {
                        @ColumnResult(name = "createAt", type = LocalDate.class),
                        @ColumnResult(name = "weight", type = Integer.class),
                }
        )
)

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

}
