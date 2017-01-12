package com.ppiotr191.classes;


import com.ppiotr191.comparators.MovieWithPriceComparator;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class FreeMovie implements Promotion{

    @Override
    public void promote(List<MovieWithPrice> movies) {
        int amountOfAllMovies = movies.size();

        Collections.sort(movies,new MovieWithPriceComparator());

        int amountOfFreeMovies = amountOfAllMovies / 3;
        int amountOfMoviesForPromotions = amountOfAllMovies % 3;

        int freeMovies = 0;

        for (MovieWithPrice movie : movies){
            if ("others".equals(movie.getMovie().getCategory().getSlug())){
                --amountOfFreeMovies;
                --amountOfMoviesForPromotions;
                if(amountOfFreeMovies < 0 || amountOfMoviesForPromotions < 0){
                    break;
                }
                movie.setPrice(new BigDecimal(0.00));
            }
        }
    }
}
