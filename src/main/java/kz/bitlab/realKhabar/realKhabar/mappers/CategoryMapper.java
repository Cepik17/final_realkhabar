package kz.bitlab.realKhabar.realKhabar.mappers;

import kz.bitlab.realKhabar.realKhabar.dtos.CategoryDto;
import kz.bitlab.realKhabar.realKhabar.models.Category;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDto);
}
