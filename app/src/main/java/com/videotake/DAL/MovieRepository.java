package com.videotake.DAL;

import com.videotake.Domain.Movie;
import com.videotake.Domain.MovieList;

import java.util.concurrent.Executor;

public class MovieRepository {
    private static volatile MovieRepository instance;

    private final Executor executor;
    private MovieApiDAO movieDAO;

    private MovieList trendingMovies = null;

    private Movie movieById = null;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore

    // private constructor : singleton access
    private MovieRepository(MovieApiDAO movieDAO, Executor executor) {
        this.executor = executor;
        this.movieDAO = movieDAO;
    }

    public static MovieRepository getInstance(MovieApiDAO movieDAO, Executor executor) {
        if (instance == null) {
            instance = new MovieRepository(movieDAO,executor);
        }
        return instance;
    }

    private void setTrendingMovies(MovieList trendingMovies) {
        this.trendingMovies = trendingMovies;
    }

    public MovieList getTrendingMovieList(){
        return this.trendingMovies;
    }

    public void getTrendingMovies(final RepositoryCallback<MovieList> callback) {
        if (trendingMovies==null){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Result<MovieList> result = MovieApiDAO.getTrendingMovies();
                    if (result instanceof Result.Success) {
                        setTrendingMovies(((Result.Success<MovieList>) result).getData());
                    }
                    callback.onComplete(result);
                }
            });
        }
    }

    public void setMovieById(Movie movie) { this.movieById = movie; }

    public Movie getMovieByIdResultMovie() {
        return this.movieById;
    }

    public void getMovieById(int id, final RepositoryCallback<Movie> callback){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Result<Movie> result = MovieApiDAO.getMovieById(id);
                if (result instanceof Result.Success) {
                    setMovieById(((Result.Success<Movie>) result).getData());
                }
                callback.onComplete(result);
            }
        });
    }

    public void getVideoLinkAndReviews(Movie movie, final RepositoryCallback<Movie> callback){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Result<Movie> result = MovieApiDAO.getVideoLinkAndReviews(movie);
                callback.onComplete(result);
            }
        });
    }

}
