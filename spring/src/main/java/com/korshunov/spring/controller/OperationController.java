package com.korshunov.spring.controller;

import com.korshunov.spring.entity.Article;
import com.korshunov.spring.entity.Balance;
import com.korshunov.spring.entity.Operation;
import com.korshunov.spring.exception.OperationNotFoundException;
import com.korshunov.spring.service.ArticleService;
import com.korshunov.spring.service.BalanceService;
import com.korshunov.spring.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/operations")
@CrossOrigin
public class OperationController {
    private OperationService operationService;
    private BalanceService balanceService;
    private ArticleService articleService;

    @GetMapping("/all")
    ResponseEntity<List<Operation>> getAllOperations() {
        List<Operation> operationList = operationService.listOperation();
        return new ResponseEntity<>(operationList, HttpStatus.OK);
    }

    @GetMapping("/operation/{id}")
    ResponseEntity<Operation> getOperation(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(operationService.findOperation(id), HttpStatus.OK);
        } catch (OperationNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Operation not found");
        }
    }

    @GetMapping("/filter/{filter}")
    ResponseEntity<List<Operation>> getOperationByFilter(@PathVariable("filter") String filter) {
        try {
            return new ResponseEntity<>(operationService.findByFilter(filter), HttpStatus.OK);
        } catch (OperationNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Operation not found");
        }
    }

    @GetMapping("/article/{article}")
    ResponseEntity<List<Operation>> getOperationByArticle(@PathVariable("article") Long idArticle) {
        try {
            return new ResponseEntity<>(operationService.findOperationWithArticle(idArticle), HttpStatus.OK);
        } catch (OperationNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Operation not found");
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Operation> addOperation(@RequestBody Operation newOperation) {
        try{
            Long articleId = articleService.getId(newOperation.getArticle().getName());
            newOperation.getArticle().setId(articleId);
        } catch (Exception ex) {
            Article article = articleService.create(new Article(newOperation.getArticle().getName()));
            newOperation.getArticle().setId(article.getId());
        }

        Balance balance = balanceService.create(new Balance(newOperation.getCreateDate(), newOperation.getDebit(),
                newOperation.getCredit(), newOperation.getBalance().getAmount()));

        newOperation.getBalance().setId(balance.getId());

        return new ResponseEntity<>(operationService.create(newOperation), HttpStatus.OK);
    }

    @PutMapping(value = "/operation/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Operation> updateOperation(@PathVariable Long id, @Valid @RequestBody Operation operation) {
        if (!id.equals(operation.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Operation not found");
        }

        Long pastArticleId = operation.getArticle().getId();

        try {
            Long articleId = articleService.getId(operation.getArticle().getName());
            operation.getArticle().setId(articleId);
        } catch (Exception ex) {
            if (operationService.findOperationWithArticle(operation.getArticle().getId()).size() > 1) {
                operation.setArticle(articleService.create(new Article(operation.getArticle().getName())));
            } else {
                articleService.update(operation.getArticle());
            }
        }

        operation.setBalance(balanceService.update(operation.getBalance()));

        operationService.update(operation);

        if (operationService.findOperationWithArticle(pastArticleId).isEmpty()){
            articleService.delete(pastArticleId);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/operation/{id}")
    public ResponseEntity<Operation> deleteOperation(@PathVariable Long id) {
        try {
            Operation operation = operationService.findOperation(id);
            Long balanceId = operation.getBalance().getId();
            Long articleId = operation.getArticle().getId();
            operationService.delete(id);
            balanceService.delete(balanceId);
            if (operationService.findOperationWithArticle(articleId).isEmpty()) {
                articleService.delete(articleId);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OperationNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Operation not found");
        }
    }

    @Autowired
    public void setOperationService(OperationService operationService) {
        this.operationService = operationService;
    }

    @Autowired
    public void setBalanceService(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }
}