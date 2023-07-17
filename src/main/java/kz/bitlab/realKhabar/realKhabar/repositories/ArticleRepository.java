package kz.bitlab.realKhabar.realKhabar.repositories;

import kz.bitlab.realKhabar.realKhabar.models.Article;
import kz.bitlab.realKhabar.realKhabar.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findArticlesByAuthor(User user);

    @Query("select a from Article a where a.isNewsOfTheDay=true")
    Article findArticleByNewsOfTheDayIsTrue();

    @Modifying
    @Transactional
    @Query("update Article a set a.isNewsOfTheDay = false where a.isNewsOfTheDay = true")
    void resetNewsOfTheDay();

    Article getArticleById(Long articleId);

    Page<Article> getPageByOrderByPostTimeDesc(Pageable pageable);

    List<Article> getAllByOrderByPostTimeDesc();

    @Query("select a from Article a join a.categories c where c.id = :categoryId")
    Page<Article> getAllArticlesByCategoryId(Long categoryId, Pageable pageable);

    Page<Article> getAllByAuthorId(Long authorId, Pageable pageable);

    List<Article> getByTitleContainingIgnoreCase(String searchInTitle);

    @Query("SELECT a FROM Article a WHERE LOWER(a.author.fullName) LIKE LOWER(CONCAT('%', :searchByAuthor, '%'))")
    List<Article> getByAuthorName(@Param("searchByAuthor") String searchByAuthor);

    List<Article> getByTextContainingIgnoreCase(String searchInText);


}
