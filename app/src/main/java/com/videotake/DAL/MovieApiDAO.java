package com.videotake.DAL;

import android.util.Log;

import com.videotake.Domain.Movie;
import com.videotake.Domain.MovieList;
import com.videotake.Domain.Review;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MovieApiDAO extends ApiDAO {
    private static final String TAG_NAME = MovieApiDAO.class.getSimpleName();
    private static final String YOUTUBE_PATH = "https://www.youtube.com/watch?v=";
    private static final String VIMEO_PATH = "https://vimeo.com/";
    private static final String THEMOVIEDB_URL = "https://www.themoviedb.org/";
    private static final String DISCOVER = "discover/movie";
    private static final String SORT = "&sort_by=";
    private static final String ADULT = "&include_adult=";
    private static final String RELEASE_YEAR = "&year=";
    private static final String MIN_RATING = "&vote_average.gte=";
    private static final String MAX_RATING = "&vote_average.lte=";
    private static final String GENRES = "&with_genres=";
    private static Map<Integer,String> genres;
    private static Map<String,String> sortOptions;
    private static String[] genreOptionsArray;

    public static String[] getSortOptions(){
        if (sortOptions==null){
            sortOptions = new LinkedHashMap<>();
            sortOptions.put("Popularity (desc)", "popularity.desc");
            sortOptions.put("Popularity (asc)", "popularity.asc");
            sortOptions.put("Rating (desc)", "vote_average.desc");
            sortOptions.put("Rating (asc)", "vote_average.asc");
            sortOptions.put("Title (desc)", "original_title.desc");
            sortOptions.put("Title (asc)", "original_title.asc");
            sortOptions.put("Release date (desc)", "release_date.desc");
            sortOptions.put("Release date (asc)", "release_date.asc");
        }
        List<String> keys = new ArrayList<>(sortOptions.keySet());
        String[] sortOptionsArray = new String[keys.size()];
        for (int i=0; i<sortOptions.size(); i++){ sortOptionsArray[i] = keys.get(i); }
        return sortOptionsArray;
    }

    public static String[] getGenres(){
        return genreOptionsArray;
    }

    public static void getGenresFromAPI(){
        Request genre_request = new Request.Builder()
                .url(BASE_URL + MOVIE_GENRES + API_KEY)
                .build();
        OkHttpClient client = new OkHttpClient();
        try (Response genre_response = client.newCall(genre_request).execute()) {
            ResponseBody genre_body = genre_response.body();
            JSONObject json_genre_response = new JSONObject(genre_body.string());
            JSONArray genreArray = json_genre_response.getJSONArray("genres");
            genres = new HashMap<>();
            for (int i=0; i<genreArray.length(); i++){
                JSONObject genre = genreArray.getJSONObject(i);
                genres.put(genre.getInt("id"),genre.getString("name"));
            }
            List<String> values = new ArrayList<>(genres.values());
            genreOptionsArray = new String[values.size()+1];
            genreOptionsArray[0] = "";
            for (int i=0; i<values.size(); i++){
                genreOptionsArray[i+1] = values.get(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Result<List<Movie>> getMoviesWithFilterAndSort(
            String sortingChoice, boolean adult, String releaseYear, String minRating, String maxRating, String genre){
        String genreId= "";
        for(Map.Entry<Integer,String> entry: genres.entrySet()){
            if(genre.equals(entry.getValue())){
                genreId = String.valueOf(entry.getKey());
                break;
            }
        }
        List<Movie> movies = getMovieList(BASE_URL + DISCOVER + API_KEY + SORT + sortOptions.get(sortingChoice) +
        ADULT + adult + RELEASE_YEAR + releaseYear + MIN_RATING + minRating + MAX_RATING + maxRating +
                GENRES + genreId);
        if (movies!=null){
            return new Result.Success<>(movies);
        } else {
            return new Result.Error(new IOException("Could not get movies when filtering"));
        }
    }

    public static Result<List<Movie>> getSearchResult(String query){
        List<Movie> movies = getMovieList(BASE_URL + SEARCH_MOVIE + API_KEY + QUERY_PARAM + query);
        if (movies!=null){
            return new Result.Success<>(movies);
        } else {
            return new Result.Error(new IOException("Could not get movies from search"));
        }
    }

    public static Result<List<Movie>> getDiscoverMovies(){
        List<Movie> movies = getMovieList(BASE_URL + DISCOVER + API_KEY);
        if (movies!=null){
            return new Result.Success<>(movies);
        } else {
            return new Result.Error(new IOException("Could not get movies using discover"));
        }
    }

    public static List<Movie> getMovieList(String url){
        try {
            List<Movie> movies;
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            OkHttpClient client = new OkHttpClient();
            try (Response response = client.newCall(request).execute()) {
                ResponseBody body = response.body();
                JSONObject json_response = new JSONObject(body.string());
                if (json_response.getInt("total_results")>0){
                    if (genres==null) { getGenresFromAPI(); }
                    JSONArray movieArray = json_response.getJSONArray("results");
                    movies = getListOfMoviesFromJSONArray(movieArray);
                    Log.d(TAG_NAME, "Successfully retrieved movies");
                    return movies;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Movie> getListOfMoviesFromJSONArray(JSONArray movieArray){
        List<Movie> movies = new ArrayList<>();
        try {
            for (int i=0; i<movieArray.length(); i++){
                JSONObject json = movieArray.getJSONObject(i);
                JSONArray genres_json = json.getJSONArray("genre_ids");
                List<String> movieGenres = new ArrayList<>();
                for (int j=0; j<genres_json.length(); j++){
                    int genre = genres_json.getInt(j);
                    movieGenres.add(genres.get(genre));
                }
                Movie movie = new Movie(json.getInt("id"), json.getString("original_title"),
                        json.getString("overview"), json.getString("poster_path"),
                        json.getString("original_language"), movieGenres,
                        json.getString("release_date"), json.getDouble("vote_average"),
                        json.getInt("vote_count"));
                movie.setShareableLink(THEMOVIEDB_URL + MOVIE + json.getInt("id"));
                movies.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static Result<Movie> getMovieById(int id){
        Movie movie = getMovieByIdInClass(id);
        if (movie!=null){
            return new Result.Success<>(movie);
        } else {
            return new Result.Error(new IOException("Could not retrieve movie with id: " + id));
        }
    }

    protected static Movie getMovieByIdInClass(int id){
        Movie movie = null;
        Request request = new Request.Builder()
                .url(BASE_URL + MOVIE + id + API_KEY + "&append_to_response=videos,reviews")
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            JSONObject json = new JSONObject(body.string());
            JSONArray genres_json = json.getJSONArray("genres");
            List<String> movieGenres = new ArrayList<>();
            for (int i = 0; i < genres_json.length(); i++) {
                JSONObject genre = genres_json.getJSONObject(i);
                movieGenres.add(genre.getString("name"));
            }
            movie = new Movie(json.getInt("id"), json.getString("original_title"),
                    json.getString("overview"), json.getString("poster_path"),
                    json.getString("original_language"), movieGenres,
                    json.getString("release_date"), json.getDouble("vote_average"),
                    json.getInt("vote_count"));
            movie.setShareableLink(THEMOVIEDB_URL + MOVIE + json.getInt("id"));
            JSONObject videos_object = json.getJSONObject("videos");
            JSONArray videos_json = videos_object.getJSONArray("results");
            for (int i = 0; i < videos_json.length(); i++) {
                JSONObject video = videos_json.getJSONObject(i);
                if (video.getBoolean("official") && video.getString("type").equals("Trailer")) {
                    if (video.getString("site").equals("Youtube")) {
                        movie.setVideoPath(YOUTUBE_PATH + video.getString("key"));
                        break;
                    } else if (video.getString("site").equals("Vimeo")) {
                        movie.setVideoPath(VIMEO_PATH + video.getString("key"));
                        break;
                    }
                }
            }
            JSONObject reviews_object = json.getJSONObject("reviews");
            JSONArray reviews_json = reviews_object.getJSONArray("results");
            List<Review> reviews = new ArrayList<>();
            for (int i = 0; i < reviews_json.length(); i++) {
                JSONObject review_json = reviews_json.getJSONObject(i);
                Review review = new Review(review_json.optString("id"), review_json.optString("author"),
                        review_json.optString("content"), review_json.optDouble("rating"));
                reviews.add(review);
            }
            movie.setReviews(reviews);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }

    public static Result<Movie> getVideoLinkAndReviews(Movie movie){
        Request request = new Request.Builder()
                .url(BASE_URL + MOVIE + movie.getMovieID() + API_KEY + "&append_to_response=videos,reviews")
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            JSONObject json = new JSONObject(body.string());
            JSONObject videos_object = json.getJSONObject("videos");
            JSONArray videos_json = videos_object.getJSONArray("results");
            for (int i = 0; i < videos_json.length(); i++) {
                JSONObject video = videos_json.getJSONObject(i);
                if (video.getBoolean("official") && video.getString("type").equals("Trailer")) {
                    if (video.getString("site").equals("Youtube")) {
                        movie.setVideoPath(YOUTUBE_PATH + video.getString("key"));
                        break;
                    } else if (video.getString("site").equals("Vimeo")) {
                        movie.setVideoPath(VIMEO_PATH + video.getString("key"));
                        break;
                    }
                }
            }
            JSONObject reviews_object = json.getJSONObject("reviews");
            JSONArray reviews_json = reviews_object.getJSONArray("results");
            List<Review> reviews = new ArrayList<>();
            for (int i = 0; i < reviews_json.length(); i++) {
                JSONObject review_json = reviews_json.getJSONObject(i);
                Review review = new Review(review_json.optString("id"), review_json.optString("author"),
                        review_json.optString("content"), review_json.optDouble("rating"));
                reviews.add(review);
            }
            movie.setReviews(reviews);
            return new Result.Success<>(movie);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Could not get video link and reviews", e));
        }
    }

    public static Result<Movie> fetchNewVoteCountAndVoteAverage(Movie movie){
        Request request = new Request.Builder()
                .url(BASE_URL + MOVIE + movie.getMovieID() + API_KEY)
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            JSONObject json = new JSONObject(body.string());
            movie.setVoteAverage(json.getDouble("vote_average"));
            movie.setVoteCount(json.getInt("vote_count"));
            return new Result.Success<>(movie);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Could not get video link and reviews", e));
        }
    }
}
