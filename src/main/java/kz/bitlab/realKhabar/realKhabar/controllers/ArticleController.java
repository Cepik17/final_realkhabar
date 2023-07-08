package kz.bitlab.realKhabar.realKhabar.controllers;

import kz.bitlab.realKhabar.realKhabar.dtos.*;
import kz.bitlab.realKhabar.realKhabar.services.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

//    @PostMapping
//    private ArticleView addNewArticle(@RequestBody ArticleCreate articleCreate){
//        return articleService.addNewArticle(articleCreate);
//    }
    @PostMapping
    public ResponseEntity<?> addNewArticle(@RequestBody ArticleCreate articleCreate){
        try {
            ArticleView articleView = articleService.addNewArticle(articleCreate);
            return ResponseEntity.ok(articleView);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/my")
    public List<ArticleView> getUserArticles(@RequestParam Long userId){
        return articleService.findArticlesByUserId(userId);
    }

    @GetMapping("{articleId}")
    public ArticleView getArticleById(@PathVariable Long articleId){
        return articleService.getArticleById(articleId);
    }

//    @PutMapping
//    public ArticleView updateArticle(@RequestBody ArticleUpdate articleUpdate){
//        return articleService.updateArticle(articleUpdate);
//    }

    @PutMapping
    public ResponseEntity<?> updateArticle(@RequestBody ArticleUpdate articleUpdate) {
        try {
            ArticleView articleView = articleService.updateArticle(articleUpdate);
            return ResponseEntity.ok(articleView);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{articleId}/categories")
    public List<CategoryDto> getCategoriesByArticleId(@PathVariable Long articleId){
        return articleService.getCategoriesByArticleId(articleId);
    }

    @GetMapping("{articleId}/comments")
    public List<CommentDto> getCommentsByArticleId(@PathVariable Long articleId){
        return articleService.getCommentsByArticleId(articleId);
    }

}
