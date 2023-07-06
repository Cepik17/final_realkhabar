package kz.bitlab.realKhabar.realKhabar.controllers;

import kz.bitlab.realKhabar.realKhabar.dtos.ArticleCreate;
import kz.bitlab.realKhabar.realKhabar.dtos.ArticleView;
import kz.bitlab.realKhabar.realKhabar.dtos.UserView;
import kz.bitlab.realKhabar.realKhabar.services.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    private ArticleView addNewArticle(@RequestBody ArticleCreate articleCreate){
        System.out.println("cont " + articleCreate.isNewsOfTheDay());
        return articleService.addNewArticle(articleCreate);
    }

    @GetMapping("/my")
    private List<ArticleView> getUserArticles(@RequestParam Long userId){
        return articleService.findArticlesByUserId(userId);
    }
}
