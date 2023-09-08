package com.it.vh.article.domain.repository;

import com.it.vh.article.domain.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByArticle_ArticleIdAndUser_UserId(Long articleId, Long userId);
}
