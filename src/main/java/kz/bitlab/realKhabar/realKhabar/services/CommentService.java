package kz.bitlab.realKhabar.realKhabar.services;

import kz.bitlab.realKhabar.realKhabar.models.Article;
import kz.bitlab.realKhabar.realKhabar.models.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findAllByArticle(Article article);
}