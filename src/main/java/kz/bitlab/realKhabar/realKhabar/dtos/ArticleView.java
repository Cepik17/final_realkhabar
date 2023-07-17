package kz.bitlab.realKhabar.realKhabar.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ArticleView {

    private Long id;
    private String title;
    private String description;
    private String text;
    private LocalDateTime postTime;
    private String imgUrl;
    private boolean isNewsOfTheDay;
    private UserView author;
    private List<CategoryDto> categories;
}
