package com.videotake.DAL;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.videotake.Domain.MovieList;

import java.util.List;

@Dao
public interface MovieListDAO {

    @Insert
    public MovieList addMovieList(MovieList movielist);

    @Update
    public void updateMovieList(MovieList movielist);

    @Delete
    public void deleteMovieList(MovieList movieList);

    @Query("SELECT * FROM movie_list_table")
    public List<MovieList> getMovieLists();
}
