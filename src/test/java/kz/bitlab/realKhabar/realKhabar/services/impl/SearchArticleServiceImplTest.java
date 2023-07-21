package kz.bitlab.realKhabar.realKhabar.services.impl;

import kz.bitlab.realKhabar.realKhabar.dtos.ArticleView;
import kz.bitlab.realKhabar.realKhabar.dtos.SearchQuery;
import kz.bitlab.realKhabar.realKhabar.models.*;
import kz.bitlab.realKhabar.realKhabar.repositories.ArticleRepository;
import kz.bitlab.realKhabar.realKhabar.repositories.CategoryRepository;
import kz.bitlab.realKhabar.realKhabar.repositories.RoleRepository;
import kz.bitlab.realKhabar.realKhabar.repositories.UserRepository;
import kz.bitlab.realKhabar.realKhabar.services.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SearchArticleServiceImplTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Test
    void searchArticleByText() {
        Article article = articleCreate();
        article.setText("qwe");
        articleRepository.save(article);

        SearchQuery query = new SearchQuery();
        query.setQuery("qwe");
        query.setSearchInText(true);

        List<ArticleView> articles = articleService.searchArticle(query);
        for (ArticleView a : articles) {
            assertTrue(a.getText().contains(query.getQuery()));
        }
        articleRepository.delete(article);

    }

    @Test
    void searchArticleByTitle() {
        Article article = articleCreate();
        article.setTitle("title");
        articleRepository.save(article);

        SearchQuery query = new SearchQuery();
        query.setQuery("title");
        query.setSearchInTitle(true);

        List<ArticleView> articles = articleService.searchArticle(query);
        for (ArticleView a : articles) {
            assertTrue(a.getTitle().contains(query.getQuery()));
        }
        articleRepository.delete(article);
    }

    @Test
    void searchArticleByAuthor() {
        User user = new User();
        user.setFullName("Vasya");
        userRepository.save(user);
        Article article = articleCreate();
        article.setAuthor(user);
        articleRepository.save(article);

        SearchQuery query = new SearchQuery();
        query.setQuery("Vasya");
        query.setSearchByAuthor(true);

        List<ArticleView> articles = articleService.searchArticle(query);
        for (ArticleView a : articles) {
            assertTrue(a.getAuthor().getFullName().contains(query.getQuery()));
        }
        articleRepository.delete(article);
    }


    public Article articleCreate() {
        Role role = new Role();
        role.setName("ROLE_Admin");
        roleRepository.save(role);
        List<Role> roles = List.of(role);
        User user = new User();
        user.setFullName("testFullName");
        user.setEmail("test@email.com");
        user.setPassword("password");
        user.setRoles(roles);
        user.setEnabled(true);
        userRepository.save(user);
        Category category = new Category();
        category.setName("World");
        categoryRepository.save(category);
        Article article = new Article();
        article.setCategories(List.of(category));
        article.setPostTime(LocalDateTime.now());
        article.setImgUrl("C");
        article.setNewsOfTheDay(false);
        article.setDescription("adad");
        article.setAuthor(user);
        article.setTitle("zxc");
        article.setText("tgtb");
        articleRepository.save(article);
        return article;
    }
}