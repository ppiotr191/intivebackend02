package com.ppiotr191.entity;

import javax.persistence.*;

@Entity
public class CartElement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private User user ;
    @ManyToOne
    private Movie movie;
    int amount;

    public CartElement(User user, Movie movie, int amount){
        this.user = user;
        this.movie = movie;
        this.amount = amount;
    }

    public CartElement(){

    }

    public User getUser() {
        return user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}
