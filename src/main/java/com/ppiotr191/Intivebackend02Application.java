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
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableSwagger2
@EnableCaching
public class Intivebackend02Application {

	private static final Logger log = LoggerFactory.getLogger(Intivebackend02Application.class);


	public static void main(String[] args) {
		SpringApplication.run(Intivebackend02Application.class, args);
	}

	@Bean
	public Docket api() {

		Set<String> produces = new HashSet<String>();
		produces.add("application/json");
		produces.add("text/xml");
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.produces(produces)
				.apiInfo(apiInfo());
	}
	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo(
				"Rest api documentation",
				"Rest api documentation for Movie Rental",
				"1.0v",
				"Terms of service",
				"ppiotrowski91@outlook.com",
				"License of API",
				"API license URL");
		return apiInfo;
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
