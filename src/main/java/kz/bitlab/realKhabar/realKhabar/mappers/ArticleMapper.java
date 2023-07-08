package kz.bitlab.realKhabar.realKhabar.mappers;

import kz.bitlab.realKhabar.realKhabar.dtos.ArticleCreate;
import kz.bitlab.realKhabar.realKhabar.dtos.ArticleUpdate;
import kz.bitlab.realKhabar.realKhabar.dtos.ArticleView;
import kz.bitlab.realKhabar.realKhabar.models.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mapping(source = "author.id", target = "authorId")
    ArticleCreate toDto(Article article);

    @Mapping(source = "author.id", target = "authorId")
    ArticleView toView(Article article);

    @Mapping(source = "author.id", target = "authorId")
    List<ArticleView> toViewList(List<Article> articles);

    @Mapping(source = "authorId", target = "author.id")
    Article toEntity(ArticleCreate articleCreate);

    @Mapping(source = "authorId", target = "author.id")
    Article toEntity(ArticleView articleView);

    @Mapping(source = "authorId", target = "author.id")
    Article toEntity(ArticleUpdate articleUpdate);
}
