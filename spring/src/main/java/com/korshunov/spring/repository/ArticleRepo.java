package com.korshunov.spring.repository;

import com.korshunov.spring.entity.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ArticleRepo extends CrudRepository<Article, Long> {
    @Query("select b from Article b where b.name = :name")
    Article findByName(@Param("name") String name);
}
