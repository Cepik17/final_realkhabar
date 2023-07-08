package kz.bitlab.realKhabar.realKhabar.mappers;

import kz.bitlab.realKhabar.realKhabar.dtos.CommentDto;
import kz.bitlab.realKhabar.realKhabar.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "articleId", target = "article.id")
    Comment toEntity(CommentDto commentDto);

    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "article.id", target = "articleId")
    CommentDto toDto(Comment comment);

    @Mapping(source = "authorId", target = "author.id")
    @Mapping(source = "articleId", target = "article.id")
    List<Comment> toEntityList(List<CommentDto> commentDtos);

    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "article.id", target = "articleId")
    List<CommentDto> toDtoList(List<Comment> comments);
}