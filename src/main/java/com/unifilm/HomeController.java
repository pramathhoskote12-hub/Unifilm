package com.unifilm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private final TmdbService tmdbService;
    private final UserRepository userRepository;
    private final MovieListRepository movieListRepository;
    private final RatingRepository ratingRepository;

    public HomeController(
            TmdbService tmdbService,
            UserRepository userRepository,
            MovieListRepository movieListRepository,
            RatingRepository ratingRepository
    ) {
        this.tmdbService = tmdbService;
        this.userRepository = userRepository;
        this.movieListRepository = movieListRepository;
        this.ratingRepository = ratingRepository;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/api/movies/search")
    @ResponseBody
    public String searchMovies(
            @RequestParam String query
    ) {
        return tmdbService.searchMovies(query);
    }

    @GetMapping("/api/movies/{movieId}")
    @ResponseBody
    public String getMovieDetails(
            @PathVariable String movieId
    ) {
        return tmdbService.getMovieDetails(movieId);
    }

    @GetMapping("/movie/{movieId}")
    public String moviePage() {
        return "movie";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "search-results";
    }


    /* ---------- TEMPORARY USER ---------- */

    private User getTemporaryUser() {

        return userRepository
                .findByUsername("demoUser")
                .orElseGet(() ->
                        userRepository.save(
                                new User("demoUser")
                        )
                );

    }


    /* ---------- ADD TO MY LIST ---------- */

    @PostMapping("/api/my-list/add")
    @ResponseBody
    public String addToMyList(
            @RequestParam Long movieId,
            @RequestParam String title,
            @RequestParam(required = false) String posterPath
    ) {

        User user =
                getTemporaryUser();


        if (
                movieListRepository
                        .findByUserIdAndMovieId(
                                user.getId(),
                                movieId
                        )
                        .isPresent()
        ) {

            return "already";

        }


        MovieList movie =
                new MovieList(
                        user,
                        movieId,
                        title,
                        posterPath
                );


        movieListRepository.save(movie);


        return "added";

    }


    /* ---------- REMOVE FROM MY LIST ---------- */

    @PostMapping("/api/my-list/remove")
    @ResponseBody
    public String removeFromMyList(
            @RequestParam Long movieId
    ) {

        User user =
                getTemporaryUser();


        movieListRepository
                .findByUserIdAndMovieId(
                        user.getId(),
                        movieId
                )
                .ifPresent(
                        movieListRepository::delete
                );


        return "removed";

    }


    /* ---------- CHECK MY LIST ---------- */

    @GetMapping("/api/my-list/check")
    @ResponseBody
    public boolean checkMyList(
            @RequestParam Long movieId
    ) {

        User user =
                getTemporaryUser();


        return movieListRepository
                .findByUserIdAndMovieId(
                        user.getId(),
                        movieId
                )
                .isPresent();

    }

}