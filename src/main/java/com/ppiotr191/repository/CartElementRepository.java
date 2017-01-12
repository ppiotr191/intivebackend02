package com.ppiotr191.repository;


import com.ppiotr191.entity.Actor;
import com.ppiotr191.entity.CartElement;
import com.ppiotr191.entity.Movie;
import com.ppiotr191.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartElementRepository extends CrudRepository<CartElement,Long> {
    CartElement findByUserAndMovie(User user, Movie movie);
    Iterable<CartElement> findAllByUser(User user);
}