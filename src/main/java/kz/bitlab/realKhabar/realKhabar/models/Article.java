package kz.bitlab.realKhabar.realKhabar.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="articles")
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String title;
    private String description;
    @Column(columnDefinition = "TEXT")
    private String text;
    private LocalDateTime postTime;
    @Column(columnDefinition = "TEXT")
    private String imgUrl;
    private boolean isNewsOfTheDay;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Category> categories;
}
