package kz.bitlab.realKhabar.realKhabar.services.impl;

import kz.bitlab.realKhabar.realKhabar.dtos.*;
import kz.bitlab.realKhabar.realKhabar.mappers.ArticleMapper;
import kz.bitlab.realKhabar.realKhabar.mappers.CommentMapper;
import kz.bitlab.realKhabar.realKhabar.models.*;
import kz.bitlab.realKhabar.realKhabar.repositories.ArticleRepository;
import kz.bitlab.realKhabar.realKhabar.repositories.CategoryRepository;
import kz.bitlab.realKhabar.realKhabar.services.ArticleService;
import kz.bitlab.realKhabar.realKhabar.services.CommentService;
import kz.bitlab.realKhabar.realKhabar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    public Page<ArticleView> getAllArticles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "postTime"));
        Page<Article> articles = articleRepository.getPageByOrderByPostTimeDesc(pageable);
        return articles.map(articleMapper::toView);
    }

    @Override
    public List<ArticleView> getArticlesList() {
        return articleMapper.toViewList(articleRepository.findAll());
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
        List<Category> categories = categoryRepository.findAllById(articleUpdate.getCategoryId());
        List<Category> newCategories = new ArrayList<>();
        if (article.getCategories() == null) {
            article.setCategories(new ArrayList<>());
        }
        for (Category category : categories) {
            if (!article.getCategories().contains(category)) {
                newCategories.add(category);
            }
        }
        article.setCategories(newCategories);
        articleRepository.save(article);
        return articleMapper.toView(article);
    }

    @Override
    public Page<ArticleView> getArticlesByCategory(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "postTime"));
        Page<Article> articlesPage = articleRepository.getAllArticlesByCategoryId(categoryId, pageable);
        return articlesPage.map(articleMapper::toView);
    }

    @Override
    public Page<ArticleView> getArticlesByAuthorId(Long authorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "postTime"));
        Page<Article> articlesPage = articleRepository.getAllByAuthorId(authorId, pageable);
        return articlesPage.map(articleMapper::toView);
    }

    @Override
    public List<ArticleView> getLastArticles() {
        List<ArticleView> articles = articleMapper.toViewList(articleRepository.getAllByOrderByPostTimeDesc());
        return articles.subList(0, Math.min(articles.size(), 3));
    }

    @Override
    public List<ArticleView> searchArticle(SearchQuery query) {
        List<Article> articles = new ArrayList<>();
        if (query.isSearchInText()) {
            articles.addAll(articleRepository.getByTextContainingIgnoreCase(query.getQuery()));
        }
        if (query.isSearchInTitle()) {
            articles.addAll(articleRepository.getByTitleContainingIgnoreCase(query.getQuery()));
        }
        if (query.isSearchByAuthor()) {
            articles.addAll(articleRepository.getByAuthorName(query.getQuery()));
        }
        if (!query.isSearchInText() && !query.isSearchInTitle() && !query.isSearchByAuthor()) {
            articles = articleRepository.getByTextContainingIgnoreCase(query.getQuery());
        }
        return articleMapper.toViewList(articles);
    }

    @Override
    public void deleteArticle(Long articleId) {
        List<CommentDto> comments = commentService.getCommentsByArticleId(articleId);
        commentService.deleteAllComments(commentMapper.toEntityList(comments));
        articleRepository.deleteById(articleId);
    }
}

