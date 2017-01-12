package com.ppiotr191.classes;


import com.ppiotr191.entity.Movie;

import java.math.BigDecimal;

public class MovieWithPrice {

    private Movie movie;
    private BigDecimal price;

    public MovieWithPrice(Movie movie) {
        this.movie = movie;
        this.price = movie.getPrice();
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
