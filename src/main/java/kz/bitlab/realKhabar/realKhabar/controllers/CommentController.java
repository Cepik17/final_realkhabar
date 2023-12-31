package kz.bitlab.realKhabar.realKhabar.controllers;

import kz.bitlab.realKhabar.realKhabar.dtos.CommentCreate;
import kz.bitlab.realKhabar.realKhabar.dtos.CommentDto;
import kz.bitlab.realKhabar.realKhabar.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public void addNewComment(@RequestBody CommentCreate commentCreate) {
        commentService.addNewComment(commentCreate);
    }

    @PreAuthorize("hasAnyRole('ROLE_Admin')")
    @DeleteMapping("/delete/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

    @GetMapping("/{articleId}")
    public List<CommentDto> getCommentsByArticleId(@PathVariable Long articleId) {
        return commentService.getCommentsByArticleId(articleId);
    }
}
