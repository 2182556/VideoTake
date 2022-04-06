package com.videotake.Domain;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {

    @TypeConverter
    public static ArrayList<Movie> toMovie(String value) {

    }

    @TypeConverter
    public static String toString(ArrayList<Movie> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
