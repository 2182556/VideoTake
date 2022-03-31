package com.videotake.Domain;

import java.util.Date;

public class Movie {

    private String movieID;
    private String movieName;
    private String description;
    private String genreID;
    private Double rating;
    private Date releaseDate;

    public String getMovieName() {
        return movieName;
    }

    public String getMovieDescription() {
        return description;
    }

    public Double getRating() {
        return rating;
    }

}
