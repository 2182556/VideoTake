package com.videotake.Domain;

import java.util.ArrayList;
import java.util.List;

public class MovieList {

    private String listName;
    private String listDescription;
    private List<Movie> movies;

    public MovieList(String listName, String listDescription) {
        this(listName,listDescription,new ArrayList<>());
    }

    public MovieList(String listName, String listDescription, List<Movie> movies){
        this.listName = listName;
        this.listDescription = listDescription;
        this.movies = movies;
    }

    public List<Movie> getMovies(){
        return this.movies;
    }

    public String getListName() {
        return listName;
    }

    public String getListDescription() {
        return listDescription;
    }
}
