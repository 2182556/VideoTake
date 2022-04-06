package com.videotake.DAL;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.videotake.Domain.Converters;
import com.videotake.Domain.Movie;
import com.videotake.Domain.MovieList;
import com.videotake.Domain.Review;

@Database(entities = {Movie.class, MovieList.class, Review.class, }, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MovieRoomDatabase extends RoomDatabase {

    public abstract MovieRoomDBDAO movieDao();
    private static MovieRoomDatabase INSTANCE;

    static MovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MovieRoomDatabase.class, "movie_database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
