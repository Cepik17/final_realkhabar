package kz.bitlab.realKhabar.realKhabar.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleUpdate {
    private Long id;
    private String title;
    private String description;
    private String text;
    private String imgUrl;
    private boolean isNewsOfTheDay;
    private Long authorId;
    private List<Long> categoryId;
}
