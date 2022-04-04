package com.videotake.Domain;

import java.util.ArrayList;
import java.util.List;

public class MovieList {

    private String listId;
    private String listName;
    private String listDescription;
    private List<Movie> movies;

    public MovieList(String listId, String listName, String listDescription) {
        this(listId, listName,listDescription,new ArrayList<>());
    }

    public MovieList(String listId, String listName, String listDescription, List<Movie> movies){
        this.listId = listId;
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

    public String getListId() {
        return this.listId;
    }
}
