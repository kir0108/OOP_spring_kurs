package com.korshunov.spring.controller;

import com.korshunov.spring.entity.Article;
import com.korshunov.spring.exception.ArticleNotFoundException;
import com.korshunov.spring.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/articles")
@CrossOrigin
public class ArticleController {
    private ArticleService articleService;

    @GetMapping("/all")
    ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articleList = articleService.listArticle();
        return new ResponseEntity<>(articleList, HttpStatus.OK);
    }

    @GetMapping("/article/{id}")
    ResponseEntity<Article> getArticle(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(articleService.findArticle(id), HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found");
        }
    }

    @GetMapping("/id_article/{name}")
    ResponseEntity<Long> getArticle(@PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(articleService.getId(name), HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found");
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Article addArticle(@RequestBody Article newArticle) {
        return articleService.create(newArticle);
    }

    @PutMapping(value = "/article/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @Valid @RequestBody Article article) {
        if (!id.equals(article.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found");
        }
        return new ResponseEntity<>(articleService.update(article), HttpStatus.OK);
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable Long id) {
        try {
            articleService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found");
        }
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }
}
