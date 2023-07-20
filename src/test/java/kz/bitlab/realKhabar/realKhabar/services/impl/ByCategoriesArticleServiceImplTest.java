package kz.bitlab.realKhabar.realKhabar.services.impl;

import kz.bitlab.realKhabar.realKhabar.dtos.ArticleView;
import kz.bitlab.realKhabar.realKhabar.models.Article;
import kz.bitlab.realKhabar.realKhabar.models.Category;
import kz.bitlab.realKhabar.realKhabar.repositories.ArticleRepository;
import kz.bitlab.realKhabar.realKhabar.repositories.CategoryRepository;
import kz.bitlab.realKhabar.realKhabar.services.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ByCategoriesArticleServiceImplTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleService articleService;

    @Test
    void getArticlesByCategory() {
        Category category = new Category();
        category.setName("TestCategory");
        categoryRepository.save(category);
        Article article1 = createTestArticle("Test1", category);
        Article article2 = createTestArticle("Test2", category);
        Article article3 = createTestArticle("Test3", category);
        Page<ArticleView> page = articleService.getArticlesByCategory(category.getId(), 0, 5);
        assertEquals(3, page.getTotalElements());
        ArticleView firstArticleView = page.getContent().get(0);
        ArticleView secondArticleView = page.getContent().get(1);
        ArticleView thirdArticleView = page.getContent().get(2);
        assertEquals(firstArticleView.getTitle(), article1.getTitle());
        assertEquals(secondArticleView.getTitle(), article2.getTitle());
        assertEquals(thirdArticleView.getTitle(), article3.getTitle());

    }

    private Article createTestArticle(String title, Category category) {
        Article article = new Article();
        article.setTitle(title);
        article.setCategories(List.of(category));
        articleRepository.save(article);
        return article;
    }
}