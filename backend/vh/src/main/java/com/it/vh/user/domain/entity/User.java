package com.it.vh.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Data
@Entity
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

}
