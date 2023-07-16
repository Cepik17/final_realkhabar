package kz.bitlab.realKhabar.realKhabar.services;

import kz.bitlab.realKhabar.realKhabar.dtos.*;
import kz.bitlab.realKhabar.realKhabar.models.Article;
import kz.bitlab.realKhabar.realKhabar.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

    ArticleView addNewArticle(ArticleCreate articleCreate);

    List<ArticleView> getAllArticles();

    List<ArticleView> findArticlesByUserId(Long userId);

    ArticleView findByNewsOfTheDayIsTrue();

    ArticleView getArticleById(Long articleId);

    ArticleView updateArticle(ArticleUpdate articleUpdate);

    List<CategoryDto> getCategoriesByArticleId(Long articleId);

    List<CommentDto> getCommentsByArticleId(Long article);

    List<ArticleView> getArticlesByCategory(Long id);

    List<ArticleView> getAllByAuthorId(Long authorId);

    List<ArticleView> getLastArticles();

    void deleteArticle(Long articleId);
}
