package kz.bitlab.realKhabar.realKhabar.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {

    private Long id;
    private String commentText;
    private LocalDateTime commentTime;
    private UserView author;
    private Long articleId;
}
