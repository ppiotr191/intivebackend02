package com.ppiotr191.classes;


import java.math.BigDecimal;
import java.util.List;

public class Rabate implements Promotion{

    @Override
    public void promote(List<MovieWithPrice> movies) {
        int amountOfHits = 0;
        MovieWithPrice lastMovie = null;

        for (MovieWithPrice movieWithPrice : movies){
            if ("newest".equals(movieWithPrice.getMovie().getCategory().getSlug())){
                lastMovie = movieWithPrice;
                movieWithPrice.setPrice(movieWithPrice.getPrice().multiply(new BigDecimal(0.75)));;
                ++amountOfHits;
            }
        }
        if (amountOfHits % 2 == 1 && lastMovie != null){
            lastMovie.setPrice(lastMovie.getMovie().getPrice());
        }
    }
}
