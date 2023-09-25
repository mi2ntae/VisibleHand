package com.it.vh.article.domain.repository;

import com.it.vh.article.api.dto.ScrapListResDto;
import com.it.vh.article.domain.entity.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    @Query(value = "SELECT new com.it.vh.article.api.dto.ScrapListResDto(s.scrapId, a.articleId, a.thumbnail, a.title) FROM scrap s JOIN article a ON s.article.articleId = a.articleId WHERE a.title like %:title% AND s.user.userId = :userId")
    Page<ScrapListResDto> findScrapListByUserIdAndTitle(long userId, String title, Pageable page);

    Optional<Scrap> findByArticle_ArticleIdAndUser_UserId(Long articleId, Long userId);

    boolean deleteByUser_UserIdAndArticle_ArticleId(long userId, long articleId);
}
