package com.ppiotr191.repository;

import com.ppiotr191.entity.CartElement;
import com.ppiotr191.entity.HiredMovie;
import com.ppiotr191.entity.Movie;
import com.ppiotr191.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HiredMovieRepository extends CrudRepository<HiredMovie,Long> {
    HiredMovie findByUserAndMovie(User user, Movie movie);
}
