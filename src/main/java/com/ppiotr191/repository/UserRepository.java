package com.ppiotr191.repository;


import com.ppiotr191.entity.MovieCategory;
import com.ppiotr191.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
}
