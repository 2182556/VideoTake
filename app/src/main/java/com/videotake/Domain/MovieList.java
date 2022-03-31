package com.videotake.Domain;

import java.util.ArrayList;
import java.util.List;

public class MovieList {

    private String listName;
    private List<Movie> movies;

    public MovieList() {
        this.movies = new ArrayList<>();
    }
}
