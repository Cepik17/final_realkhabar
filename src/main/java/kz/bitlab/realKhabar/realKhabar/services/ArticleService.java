package kz.bitlab.realKhabar.realKhabar.services;

import kz.bitlab.realKhabar.realKhabar.dtos.ArticleCreate;
import kz.bitlab.realKhabar.realKhabar.dtos.ArticleView;
import kz.bitlab.realKhabar.realKhabar.dtos.UserView;
import kz.bitlab.realKhabar.realKhabar.models.Article;

import java.util.List;

public interface ArticleService {

    ArticleView addNewArticle(ArticleCreate articleCreate);

    List<ArticleView> getAllUserArticles(UserView userView);

    List<ArticleView> findArticlesByUserId(Long userId);

    ArticleView findByNewsOfTheDayIsTrue();
}
