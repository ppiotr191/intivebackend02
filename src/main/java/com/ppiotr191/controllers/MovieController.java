package com.ppiotr191.controllers;


import com.ppiotr191.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class MovieController{

    @Autowired
    private CrudRepository<Movie,Long> movieRepository;

    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    public Iterable<Movie> index()
    {
        return movieRepository.findAll();
    }

    @RequestMapping(value = "/movie/{id}", method = RequestMethod.GET)
    public Movie get(@PathVariable("id") long id)
    {
        return movieRepository.findOne(id);
    }
    @RequestMapping(value = "/movie", method = RequestMethod.POST)
    public Movie create(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    @RequestMapping(value = "/movie/{id}", method = RequestMethod.PUT)
    public Movie update(@PathVariable("id") long id, @RequestBody Movie movie) {
        Movie update = movieRepository.findOne(id);
        update.setName(movie.getName());
        update.setType(movie.getType());

        return movieRepository.save(update);
    }

    @RequestMapping(value = "/movie{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        movieRepository.delete(id);
    }

}
