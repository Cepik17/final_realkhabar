package kz.bitlab.realKhabar.realKhabar.services.impl;

import kz.bitlab.realKhabar.realKhabar.dtos.ArticleCreate;
import kz.bitlab.realKhabar.realKhabar.dtos.ArticleView;
import kz.bitlab.realKhabar.realKhabar.dtos.UserView;
import kz.bitlab.realKhabar.realKhabar.mappers.ArticleMapper;
import kz.bitlab.realKhabar.realKhabar.mappers.UserMapper;
import kz.bitlab.realKhabar.realKhabar.models.Article;
import kz.bitlab.realKhabar.realKhabar.models.Category;
import kz.bitlab.realKhabar.realKhabar.models.User;
import kz.bitlab.realKhabar.realKhabar.repositories.ArticleRepository;
import kz.bitlab.realKhabar.realKhabar.repositories.CategoryRepository;
import kz.bitlab.realKhabar.realKhabar.services.ArticleService;
import kz.bitlab.realKhabar.realKhabar.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service

public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ArticleView addNewArticle(ArticleCreate articleCreate) {
        Article article = articleMapper.toEntity(articleCreate);
        User user = userService.getUserById(article.getAuthor().getId());
        List<Category> categories = categoryRepository.findAllById(articleCreate.getCategoryId());
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
//        String formattedDateTime = LocalDateTime.now().format(formatter);

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        article.setAuthor(user);
        article.setPostTime(now);
        if (articleCreate.isNewsOfTheDay()) {
            articleRepository.resetNewsOfTheDay();
        }
        article.setNewsOfTheDay(articleCreate.isNewsOfTheDay());
        article.setCategories(categories);
        articleRepository.save(article);

        return articleMapper.toView(article);
    }

    @Override
    public List<ArticleView> getAllUserArticles(UserView userView) {
        User user = userMapper.toEntity(userView);
        List<Article> articles = articleRepository.findArticlesByAuthor(user);
        return articleMapper.toViewList(articles);
    }

    @Override
    public List<ArticleView> findArticlesByUserId(Long userId) {
        User user = userService.getUserById(userId);
        List<Article> articles = articleRepository.findArticlesByAuthor(user);
        return articleMapper.toViewList(articles);
    }

    @Override
    public ArticleView findByNewsOfTheDayIsTrue() {
        return articleMapper.toView(articleRepository.findArticleByNewsOfTheDayIsTrue());
    }
}
