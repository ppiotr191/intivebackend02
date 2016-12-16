package com.ppiotr191.repository;

import com.ppiotr191.entity.Movie;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by pablo27 on 15.12.16.
 */

public interface MovieRepository extends CrudRepository<Movie,Long> {
}
