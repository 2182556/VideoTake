package com.videotake.Domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Movie {

    private int movieID;
    private String movieName;
    private String description;
    private String posterPath;
    private String originalLanguage;
    private List<String> genres;
    private Date releaseDate;
    private Double rating;
    private String moviePath;

    public Movie(int movieID, String movieName, String description, String posterPath,
                 String originalLanguage, List<String> genres, Date releaseDate, Double rating) {
        this.movieID = movieID;
        this.movieName = movieName;
        this.description = description;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public String getMovieName() {
        return this.movieName;
    }

    public String getMovieDescription() {
        return this.description;
    }

    public Double getRating() {
        return this.rating;
    }

    public String getPosterPath() { return this.posterPath; }

    public int getMovieID() { return this.movieID; }

}
