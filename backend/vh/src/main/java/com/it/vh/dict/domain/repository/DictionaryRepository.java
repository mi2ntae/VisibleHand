package com.it.vh.dict.domain.repository;

import com.it.vh.dict.domain.entity.Dictionary;
import com.it.vh.dict.domain.entity.DictionaryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long>, JpaSpecificationExecutor<Dictionary> {

    Page<Dictionary> findAll(Pageable pageable);

    @Query(value = "SELECT d.*\n" +
            "FROM dictionary d\n" +
            "left JOIN solved_quiz sq ON d.word_id = sq.word_id AND sq.user_id = :userId\n" +
            "WHERE sq.word_id IS NULL and length(d.word) < 10\n" +
            "order by rand()\n" +
            "limit 1;",nativeQuery = true)
    Optional<List<Dictionary>> findRandomWordQuiz(@Param("userId") Long userId);

    @Query(value = "SELECT d.*\n" +
            "FROM dictionary d\n" +
            "JOIN solved_quiz sq ON d.word_id = sq.word_id\n" +
            "WHERE sq.correct = false AND sq.user_id = :userId\n" +
            "order by rand()\n" +
            "limit 1;",nativeQuery = true)
    Optional<List<Dictionary>> findRandomRetryWordQuiz(@Param("userId") Long userId);

}
