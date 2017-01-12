package com.ppiotr191;

import com.ppiotr191.entity.Movie;
import com.ppiotr191.entity.MovieCategory;
import com.ppiotr191.repository.MovieCategoryRepository;
import com.ppiotr191.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Intivebackend02Application {

	private static final Logger log = LoggerFactory.getLogger(Intivebackend02Application.class);


	public static void main(String[] args) {
		SpringApplication.run(Intivebackend02Application.class, args);
	}

	@Bean
	public CommandLineRunner onStart(MovieCategoryRepository movieCategoryRepository, MovieRepository movieRepository) {
		return (args) -> {

			List<MovieCategory> categories = new ArrayList<MovieCategory>();
			MovieCategory newestCategory = new MovieCategory("Nowości", "newest");
			categories.add(newestCategory);
			MovieCategory hitsCategory = new MovieCategory("Hity", "hits");
			categories.add(hitsCategory);
			MovieCategory othersCategory = new MovieCategory("Pozostale", "others");
			categories.add(othersCategory);

			for (MovieCategory category : categories){
				movieCategoryRepository.save(category);
				for (int i=1; i <= 10; ++i){
					Movie movie = new Movie("Movie " + i + " from " + category.getSlug() , "action", new BigDecimal(10.10),  category, 5);
					movieRepository.save(movie);
				}
			}
		};
	}
}
