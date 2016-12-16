package com.ppiotr191.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;

/**
 * Created by pablo27 on 14.12.16.
 */
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String type;
    //private Set<Actor> actors;

    protected Movie(){}

    public Movie(String name, String type) {

        this.name = name;
        this.type = type;
        //this.actors = actors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //public Set<Actor> getActors() {
     //   return actors;
    //}

    //public void setActors(Set<Actor> actors) {
    //    this.actors = actors;
    //}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
