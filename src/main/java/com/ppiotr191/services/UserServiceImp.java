package com.ppiotr191.services;

import com.ppiotr191.classes.FreeMovie;
import com.ppiotr191.classes.MovieWithPrice;
import com.ppiotr191.classes.PriceCounter;
import com.ppiotr191.classes.Rabate;
import com.ppiotr191.entity.CartElement;
import com.ppiotr191.entity.HiredMovie;
import com.ppiotr191.entity.Movie;
import com.ppiotr191.entity.User;
import com.ppiotr191.exceptions.NotFoundException;
import com.ppiotr191.exceptions.NotValidDataException;
import com.ppiotr191.repository.CartElementRepository;
import com.ppiotr191.repository.HiredMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;


@Service
public class UserServiceImp implements UserService {

    @Value("${user.maximumItems}")
    private int amountOfMaximumMovies;
    @Autowired
    private CrudRepository<Movie,Long> movieRepository;
    @Autowired
    private CrudRepository<User,Long> userRepository;
    @Autowired
    private CartElementRepository cartElementRepository;
    @Autowired
    private HiredMovieRepository hiredMovieRepository;


    @Override
    @Transactional
    public Set<CartElement> addToCart(long idUser, Map<Long, Integer> idsMovies){

        User user = userRepository.findOne(idUser);
        if (user == null){
            throw new NotFoundException("User");
        }
        Set<CartElement> cartElements = new HashSet<CartElement>();
        for (Map.Entry<Long, Integer> entry : idsMovies.entrySet()){
            if (entry.getValue() < 0){
                throw new NotValidDataException("Amount of movie must be greater than zero");
            }
            Movie movie = movieRepository.findOne(entry.getKey());
            if (movie == null){
                throw new NotFoundException("Movie");
            }
            CartElement cartElement = cartElementRepository.findByUserAndMovie(user,movie);
            if (cartElement == null){
                cartElement = new CartElement(user, movie, entry.getValue());
            }
            else {
                cartElement.setAmount(cartElement.getAmount() + entry.getValue());
            }

            cartElements.add(cartElement);
            cartElementRepository.save(cartElement);
        }


        return cartElements;
    }

    @Override
    @Transactional
    public void removeFromCart(long idUser, Map<Long, Integer> idsMovies){
        User user = userRepository.findOne(idUser);
        if (user == null){
            throw new NotFoundException("User");
        }
        for (Map.Entry<Long, Integer> entry : idsMovies.entrySet()){
            if (entry.getValue() < 0){
                throw new NotValidDataException("Amount of movie must be greater than zero");
            }
            Movie movie = movieRepository.findOne(entry.getKey());

            if (movie == null){
                throw new NotFoundException("Movie");
            }
            CartElement cartElement = cartElementRepository.findByUserAndMovie(user,movie);

            if (cartElement != null){
                cartElement.setAmount(cartElement.getAmount() - entry.getValue());
                if (cartElement.getAmount() < 1){
                    cartElementRepository.delete(cartElement);
                }
            }
            else {
                throw new NotValidDataException("Movie in cart not exists");
            }


        }

    }

    @Override
    @Transactional
    public BigDecimal finalizeOrder(long idUser){



        User user = userRepository.findOne(idUser);
        if (user == null){
            throw new NotFoundException("User");
        }
        if (countHiredMovies(idUser) + countMoviesInCart(idUser) > amountOfMaximumMovies){
            throw new NotValidDataException("Maximum amount of movies is " + amountOfMaximumMovies);
        }
        Iterable<CartElement> cartElements = cartElementRepository.findAllByUser(user);

        List<MovieWithPrice> moviesWithPrice = new ArrayList<MovieWithPrice>();
        for (CartElement cartElement : cartElements){
            for (int i=0; i<cartElement.getAmount(); i++){
                moviesWithPrice.add(new MovieWithPrice(cartElement.getMovie()));
            }
            Movie movie = cartElement.getMovie();
            int movieAmount = movie.getAmount() - cartElement.getAmount();
            if (movieAmount < 0){
                throw new NotValidDataException("Amount of movie " + movie.getName() + " is lower than zero (amount:"+movie.getAmount()+", try to order : "+cartElement.getAmount()+")");
            }
            movie.setAmount(movie.getAmount() - cartElement.getAmount());

            HiredMovie hiredMovie = hiredMovieRepository.findByUserAndMovie(user, movie);
            if (hiredMovie == null){
                hiredMovie = new HiredMovie(user, cartElement.getMovie(), cartElement.getAmount());
            }
            else {
                hiredMovie.setAmount(hiredMovie.getAmount() + cartElement.getAmount());
            }

            hiredMovie = hiredMovieRepository.save(hiredMovie);
            user.addToHiredMovies(hiredMovie);
            cartElementRepository.delete(cartElement);
            userRepository.save(user);
        }

        PriceCounter priceCounter = new PriceCounter(moviesWithPrice);
        priceCounter.addPromotion(new Rabate()).addPromotion(new FreeMovie());


        return priceCounter.getPrice();
    }

    @Override
    public int countMoviesInCart(long idUser) {
        int amount = 0;
        User user = userRepository.findOne(idUser);
        Iterable<CartElement> cartElements = cartElementRepository.findAllByUser(user);

        for (CartElement cartElement : cartElements){
            amount += cartElement.getAmount();
        }
        return amount;
    }

    @Override
    public int countHiredMovies(long idUser) {
        int amount = 0;
        User user = userRepository.findOne(idUser);

        for (HiredMovie movie : user.getHiredMovies()){
            amount += movie.getAmount();
        }
        return amount;
    }

    @Override
    @Transactional
    public void returnMovies(long idUser, Map<Long, Integer> idsMovies) {
        User user = userRepository.findOne(idUser);

        int amount = 0;
        for (Map.Entry<Long, Integer> entry : idsMovies.entrySet()){
            if (entry.getValue() < 0){
                throw new NotValidDataException("Amount of movie must be greater than zero");
            }
            Movie movie = movieRepository.findOne(entry.getKey());
            if (movie == null){
                throw new NotFoundException("Movie");
            }
            HiredMovie hiredMovie = hiredMovieRepository.findByUserAndMovie(user,movie);
            if (hiredMovie != null){
                amount = hiredMovie.getAmount() - entry.getValue();
                if (amount < 0){
                    throw new NotValidDataException("Amount of movie must be greater than zero");
                }
                movie.setAmount(movie.getAmount() + entry.getValue());
                if (amount < 1){
                    hiredMovieRepository.delete(hiredMovie);
                }
                else {
                    hiredMovie.setAmount(amount);
                    hiredMovieRepository.save(hiredMovie);
                }

            }
            else {
                throw new NotValidDataException("User doesn't have this movie");
            }
        }
    }


}
