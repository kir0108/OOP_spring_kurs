package com.korshunov.spring.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@ToString(of = {"id", "article", "debit", "credit", "createDate", "balance"})
@EqualsAndHashCode(of = {"id"})
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Article.class, cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false)
    private Article article;

    private Float debit;

    private Float credit;

    @NotNull(message = "Date can't be null")
    @Size(min = 8, max = 8)
    private String createDate;

    @ManyToOne(targetEntity = Balance.class, cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false)
    private Balance balance;

    public Operation() {

    }

    public Operation(Article article, Float debit, Float credit, String createDate, Balance balance) {
        if ((credit < 0) || (debit < 0)) {
            throw new IllegalArgumentException("Приход и/или расход не могут быть отрицательными.");
        }

        this.article = article;
        this.debit = debit;
        this.credit = credit;
        this.createDate = createDate;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Float getDebit() {
        return debit;
    }

    public void setDebit(Float debit) {
        this.debit = debit;
    }

    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }
}