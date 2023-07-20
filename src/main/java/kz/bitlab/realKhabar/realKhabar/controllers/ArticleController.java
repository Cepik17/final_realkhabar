package kz.bitlab.realKhabar.realKhabar.controllers;

import kz.bitlab.realKhabar.realKhabar.dtos.ArticleCreate;
import kz.bitlab.realKhabar.realKhabar.dtos.ArticleUpdate;
import kz.bitlab.realKhabar.realKhabar.dtos.ArticleView;
import kz.bitlab.realKhabar.realKhabar.models.SearchQuery;
import kz.bitlab.realKhabar.realKhabar.services.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<?> addNewArticle(@RequestBody ArticleCreate articleCreate) {
        try {
            ArticleView articleView = articleService.addNewArticle(articleCreate);
            return ResponseEntity.ok(articleView);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/my")
    public List<ArticleView> getUserArticles(@RequestParam Long userId) {
        return articleService.findArticlesByUserId(userId);
    }

    @GetMapping("{articleId}")
    public ArticleView getArticleById(@PathVariable Long articleId) {
        return articleService.getArticleById(articleId);
    }

    @PutMapping
    public ResponseEntity<?> updateArticle(@RequestBody ArticleUpdate articleUpdate) {
        try {
            ArticleView articleView = articleService.updateArticle(articleUpdate);
            return ResponseEntity.ok(articleView);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @GetMapping("{articleId}/categories")
//    public List<CategoryDto> getCategoriesByArticleId(@PathVariable Long articleId) {
//        return categoryService.getCategoriesByArticleId(articleId);
//    }

//    @GetMapping("{articleId}/comments")
//    public List<CommentDto> getCommentsByArticleId(@PathVariable Long articleId) {
//        return articleService.getCommentsByArticleId(articleId);
//    }

    @GetMapping("/category/{categoryId}/page/{pageNumber}")
    public Page<ArticleView> getArticlesByCategoryId(@PathVariable Long categoryId, @PathVariable int pageNumber) {
        return articleService.getArticlesByCategory(categoryId, pageNumber - 1, 5);
    }

    @GetMapping("/author/{authorId}/page/{pageNumber}")
    public Page<ArticleView> getAllArticleByAuthor(@PathVariable Long authorId, @PathVariable int pageNumber) {
        return articleService.getArticlesByAuthorId(authorId, pageNumber - 1, 5);
    }

    @GetMapping("/all")
    public Page<ArticleView> getAllArticles(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        return articleService.getAllArticles(page, size);
    }

    @GetMapping("/allarticles")
    public List<ArticleView> getAllArticlesList() {
        return articleService.getArticlesList();
    }

    @DeleteMapping("/delete/{articleId}")
    public void deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);
    }

    @PostMapping("/search")
    public List<ArticleView> getSearchResult(@RequestBody SearchQuery query) {
        return articleService.searchArticle(query);
    }

}
