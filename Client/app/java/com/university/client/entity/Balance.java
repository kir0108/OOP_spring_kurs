package com.university.client.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Balance {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("debit")
    @Expose
    private Float debit;
    @SerializedName("credit")
    @Expose
    private Float credit;
    @SerializedName("amount")
    @Expose
    private Float amount;

    public Balance(Long id, String createDate, Float debit, Float credit, Float amount) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", createDate='" + createDate + '\'' +
                ", debit=" + debit +
                ", credit=" + credit +
                ", amount=" + amount +
                '}';
    }
}
