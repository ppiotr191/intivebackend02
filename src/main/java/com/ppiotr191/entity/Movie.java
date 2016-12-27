package com.ppiotr191.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String type;

    @ManyToMany
    private Set<Actor> actors;

    public Movie(){
        this.setActors(new HashSet<Actor>());
    }

    public Movie(String name, String type) {

        this.name = name;
        this.type = type;
        this.setActors(new HashSet<Actor>());
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


    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", actors=" + actors +
                '}';
    }
}
