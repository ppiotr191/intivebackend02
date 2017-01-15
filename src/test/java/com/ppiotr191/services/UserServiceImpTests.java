package com.ppiotr191.services;

import com.ppiotr191.entity.CartElement;
import com.ppiotr191.entity.Movie;
import com.ppiotr191.entity.MovieCategory;
import com.ppiotr191.entity.User;
import com.ppiotr191.repository.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.assertj.core.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImpTests {


    private User user;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Autowired
    private UserService userService;

    @Autowired
    private CrudRepository<User,Long> userRepository;

    @Autowired
    private CrudRepository<Movie,Long> movieRepository;

    @Autowired
    private MovieCategoryRepository movieCategoryRepository;

    @Autowired
    private CartElementRepository cartElementRepository;

    @Autowired
    private HiredMovieRepository hiredMovieRepository;
    @Test
    public void test1AddToCart() {

        Map<Long, Integer> idsMovies = new HashMap<Long, Integer>();

        Movie movieFirst = new Movie("Movie 1 for tests","test", new BigDecimal(10.01), movieCategoryRepository.findOne(new Long(1)),10);
        Movie movieSecond = new Movie("Movie 2 for tests","test", new BigDecimal(19.01), movieCategoryRepository.findOne(new Long(2)),10);
        Movie movieThird = new Movie("Movie 3 for tests","test", new BigDecimal(5.11), movieCategoryRepository.findOne(new Long(3)), 10);

        movieFirst = movieRepository.save(movieFirst);
        movieSecond = movieRepository.save(movieSecond);
        movieThird = movieRepository.save(movieThird);



        idsMovies.put(movieFirst.getId(), 1);
        idsMovies.put(movieSecond.getId(), 2);
        idsMovies.put(movieThird.getId(), 1);

        Set<CartElement> cartElements = userService.addToCart(user.getId(), idsMovies);

        assertThat(cartElements.size()).isEqualTo(3);
        assertThat(userService.countMoviesInCart(user.getId())).isEqualTo(4);
        Iterable<CartElement> cartElementsCheck = cartElementRepository.findAll();
        int size = 0;
        for(CartElement value : cartElementsCheck) {
            size++;
        }
        assertThat(size).isEqualTo(3);

        cartElementRepository.deleteAll();
        movieRepository.deleteAll();

    }

    @Test
    public void testRemoveFromCart(){

        Map<Long, Integer> idsMovies = new HashMap<Long, Integer>();
        int size = 0;
        Movie movieFirst = new Movie("Movie 1 for tests","test", new BigDecimal(10.01), movieCategoryRepository.findOne(new Long(1)),10);
        Movie movieSecond = new Movie("Movie 2 for tests","test", new BigDecimal(19.01), movieCategoryRepository.findOne(new Long(2)),10);
        Movie movieThird = new Movie("Movie 3 for tests","test", new BigDecimal(5.11), movieCategoryRepository.findOne(new Long(3)), 10);

        movieFirst = movieRepository.save(movieFirst);
        movieSecond = movieRepository.save(movieSecond);
        movieThird = movieRepository.save(movieThird);

        idsMovies.put(movieFirst.getId(), 1);
        idsMovies.put(movieSecond.getId(), 2);
        idsMovies.put(movieThird.getId(), 1);

        Set<CartElement> cartElements = userService.addToCart(user.getId(), idsMovies);
        Iterable<CartElement> cartElementsCheck = cartElementRepository.findAll();

        size = 0;
        for(CartElement value : cartElementsCheck) {
            size++;
        }
        assertThat(size).isEqualTo(3);

        Map<Long, Integer> idsMoviesToDelete = new HashMap<Long, Integer>();
        idsMoviesToDelete.put(movieFirst.getId(), 1);
        idsMoviesToDelete.put(movieSecond.getId(), 1);


        userService.removeFromCart(user.getId(), idsMoviesToDelete);
        Iterable<CartElement> cartElementsDeletedCheck = cartElementRepository.findAll();

        size = 0;
        for(CartElement value : cartElementsDeletedCheck) {
            size++;
        }

        assertThat(size).isEqualTo(2);

        cartElementRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    public void testFinalizeOrder(){
        Map<Long, Integer> idsMovies = new HashMap<Long, Integer>();
        Movie movieFirst = new Movie("Movie 1 for tests","test", new BigDecimal(10.01), movieCategoryRepository.findBySlug("newest"),10);
        Movie movieSecond = new Movie("Movie 2 for tests","test", new BigDecimal(19.01), movieCategoryRepository.findBySlug("hits"),10);
        Movie movieThird = new Movie("Movie 3 for tests","test", new BigDecimal(5.11), movieCategoryRepository.findBySlug("others"), 10);

        movieFirst = movieRepository.save(movieFirst);
        movieSecond = movieRepository.save(movieSecond);
        movieThird = movieRepository.save(movieThird);

        idsMovies.put(movieSecond.getId(), 1);
        idsMovies.put(movieThird.getId(), 1);

        Set<CartElement> cartElements = userService.addToCart(user.getId(), idsMovies);
        BigDecimal payout =  userService.finalizeOrder(user.getId());
        assertThat(payout).isEqualTo(new BigDecimal(24.12).setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    @Test
    public void testRabate(){


        Movie movieFirst = new Movie("Movie 1 for tests","test", new BigDecimal(10.01), movieCategoryRepository.findBySlug("newest"),10);
        Movie movieSecond = new Movie("Movie 2 for tests","test", new BigDecimal(19.01), movieCategoryRepository.findBySlug("newest"),10);
        Movie movieThird = new Movie("Movie 3 for tests","test", new BigDecimal(5.11), movieCategoryRepository.findBySlug("others"), 10);


        movieFirst = movieRepository.save(movieFirst);
        movieSecond = movieRepository.save(movieSecond);
        movieThird = movieRepository.save(movieThird);


        Map<Long, Integer> idsMovies = new HashMap<Long, Integer>();
        idsMovies.put(movieFirst.getId(), 1);
        idsMovies.put(movieSecond.getId(), 1);
        idsMovies.put(movieThird.getId(), 1);

        Set<CartElement> cartElements = userService.addToCart(user.getId(), idsMovies);

        BigDecimal payout =  userService.finalizeOrder(user.getId());
        assertThat(payout).isEqualTo(new BigDecimal(25.60).setScale(2, BigDecimal.ROUND_HALF_UP));
    }
    @Test
    public void testFreeMovie(){
        Movie movieFirst = new Movie("Movie 1 for tests","test", new BigDecimal(10.01), movieCategoryRepository.findBySlug("hits"),10);
        Movie movieSecond = new Movie("Movie 2 for tests","test", new BigDecimal(19.01), movieCategoryRepository.findBySlug("hits"),10);
        Movie movieFourth = new Movie("Movie 4 for tests","test", new BigDecimal(19.05), movieCategoryRepository.findBySlug("newest"),10);

        Movie movieThird = new Movie("Movie 3 for tests","test", new BigDecimal(5.11), movieCategoryRepository.findBySlug("others"), 10);

        movieFirst = movieRepository.save(movieFirst);
        movieSecond = movieRepository.save(movieSecond);
        movieThird = movieRepository.save(movieThird);
        movieFourth = movieRepository.save(movieFourth);


        Map<Long, Integer> idsMovies = new HashMap<Long, Integer>();
        idsMovies.put(movieFirst.getId(), 1);
        idsMovies.put(movieSecond.getId(), 1);
        idsMovies.put(movieThird.getId(), 1);
        idsMovies.put(movieFourth .getId(), 1);

        Set<CartElement> cartElements = userService.addToCart(user.getId(), idsMovies);

        BigDecimal payout =  userService.finalizeOrder(user.getId());
        assertThat(payout).isEqualTo(new BigDecimal(48.07).setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    @Before
    public void init(){
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        List<MovieCategory> categories = new ArrayList<MovieCategory>();
        MovieCategory newestCategory = new MovieCategory("Nowości", "newest");
        categories.add(newestCategory);
        MovieCategory hitsCategory = new MovieCategory("Hity", "hits");
        categories.add(hitsCategory);
        MovieCategory othersCategory = new MovieCategory("Pozostale", "others");
        categories.add(othersCategory);

        for (MovieCategory category : categories){
            movieCategoryRepository.save(category);
        }

        this.user = new User("Jan", "Kowalski");

        userRepository.save(user);
    }

    @After
    public void cleanUp(){
        hiredMovieRepository.deleteAll();
        cartElementRepository.deleteAll();
        movieRepository.deleteAll();
        movieCategoryRepository.deleteAll();

        System.setOut(null);
        System.setErr(null);
    }
}
