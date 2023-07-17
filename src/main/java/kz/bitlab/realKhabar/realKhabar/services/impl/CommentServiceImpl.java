package kz.bitlab.realKhabar.realKhabar.services.impl;

import kz.bitlab.realKhabar.realKhabar.dtos.CommentCreate;
import kz.bitlab.realKhabar.realKhabar.mappers.CommentMapper;
import kz.bitlab.realKhabar.realKhabar.models.Article;
import kz.bitlab.realKhabar.realKhabar.models.Comment;
import kz.bitlab.realKhabar.realKhabar.repositories.CommentRepository;
import kz.bitlab.realKhabar.realKhabar.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<Comment> findAllByArticle(Article article) {
        return commentRepository.findAllByArticle(article);
    }

    @Override
    public void addNewComment(CommentCreate commentCreate) {
        if (commentCreate.getCommentText() == null || commentCreate.getCommentText().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }
        Comment newComment = commentMapper.toEntity(commentCreate);
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        newComment.setCommentTime(now);
        commentRepository.save(newComment);

    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
