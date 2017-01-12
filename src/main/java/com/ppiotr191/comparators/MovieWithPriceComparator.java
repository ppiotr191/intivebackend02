package com.ppiotr191.comparators;


import com.ppiotr191.classes.MovieWithPrice;

import java.util.Comparator;

public class MovieWithPriceComparator implements Comparator<MovieWithPrice> {


    @Override
    public int compare(MovieWithPrice o1, MovieWithPrice o2) {
        return o1.getPrice().compareTo(o2.getPrice());
    }
}
