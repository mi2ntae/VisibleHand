package com.it.vh.article.domain.repository;

import com.it.vh.article.api.dto.ScrapListRes;
import com.it.vh.article.domain.entity.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    @Query(value = "SELECT new com.it.vh.article.api.dto.ScrapListRes(s.scrapId, a.articleId, a.thumbnail, a.title) FROM Scrap s JOIN Article a ON s.article.articleId = a.articleId WHERE a.title like %:title% AND s.user.userId = :userId")
    Page<ScrapListRes> findScrapListByUserIdAndTitle(long userId, String title, Pageable page);
}
