package com.university.client.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Operation {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("article")
    @Expose
    private Article article;
    @SerializedName("debit")
    @Expose
    private Float debit;
    @SerializedName("credit")
    @Expose
    private Float credit;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("balance")
    @Expose
    private Balance balance;

    public Operation(Long id, Article article, Float debit, Float credit, String createDate, Balance balance) {
        this.id = id;
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

    @Override
    public String toString() {
        return article.getName().toLowerCase() + " " + debit + " " + credit + " " + balance.getAmount() +
                " " + createDate;
    }
}
