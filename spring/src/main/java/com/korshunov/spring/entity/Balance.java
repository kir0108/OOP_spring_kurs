package com.korshunov.spring.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@ToString(of = {"id", "createDate", "debit", "credit", "amount"})
@EqualsAndHashCode(of = {"id", "createDate", "debit", "credit", "amount"})
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date can't be null")
    @Size(min = 8, max = 8)
    private String createDate;

    private Float debit;

    private Float credit;

    private Float amount;

    public Balance() {

    }

    public Balance(String createDate, Float debit, Float credit, Float amount) {
        this.createDate = createDate;
        this.debit = debit;
        this.credit = credit;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}