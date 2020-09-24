package com.korshunov.spring.service;

import com.korshunov.spring.entity.Article;

import java.util.List;

public interface ArticleService {
    List<Article> listArticle();
    Article create(Article article);
    void delete(Long id);
    Article update(Article article);
    Article findArticle(Long id);
    Long getId(String name);
}
