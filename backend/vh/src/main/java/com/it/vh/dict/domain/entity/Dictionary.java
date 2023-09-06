package com.it.vh.dict.domain.entity;

import com.it.vh.article.domain.ArticleKind;
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
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordId;

    @Column(nullable = false, length = 80)
    private String word;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String meaning;

}
