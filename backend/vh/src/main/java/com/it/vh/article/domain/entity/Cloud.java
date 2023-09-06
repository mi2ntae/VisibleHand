package com.it.vh.article.domain.entity;

import com.it.vh.article.domain.ArticleKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cloud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordId;

    @Column(nullable = false, length = 20)
    private String word;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int count;

    @Column(nullable = false)
    private LocalDateTime issueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArticleKind kind;
}
