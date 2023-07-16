package kz.bitlab.realKhabar.realKhabar.services.impl;

import kz.bitlab.realKhabar.realKhabar.dtos.*;
import kz.bitlab.realKhabar.realKhabar.mappers.ArticleMapper;
import kz.bitlab.realKhabar.realKhabar.mappers.CategoryMapper;
import kz.bitlab.realKhabar.realKhabar.mappers.CommentMapper;
import kz.bitlab.realKhabar.realKhabar.mappers.UserMapper;
import kz.bitlab.realKhabar.realKhabar.models.Article;
import kz.bitlab.realKhabar.realKhabar.models.Category;
import kz.bitlab.realKhabar.realKhabar.models.Comment;
import kz.bitlab.realKhabar.realKhabar.models.User;
import kz.bitlab.realKhabar.realKhabar.repositories.ArticleRepository;
import kz.bitlab.realKhabar.realKhabar.repositories.CategoryRepository;
import kz.bitlab.realKhabar.realKhabar.services.ArticleService;
import kz.bitlab.realKhabar.realKhabar.services.CommentService;
import kz.bitlab.realKhabar.realKhabar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service

public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ArticleView addNewArticle(ArticleCreate articleCreate) {
        Article article = articleMapper.toEntity(articleCreate);
        User user = userService.getUserById(article.getAuthor().getId());
        List<Category> categories = categoryRepository.findAllById(articleCreate.getCategoryId());
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        if (articleCreate.getTitle() == null || articleCreate.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (articleCreate.getDescription() == null || articleCreate.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (articleCreate.getText() == null || articleCreate.getText().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }
        if (articleCreate.getCategoryId() == null || articleCreate.getCategoryId().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
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
    public List<ArticleView> getAllArticles() {
        List <Article> articles =   articleRepository.getAllByOrderByPostTimeDesc();
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

    @Override
    public ArticleView getArticleById(Long articleId) {
        return articleMapper.toView(articleRepository.getArticleById(articleId));
    }

    @Override
    public ArticleView updateArticle(ArticleUpdate articleUpdate) {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<Category> categories = categoryRepository.findAllById(articleUpdate.getCategoryId());
        if (articleUpdate.getTitle() == null || articleUpdate.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (articleUpdate.getDescription() == null || articleUpdate.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (articleUpdate.getText() == null || articleUpdate.getText().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }
        if (articleUpdate.getCategoryId() == null || articleUpdate.getCategoryId().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
        Article article = articleMapper.toEntity(articleUpdate);
        article.setPostTime(now);
        if (articleUpdate.isNewsOfTheDay()) {
            articleRepository.resetNewsOfTheDay();
        }
        article.setNewsOfTheDay(articleUpdate.isNewsOfTheDay());
        article.setCategories(categories);
        articleRepository.save(article);
        return articleMapper.toView(article);
    }

    @Override
    public List<CategoryDto> getCategoriesByArticleId(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        List<Category> categories = article.getCategories();
        ArticleView articleView = articleMapper.toView(article);
        return articleView.getCategories();
    }

    @Override
    public List<CommentDto> getCommentsByArticleId(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        List<Comment> comments = commentService.findAllByArticle(article);
        return commentMapper.toDtoList(comments);
    }

    @Override
    public List<ArticleView> getArticlesByCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        CategoryDto categoryDto = categoryMapper.toDto(category);
        List<Article> articles = articleRepository.getAllArticlesByCategoryId(category.getName());
        return articleMapper.toViewList(articles);
    }

    @Override
    public List<ArticleView> getAllByAuthorId(Long authorId) {
        List<Article> articles = articleRepository.getAllByAuthorId(authorId);
        return articleMapper.toViewList(articles);
    }

    @Override
    public List<ArticleView> getLastArticles() {
        List<ArticleView> articles = getAllArticles();
        return articles.subList(0, Math.min(articles.size(), 3));
    }

    @Override
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}

