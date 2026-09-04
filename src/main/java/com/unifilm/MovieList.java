package com.unifilm;

import jakarta.persistence.*;

@Entity
@Table(
        name = "movie_list",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "movie_id"}
                )
        }
)
public class MovieList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "movie_id", nullable = false)
    private Long movieId;

    private String title;

    private String posterPath;

    public MovieList() {
    }

    public MovieList(
            User user,
            Long movieId,
            String title,
            String posterPath
    ) {
        this.user = user;
        this.movieId = movieId;
        this.title = title;
        this.posterPath = posterPath;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}