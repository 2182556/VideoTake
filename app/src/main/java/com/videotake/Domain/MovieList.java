package com.videotake.Domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "movie_list", primaryKeys = {"listID", "list_name"})
public class MovieList {

    @NonNull
    @ColumnInfo(name = "listID")
    private String listId;

    @NonNull
    @ColumnInfo(name = "list_name")
    private String listName;

    @ColumnInfo(name = "list_description")
    private String listDescription;

    //TODO, figure this bitch out
    @Ignore
    private List<Movie> movies;

    @Ignore
    public MovieList(String listId, String listName, String listDescription) {
        this(listId, listName,listDescription,new ArrayList<>());
    }

    public MovieList(@NonNull String listId, @NonNull String listName, String listDescription, List<Movie> movies){
        this.listId = listId;
        this.listName = listName;
        this.listDescription = listDescription;
        this.movies = movies;
    }

    public MovieList(String listId, String listName, String listDescription){
        this.listId = listId;
        this.listName = listName;
        this.listDescription = listDescription;
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
