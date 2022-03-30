package com.videotake.Domain;

import java.util.Date;

public class Movie {

    private String MovieID;
    private String MovieName;
    private String Description;
    private String GenreID;
    private Double Rating;
    private Date ReleaseDate;

    public String getMovieName() {
        return MovieName;
    }

    public String getMovieDescription() {
        return Description;
    }

    public Double getRating() {
        return Rating;
    }

}
