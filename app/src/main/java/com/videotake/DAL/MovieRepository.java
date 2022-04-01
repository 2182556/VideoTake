package com.videotake.DAL;

import com.videotake.Domain.MovieList;

import java.util.concurrent.Executor;

public class MovieRepository {
    private static volatile MovieRepository instance;

    private final Executor executor;
    private MovieApiDAO movieDAO;

    private MovieList trendingMovies = null;

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
