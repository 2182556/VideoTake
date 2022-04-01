package com.videotake.DAL;

import android.util.Log;

import com.videotake.Domain.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MovieDAO extends DAO {
    private static final String TAG_NAME = MovieDAO.class.getSimpleName();

    public static List<Movie> getTrendingMovies(){
        List<Movie> movies = new ArrayList<>();
        Request request = new Request.Builder()
                .url(BASE_URL + TRENDING + LIST + API_KEY)
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            JSONObject json_response = new JSONObject(body.string());
            Log.d(TAG_NAME,json_response.toString());
            if (json_response.getInt("total_results")>0){
                JSONArray movieArray = json_response.getJSONArray("results");
                for (int i=0; i<movieArray.length(); i++){
                    JSONObject json = movieArray.getJSONObject(i);
                    JSONArray genres_json = json.getJSONArray("genres");
                    List<String> genres = new ArrayList<>();
                    for (int j=0; j<genres_json.length(); j++){
                        JSONObject genre = genres_json.getJSONObject(j);
                        genres.add(genre.getString("name"));
                    }
                    Movie movie = new Movie(json.getInt("id"),json.getString("original_title"),
                            json.getString("overview"),json.getString("poster_path"),
                            json.getString("original_language"), genres,
                            new SimpleDateFormat("yyyy-MM-dd").parse(json.getString("release_date")),
                            json.getDouble("vote_average"));
                    movies.add(movie);
                }
                return movies;
            } else {
                Log.e(TAG_NAME, "No results found for trending");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
