package kz.bitlab.realKhabar.realKhabar.services.impl;

import kz.bitlab.realKhabar.realKhabar.dtos.ArticleView;
import kz.bitlab.realKhabar.realKhabar.models.Article;
import kz.bitlab.realKhabar.realKhabar.models.Category;
import kz.bitlab.realKhabar.realKhabar.models.Role;
import kz.bitlab.realKhabar.realKhabar.models.User;
import kz.bitlab.realKhabar.realKhabar.repositories.ArticleRepository;
import kz.bitlab.realKhabar.realKhabar.repositories.CategoryRepository;
import kz.bitlab.realKhabar.realKhabar.repositories.RoleRepository;
import kz.bitlab.realKhabar.realKhabar.repositories.UserRepository;
import kz.bitlab.realKhabar.realKhabar.services.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ByAuthorIdArticleServiceImplTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleService articleService;

    @Test
    void getArticlesByAuthorId() {
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
        Article article1 = articleCreate(user);
        Article article2 = articleCreate(user);
        Article article3 = articleCreate(user);

        Page<ArticleView> page = articleService.getArticlesByAuthorId(article1.getAuthor().getId(), 0, 5);
        assertNotNull(page);
        assertEquals(3, page.getTotalElements());

        ArticleView firstArticleView = page.getContent().get(0);
        ArticleView secondArticleView = page.getContent().get(1);
        ArticleView thirdArticleView = page.getContent().get(2);
        assertEquals(firstArticleView.getAuthor().getId(), article1.getAuthor().getId());
        assertEquals(secondArticleView.getAuthor().getId(), article2.getAuthor().getId());
        assertEquals(thirdArticleView.getAuthor().getId(), article3.getAuthor().getId());

    }

    public Article articleCreate(User user) {
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