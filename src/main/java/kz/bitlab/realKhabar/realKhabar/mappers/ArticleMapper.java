package kz.bitlab.realKhabar.realKhabar.mappers;

import kz.bitlab.realKhabar.realKhabar.dtos.ArticleCreate;
import kz.bitlab.realKhabar.realKhabar.dtos.ArticleUpdate;
import kz.bitlab.realKhabar.realKhabar.dtos.ArticleView;
import kz.bitlab.realKhabar.realKhabar.models.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ArticleMapper {

    @Mapping(source = "author", target = "author")
    ArticleView toView(Article article);

    @Mapping(source = "author", target = "author")
    List<ArticleView> toViewList(List<Article> articles);

    @Mapping(source = "authorId", target = "author.id")
    Article toEntity(ArticleCreate articleCreate);

    @Mapping(source = "authorId", target = "author.id")
    Article toEntity(ArticleUpdate articleUpdate);

    Article toEntity(ArticleView articleView);
}
