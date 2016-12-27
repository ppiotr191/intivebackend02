package com.ppiotr191.controllers;

import com.ppiotr191.entity.Actor;
import com.ppiotr191.entity.Movie;
import com.ppiotr191.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;


import java.util.Set;

@RestController
public class ActorController{


    @Autowired
    private CrudRepository<Movie,Long> movieRepository;

    @Autowired
    private CrudRepository<Actor,Long> actorRepository;

    @RequestMapping(value = "/actors", method = RequestMethod.GET)
    public Iterable<Actor> index()
    {
        return actorRepository.findAll();
    }

    @RequestMapping(value = "/actors/{id}", method = RequestMethod.GET)
    public Actor get(@PathVariable("id") long id) throws NotFoundException {
        Actor actor = actorRepository.findOne(id);
        if (actor == null){
            throw new NotFoundException("Actor");
        }
        return actorRepository.findOne(id);

    }
    @RequestMapping(value = "/actors", method = RequestMethod.POST)
    public Actor create(@RequestBody Actor actor) {
        return actorRepository.save(actor);
    }

    @RequestMapping(value = "/actors/{id}", method = RequestMethod.PUT)
    public Actor update(@PathVariable("id") long id, @RequestBody Actor actor) throws NotFoundException {
        Actor update = actorRepository.findOne(id);
        if (update == null){
            throw new NotFoundException("Actor");
        }
        update.setFirstName(actor.getFirstName());
        update.setLastName(actor.getLastName());
        return actorRepository.save(update);
    }

    @RequestMapping(value = "/actors/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) throws NotFoundException {
        Actor actor = actorRepository.findOne(id);
        if (actor == null){
            throw new NotFoundException("Actor");
        }

        actorRepository.delete(id);
    }

}
