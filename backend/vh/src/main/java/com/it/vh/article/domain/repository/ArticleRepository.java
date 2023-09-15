package com.it.vh.article.domain.repository;

import com.it.vh.article.api.dto.response.RankRes;
import com.it.vh.article.domain.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "SELECT a.* FROM article a INNER JOIN cloud_article ca ON a.article_id = ca.article_id " +
            "WHERE a.kind = :kind AND DATE(a.issue_date) = :date " +
            "AND ca.word = :word ORDER BY ca.score DESC", nativeQuery = true)
    Slice<Article> findSliceByConditions(@Param("kind") String kind, @Param("date") String date, @Param("word") String word, Pageable pageable);

    @Query(name = "getArticleRanking", nativeQuery = true)
    List<RankRes> getArticleRanking();
}
