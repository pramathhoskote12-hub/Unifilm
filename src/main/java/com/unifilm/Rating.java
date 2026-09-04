package com.unifilm;

import jakarta.persistence.*;

@Entity
@Table(
        name = "ratings",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "movie_id"}
                )
        }
)
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "movie_id", nullable = false)
    private Long movieId;

    @Column(nullable = false)
    private Integer score;

    public Rating() {
    }

    public Rating(User user, Long movieId, Integer score) {
        this.user = user;
        this.movieId = movieId;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}