package com.ppiotr191.repository;

import com.ppiotr191.entity.CartElement;
import com.ppiotr191.entity.Movie;
import com.ppiotr191.entity.MovieCategory;
import com.ppiotr191.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface MovieRepository extends PagingAndSortingRepository<Movie,Long> {
    Iterable<Movie> findAllByCategory(MovieCategory category);
    Iterable<Movie> findByAmountGreaterThan(Integer amount);
}
