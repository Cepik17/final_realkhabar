package kz.bitlab.realKhabar.realKhabar.services.impl;

import kz.bitlab.realKhabar.realKhabar.dtos.ArticleView;
import kz.bitlab.realKhabar.realKhabar.dtos.CategoryDto;
import kz.bitlab.realKhabar.realKhabar.mappers.ArticleMapper;
import kz.bitlab.realKhabar.realKhabar.models.Article;
import kz.bitlab.realKhabar.realKhabar.repositories.ArticleRepository;
import kz.bitlab.realKhabar.realKhabar.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    public List<CategoryDto> getCategoriesByArticleId(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        ArticleView articleView = articleMapper.toView(article);
        return articleView.getCategories();
    }
}
