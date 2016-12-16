package com.ppiotr191.repository;

import com.ppiotr191.entity.Actor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by pablo27 on 15.12.16.
 */

@Repository
public interface ActorRepository extends CrudRepository<Actor,Long> {
}
