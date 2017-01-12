package com.ppiotr191.classes;


import com.ppiotr191.entity.Movie;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PriceCounter {

    private List<MovieWithPrice> moviesWithPrice;
    private List<Promotion> promotions;

    public PriceCounter(List<MovieWithPrice> movieWithPriceList){
        this.moviesWithPrice = movieWithPriceList;
        this.promotions = new ArrayList<Promotion>();
    }

    public PriceCounter addPromotion(Promotion promotion){
        this.promotions.add(promotion);
        return this;
    }

    public BigDecimal getPrice(){

        for (Promotion promotion: this.promotions ){
            promotion.promote(this.moviesWithPrice);
        }

        BigDecimal result = new BigDecimal(0);
        for (MovieWithPrice movieWithPrice : this.moviesWithPrice){
            result = result.add(movieWithPrice.getPrice());
        }

        return result.setScale(2, BigDecimal.ROUND_HALF_UP);
    }


}
