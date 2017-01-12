package com.ppiotr191.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String type;
    private BigDecimal price;
    private Integer amount;

    @ManyToMany
    private Set<Actor> actors;

    @ManyToOne
    private MovieCategory category;

    @ManyToOne
    private CartElement cart;

    public Movie(){
        this.setActors(new HashSet<Actor>());
    }

    public Movie(String name, String type, BigDecimal price, MovieCategory category, int amount) {

        this.name = name;
        this.type = type;
        this.category = category;
        this.price = price;
        this.amount = amount;
        this.setActors(new HashSet<Actor>());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MovieCategory getCategory() {
        return category;
    }

    public void setCategory(MovieCategory category) {
        this.category = category;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public CartElement getCart() {
        return cart;
    }

    public void setCart(CartElement cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", actors=" + actors +
                '}';
    }
}
