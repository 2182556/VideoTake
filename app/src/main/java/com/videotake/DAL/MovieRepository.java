package com.videotake.DAL;

import com.videotake.Domain.Movie;
import com.videotake.Domain.MovieList;

import java.util.List;
import java.util.concurrent.Executor;

public class MovieRepository {
    private static volatile MovieRepository instance;

    private final Executor executor;
    private MovieApiDAO movieDAO;

    private MovieList trendingMovies = null;
    private List<Movie> currentList = null;

    private Movie movieById = null;

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

    public void setCurrentList(List<Movie> list) { this.currentList = list; }

    public List<Movie> getCurrentList() { return this.currentList; }

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
