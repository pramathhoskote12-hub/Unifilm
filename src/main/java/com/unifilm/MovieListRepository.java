package com.unifilm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieListRepository extends JpaRepository<MovieList, Long> {

    Optional<MovieList> findByUserIdAndMovieId(
            Long userId,
            Long movieId
    );

    List<MovieList> findByUserId(Long userId);

}