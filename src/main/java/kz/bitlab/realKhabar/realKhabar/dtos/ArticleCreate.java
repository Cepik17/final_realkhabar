package kz.bitlab.realKhabar.realKhabar.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCreate {
    private String title;
    private String description;
    private String text;
    private String imgUrl;
    private boolean isNewsOfTheDay;
    private Long authorId;
    private List<Long> categoryId;
}
