package kz.bitlab.realKhabar.realKhabar.services;

import kz.bitlab.realKhabar.realKhabar.dtos.*;
import kz.bitlab.realKhabar.realKhabar.models.Article;
import kz.bitlab.realKhabar.realKhabar.models.Category;

import java.util.List;

public interface ArticleService {

    ArticleView addNewArticle(ArticleCreate articleCreate);

    List<ArticleView> getAllUserArticles(UserView userView);

    List<ArticleView> findArticlesByUserId(Long userId);

    ArticleView findByNewsOfTheDayIsTrue();

    ArticleView getArticleById(Long articleId);

    ArticleView updateArticle(ArticleUpdate articleUpdate);

    List<CategoryDto> getCategoriesByArticleId(Long articleId);

    List<CommentDto> getCommentsByArticleId(Long article);
}
