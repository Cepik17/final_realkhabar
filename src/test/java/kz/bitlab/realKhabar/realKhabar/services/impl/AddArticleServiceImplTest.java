package kz.bitlab.realKhabar.realKhabar.services.impl;

import kz.bitlab.realKhabar.realKhabar.dtos.ArticleCreate;
import kz.bitlab.realKhabar.realKhabar.dtos.ArticleView;
import kz.bitlab.realKhabar.realKhabar.mappers.ArticleMapper;
import kz.bitlab.realKhabar.realKhabar.models.Article;
import kz.bitlab.realKhabar.realKhabar.models.Category;
import kz.bitlab.realKhabar.realKhabar.models.Role;
import kz.bitlab.realKhabar.realKhabar.models.User;
import kz.bitlab.realKhabar.realKhabar.repositories.CategoryRepository;
import kz.bitlab.realKhabar.realKhabar.repositories.RoleRepository;
import kz.bitlab.realKhabar.realKhabar.repositories.UserRepository;
import kz.bitlab.realKhabar.realKhabar.services.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddArticleServiceImplTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    void addNewArticle() {
        User user = new User();
        String password = "testPassword";
        user.setFullName("testFullName");
        user.setEmail("test@email");
        user.setPassword(passwordEncoder.encode(password));
        Role role = new Role();
        role.setName("ROLE_Admin");
        roleRepository.save(role);
        List<Role> roles = List.of(role);
        user.setRoles(roles);
        user.setEnabled(true);
        userRepository.save(user);
        Category category = new Category();
        category.setName("World");
        categoryRepository.save(category);
        ArticleCreate articleCreate = new ArticleCreate();
        articleCreate.setAuthorId(user.getId());
        articleCreate.setTitle("abc");
        articleCreate.setDescription("zxc");
        articleCreate.setText("qwe");
        articleCreate.setCategoryId(List.of(category.getId()));
        articleCreate.setImgUrl("/images");
        articleCreate.setNewsOfTheDay(false);
        ArticleView articleView = articleService.addNewArticle(articleCreate);
        assertNotNull(articleView.getTitle());
        assertNotNull(articleView.getDescription());
        assertNotNull(articleView.getAuthor());
        assertEquals(articleView.getTitle(), articleCreate.getTitle());
        assertEquals(articleView.getAuthor().getId(), articleCreate.getAuthorId());
        Article article = articleMapper.toEntity(articleView);
        articleService.deleteArticle(article.getId());
    }

    @Test
    void addNewArticleEmptyTitle() {
        User user = new User();
        String password = "testPassword";
        user.setFullName("testFullName");
        user.setEmail("test@email");
        user.setPassword(passwordEncoder.encode(password));
        Role role = new Role();
        role.setName("ROLE_Admin");
        roleRepository.save(role);
        List<Role> roles = List.of(role);
        user.setRoles(roles);
        user.setEnabled(true);
        userRepository.save(user);
        Category category = new Category();
        category.setName("World");
        categoryRepository.save(category);
        ArticleCreate articleCreate = new ArticleCreate();
        articleCreate.setAuthorId(user.getId());
        articleCreate.setTitle("");
        articleCreate.setDescription("zxc");
        articleCreate.setText("qwe");
        articleCreate.setCategoryId(List.of(category.getId()));
        articleCreate.setImgUrl("/images");
        articleCreate.setNewsOfTheDay(false);
        assertThrows(IllegalArgumentException.class, () -> articleService.addNewArticle(articleCreate));
    }
}