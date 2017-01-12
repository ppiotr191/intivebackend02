package com.ppiotr191.controllers;

import com.ppiotr191.entity.*;
import com.ppiotr191.exceptions.NotFoundException;
import com.ppiotr191.exceptions.NotValidDataException;
import com.ppiotr191.repository.CartElementRepository;
import com.ppiotr191.repository.UserRepository;
import com.ppiotr191.services.UserService;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Set;

@RestController
public class UserController {



    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartElementRepository cartElementRepository;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Iterable<User> index()
    {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User get(@PathVariable("id") long id) throws NotFoundException {
        User user = userRepository.findOne(id);
        if (user == null){
            throw new NotFoundException("User");
        }
        return user;

    }
    @RequestMapping(value = "/users/{id}/movies", method = RequestMethod.GET)
    public Iterable<HiredMovie> getMovies(@PathVariable("id") long id) throws NotFoundException {
        User user = userRepository.findOne(id);
        if (user == null){
            throw new NotFoundException("User");
        }
        return user.getHiredMovies();

    }
    @RequestMapping(value = "/users/{id}/movies_in_cart", method = RequestMethod.GET)
    public Iterable<CartElement> getMoviesInCart(@PathVariable("id") long id) throws NotFoundException {
        User user = userRepository.findOne(id);
        if (user == null){
            throw new NotFoundException("User");
        }

        return cartElementRepository.findAllByUser(user);

    }
    @RequestMapping(value = "/users/{id}/return_movies", method = RequestMethod.POST)
    public void returnMovies(@PathVariable("id") long id, @RequestBody HashMap<Long,Integer> idsMovies) {
        userService.returnMovies(id, idsMovies);
    }
    @RequestMapping(value = "/users/{id}/add_to_cart", method = RequestMethod.POST)
    public Set<CartElement> addToCart(@PathVariable("id") long id, @RequestBody HashMap<Long,Integer> idsMovies) {
        return userService.addToCart(id, idsMovies);
    }
    @RequestMapping(value = "/users/{id}/remove_from_cart", method = RequestMethod.DELETE)
    public void removeFromCart(@PathVariable("id") long id, @RequestBody HashMap<Long,Integer> idsMovies) {
        userService.removeFromCart(id, idsMovies);
    }
    @RequestMapping(value = "/users/{id}/finalize_order", method = RequestMethod.GET)
    public BigDecimal finalizeOrder(@PathVariable("id") long id) {




        return userService.finalizeOrder(id);
    }


    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public User create(@RequestBody @Valid User user) {
        return userRepository.save(user);
    }

}
