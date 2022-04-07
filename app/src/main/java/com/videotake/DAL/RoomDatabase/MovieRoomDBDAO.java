package com.videotake.DAL.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.videotake.Domain.Movie;

import java.util.List;

@Dao
public interface MovieRoomDBDAO {
    @Insert
    void insert(Movie movie);

    @Query("DELETE FROM movie_table")
    void deleteAll();

    @Query("SELECT * from movie_table ORDER BY movie_name ASC")
    LiveData<List<Movie>> getAllMovies();
}
