package com.ppiotr191.services;

import com.ppiotr191.entity.CartElement;
import com.ppiotr191.entity.Movie;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public interface UserService {
    Set<CartElement> addToCart(long idUser, Map<Long, Integer> idsMovies);
    void removeFromCart(long idUser, Map<Long, Integer> idsMovies);
    BigDecimal finalizeOrder(long idUser);
    int countMoviesInCart(long idUser);
    int countHiredMovies(long idUser);
    void returnMovies(long idUser, Map<Long, Integer> idsMovies);
}
