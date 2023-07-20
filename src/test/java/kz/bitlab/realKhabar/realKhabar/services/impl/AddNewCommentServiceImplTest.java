package kz.bitlab.realKhabar.realKhabar.services.impl;

import kz.bitlab.realKhabar.realKhabar.dtos.CommentCreate;
import kz.bitlab.realKhabar.realKhabar.mappers.CommentMapper;
import kz.bitlab.realKhabar.realKhabar.models.*;
import kz.bitlab.realKhabar.realKhabar.repositories.*;
import kz.bitlab.realKhabar.realKhabar.services.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddNewCommentServiceImplTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @Test
    void addNewComment() {
        Article article = articleCreate();
        CommentCreate commentCreate = new CommentCreate();
        commentCreate.setCommentText("test comment");
        commentCreate.setAuthorId(article.getAuthor().getId());
        commentCreate.setArticleId(article.getId());
        Comment newComment = commentMapper.toEntity(commentCreate);
        newComment.setCommentTime(LocalDateTime.now());
        commentRepository.save(newComment);
        assertNotNull(newComment);
        assertEquals(commentCreate.getCommentText(), newComment.getCommentText());
        commentRepository.delete(newComment);
        articleRepository.delete(article);
    }

    @Test
    public void addNewCommentEmptyTextThrowsException() {
        Article article = articleCreate();
        CommentCreate commentCreate = new CommentCreate();
        commentCreate.setCommentText("");
        commentCreate.setAuthorId(article.getAuthor().getId());
        commentCreate.setArticleId(article.getId());
        Comment newComment = commentMapper.toEntity(commentCreate);
        newComment.setCommentTime(LocalDateTime.now());
        commentRepository.save(newComment);
        assertThrows(IllegalArgumentException.class, () -> commentService.addNewComment(commentCreate));
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