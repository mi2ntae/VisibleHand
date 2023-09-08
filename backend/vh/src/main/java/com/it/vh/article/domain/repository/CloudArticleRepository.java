package com.it.vh.article.domain.repository;

import com.it.vh.article.domain.entity.CloudArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CloudArticleRepository extends JpaRepository<CloudArticle, Long> {
}
