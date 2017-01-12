package com.ppiotr191.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;
    private String lastName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @JsonBackReference
    private Set<HiredMovie> hiredMovies;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public User(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<HiredMovie> getHiredMovies() {
        return hiredMovies;
    }

    public void addToHiredMovies(HiredMovie hiredMovie){
        this.hiredMovies.add(hiredMovie);
    }
    public void setHiredMovies(Set<HiredMovie> hiredMovies) {
        this.hiredMovies = hiredMovies;
    }
}
