package kz.bitlab.realKhabar.realKhabar.services.impl;

import kz.bitlab.realKhabar.realKhabar.dtos.ArticleView;
import kz.bitlab.realKhabar.realKhabar.dtos.CategoryDto;
import kz.bitlab.realKhabar.realKhabar.repositories.CategoryRepository;
import kz.bitlab.realKhabar.realKhabar.services.ArticleService;
import kz.bitlab.realKhabar.realKhabar.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final ArticleService articleService;

    private final CategoryRepository categoryRepository;

//    @Override
//    public String getCategoryName(ArticleView articleView) {
//        List<CategoryDto> categoryDtos = articleView.getCategories();
//        List<String> categories = categoryDtos
//        return null;
//    }
}
