package com.ppiotr191.repository;

import com.ppiotr191.entity.CartElement;
import com.ppiotr191.entity.Movie;
import com.ppiotr191.entity.MovieCategory;
import com.ppiotr191.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface MovieRepository extends PagingAndSortingRepository<Movie,Long> {
    @Cacheable(value = "movies", key = "#p0")
    Page<Movie> findAll(Pageable pageable);

    Iterable<Movie> findAllByCategory(MovieCategory category);
    Iterable<Movie> findByAmountGreaterThan(Integer amount);
}
