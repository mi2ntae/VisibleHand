package com.it.vh.dict.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "dictionary")
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

    @Enumerated(EnumType.STRING)
    private DictionaryType type;
}
