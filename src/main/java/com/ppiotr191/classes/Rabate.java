package com.ppiotr191.classes;


import java.math.BigDecimal;
import java.util.List;

public class Rabate implements Promotion{


    private int amountOfNewest(List<MovieWithPrice> movies){
        int amountOfNewest = 0;
        for (MovieWithPrice movieWithPrice : movies){
            if ("newest".equals(movieWithPrice.getMovie().getCategory().getSlug())){
                ++amountOfNewest;
            }
        }
        return amountOfNewest;
    }

    @Override
    public void promote(List<MovieWithPrice> movies) {
        int amountOfNewest = this.amountOfNewest(movies);

        if (amountOfNewest < 2){
            return;
        }

        for (MovieWithPrice movieWithPrice : movies){
            movieWithPrice.setPrice(movieWithPrice.getPrice().multiply(new BigDecimal(0.75)));;
        }
    }
}
