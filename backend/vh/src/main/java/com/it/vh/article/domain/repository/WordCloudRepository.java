package com.it.vh.article.domain.repository;

import com.it.vh.article.domain.entity.Cloud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WordCloudRepository extends JpaRepository<Cloud, Long> {
    @Query(value = "select * from cloud where kind = :kind and Date(issue_date) = :selectedDate",nativeQuery = true)
    Optional<List<Cloud>> findByArticleKindAndIssueDate(@Param("selectedDate") String date, @Param("kind") String kind);
}
