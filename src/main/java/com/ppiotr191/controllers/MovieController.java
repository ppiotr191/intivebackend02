package com.ppiotr191.controllers;


import com.ppiotr191.entity.Actor;
import com.ppiotr191.entity.Movie;
import com.ppiotr191.entity.MovieCategory;
import com.ppiotr191.exceptions.NotFoundException;
import com.ppiotr191.repository.MovieCategoryRepository;
import com.ppiotr191.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
public class MovieController{

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieCategoryRepository movieCategoryRepository;

    @Autowired
    private CrudRepository<Actor,Long> actorRepository;

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public Iterable<Movie> index(Pageable pageable)
    {
        return movieRepository.findAll(pageable);
    }

    @RequestMapping(value = "/movies/category/{id}", method = RequestMethod.GET)
    public Iterable<Movie> allByCategory(@PathVariable("id") long id)
    {
        MovieCategory movieCategory = movieCategoryRepository.findOne(id);
        if (movieCategory == null){
            throw new NotFoundException("Movie Category");
        }
        return movieRepository.findAllByCategory(movieCategory);
    }
    @RequestMapping(value = "/movies/available", method = RequestMethod.GET)
    public Iterable<Movie> allByCategory()
    {
        return movieRepository.findByAmountGreaterThan(0);
    }
    @RequestMapping(value = "/movies/{id}", method = RequestMethod.GET)
    public Movie get(@PathVariable("id") long id) throws NotFoundException {
        Movie movie = movieRepository.findOne(id);
        if (movie == null){
            throw new NotFoundException("Movie");
        }
        return movieRepository.findOne(id);
    }
    @RequestMapping(value = "/movies/category/{id}", method = RequestMethod.POST)
    public Movie create(@PathVariable("id") int id_category, @RequestBody Movie movie) {

        MovieCategory category = movieCategoryRepository.findOne((long)id_category);
        if (category == null){
            throw new NotFoundException("Movie Category");
        }
        movie.setCategory(category);
        return movieRepository.save(movie);

    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.PUT)
    public Movie update(@PathVariable("id") long id, @RequestBody Movie movie) throws NotFoundException {
        Movie update = movieRepository.findOne(id);
        if (update == null) {
            throw new NotFoundException("Movie");
        }
        update.setName(movie.getName());
        update.setType(movie.getType());

        return movieRepository.save(update);

    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) throws NotFoundException {
        Movie movie = movieRepository.findOne(id);

        if (movie == null){
            throw new NotFoundException("Movie");
        }
        movieRepository.delete(id);
    }


    @RequestMapping(value = "/movies/{id}/add_actor/{id_actor}", method = RequestMethod.POST)
    public Actor addActor(@PathVariable("id") long id, @PathVariable("id_actor") long idActor) throws NotFoundException {

        Actor actor = actorRepository.findOne(idActor);
        Movie movie = movieRepository.findOne(id);

        if (actor == null){
            throw new NotFoundException("Actor");
        }
        if (movie == null) {
            throw new NotFoundException("Movie");
        }

        Set<Actor> actors = movie.getActors();
        actors.add(actor);
        movie.setActors(actors);
        movieRepository.save(movie);

        return actor;


    }
}
