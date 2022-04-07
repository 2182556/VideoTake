package com.videotake.DAL;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.videotake.Domain.Movie;

import java.util.List;
import java.util.function.LongConsumer;

@Dao
public interface MovieDbDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertMovies(Movie movie);

    @Update
    public void updateMovies(Movie movie);

    @Delete
    public void deleteMovies(Movie movie);

    @Query("SELECT * FROM movie_table")
    List<Movie> getAll();
}
