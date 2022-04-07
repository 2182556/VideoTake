package com.videotake.DAL.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.videotake.Domain.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
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
