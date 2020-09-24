package com.korshunov.spring.service;

import com.korshunov.spring.entity.Article;
import com.korshunov.spring.exception.ArticleNotFoundException;
import com.korshunov.spring.repository.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepo articleRepo;

    @Override
    public List<Article> listArticle() {
        return (List<Article>) articleRepo.findAll();
    }

    @Override
    public Article create(Article article) {
        return articleRepo.save(article);
    }

    @Override
    public void delete(Long id) {
        try {
            articleRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ArticleNotFoundException("Article not found");
        }
    }

    @Override
    public Article update(Article article) {
        return articleRepo.save(article);
    }

    @Override
    public Article findArticle(Long id) {
        Optional<Article> optionalArticle = articleRepo.findById(id);
        if (optionalArticle.isPresent()){
            return optionalArticle.get();
        } else {
            throw new ArticleNotFoundException("Article not found");
        }
    }

    @Override
    public Long getId(String name) {
        Article article = articleRepo.findByName(name);
        if (article != null) {
            return article.getId();
        } else {
            throw new ArticleNotFoundException("Article not found");
        }
    }
}
