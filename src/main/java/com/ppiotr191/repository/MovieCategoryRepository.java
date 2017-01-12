package com.ppiotr191.repository;



import com.ppiotr191.entity.MovieCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCategoryRepository extends CrudRepository<MovieCategory,Long> {
    MovieCategory findBySlug(String slug);
}
