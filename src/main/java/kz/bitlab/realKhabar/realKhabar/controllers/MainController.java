package kz.bitlab.realKhabar.realKhabar.controllers;

import kz.bitlab.realKhabar.realKhabar.dtos.ArticleView;
import kz.bitlab.realKhabar.realKhabar.models.User;
import kz.bitlab.realKhabar.realKhabar.services.ArticleService;
import kz.bitlab.realKhabar.realKhabar.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final ArticleService articleService;

    @GetMapping("/")
    public String homePage(Model model){
        List<ArticleView> allArticles = articleService.getAllArticles();
        ArticleView articleView = articleService.findByNewsOfTheDayIsTrue();
        model.addAttribute("articleOfTheDay", articleView);
        model.addAttribute("articles", allArticles);
        return "home";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myarticles")
    public String myArticlePage(Model model){
        User user =userService.getCurrentUser();
        model.addAttribute("currentUser", user);
        return "myarticles";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/addarticle")
    public String addArticlePage(Model model){
        User user =userService.getCurrentUser();
        model.addAttribute("currentUser", user);
        return "addarticle";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/article/{id}")
    public String articlePage(@PathVariable Long id, Model model){
//        ArticleView articleView = articleService.findByNewsOfTheDayIsTrue();
        model.addAttribute("articleId", id);
        User user =userService.getCurrentUser();
        model.addAttribute("currentUser", user);
        return "article";
    }
}
