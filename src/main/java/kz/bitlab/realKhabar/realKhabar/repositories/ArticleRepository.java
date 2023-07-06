package kz.bitlab.realKhabar.realKhabar.repositories;

import kz.bitlab.realKhabar.realKhabar.models.Article;
import kz.bitlab.realKhabar.realKhabar.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findArticlesByAuthor(User user);

    @Query("select a from Article a where a.isNewsOfTheDay=true")
    Article findArticleByNewsOfTheDayIsTrue();

    @Query("update Article a set a.isNewsOfTheDay = false where a.isNewsOfTheDay = true")
    void resetNewsOfTheDay();
}
