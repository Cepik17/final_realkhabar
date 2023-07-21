package kz.bitlab.realKhabar.realKhabar.services;

import kz.bitlab.realKhabar.realKhabar.dtos.*;
import kz.bitlab.realKhabar.realKhabar.dtos.SearchQuery;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ArticleService {

    ArticleView addNewArticle(ArticleCreate articleCreate);

    Page<ArticleView> getAllArticles(int page, int size);

    List<ArticleView> getArticlesList();

    List<ArticleView> findArticlesByUserId(Long userId);

    ArticleView findByNewsOfTheDayIsTrue();

    ArticleView getArticleById(Long articleId);

    ArticleView updateArticle(ArticleUpdate articleUpdate);

    Page<ArticleView> getArticlesByCategory(Long categoryId, int page, int size);

    Page<ArticleView> getArticlesByAuthorId(Long authorId, int page, int size);

    List<ArticleView> getLastArticles();

    List<ArticleView> searchArticle(SearchQuery query);

    void deleteArticle(Long articleId);
}
