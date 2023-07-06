package kz.bitlab.realKhabar.realKhabar.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ArticleCreate {
    private String title;
    private String description;
    private String text;
    private String imgUrl;
    private boolean isNewsOfTheDay;
    private Long authorId;
    private List<Long> categoryId;
}
