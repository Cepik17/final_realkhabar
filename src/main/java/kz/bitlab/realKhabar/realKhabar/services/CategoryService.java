package kz.bitlab.realKhabar.realKhabar.services;

import kz.bitlab.realKhabar.realKhabar.dtos.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getCategoriesByArticleId(Long articleId);
}
