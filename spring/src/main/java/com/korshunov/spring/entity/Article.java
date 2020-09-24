package com.korshunov.spring.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = {"id", "name"})
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name can't be blank")
    @Size(max = 50, message = "Name's max size is 50")
    private String name;

    public Article() {
    }

    public Article(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}