package kz.bitlab.realKhabar.realKhabar.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreate {

    private String commentText;
    private Long authorId;
    private Long articleId;
}
