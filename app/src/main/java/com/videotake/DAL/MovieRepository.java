package com.videotake.DAL;

import com.videotake.Domain.Movie;
import com.videotake.Domain.MovieList;

import java.util.List;
import java.util.concurrent.Executor;

public class MovieRepository {
    private static volatile MovieRepository instance;

    private final Executor executor;
    private MovieApiDAO movieDAO;

    private List<Movie> discoverMovies = null;
    private List<Movie> discoverFilterAndSortMovies = null;
    private List<Movie> searchResultMovies = null;
    private List<Movie> currentList = null;
    private String[] genreArray = null;

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

    private void setDiscoverMovies(List<Movie> discoverMovies) {
        this.discoverMovies = discoverMovies;
    }

    public List<Movie> getDiscoverMoviesList(){
        return this.discoverMovies;
    }

    public void getDiscoverMovies(final RepositoryCallback<List<Movie>> callback) {
        if (discoverMovies==null){
            executor.execute(() -> {
                Result<List<Movie>> result = MovieApiDAO.getDiscoverMovies();
                if (result instanceof Result.Success) {
                    List<Movie> movies = ((Result.Success<List<Movie>>) result).getData();
                    if (searchResultMovies==null) setSearchResultMovies(movies);
                    if (discoverFilterAndSortMovies==null) setDiscoverFilterAndSortMovies(movies);
                    setDiscoverMovies(((Result.Success<List<Movie>>) result).getData());
                }
                callback.onComplete(result);
            });
        }
    }

    private void setDiscoverFilterAndSortMovies(List<Movie> discoverFilterAndSortMovies) {
        this.discoverFilterAndSortMovies = discoverFilterAndSortMovies;
    }

    public List<Movie> getDiscoverFilterAndSortMoviesList(){
        return this.discoverFilterAndSortMovies;
    }

    public void getDiscoverFilterAndSortMovies(String sortingChoice, boolean adult, String releaseYear,
                                               String minRating, String maxRating, String genre,
                                               final RepositoryCallback<List<Movie>> callback) {
        executor.execute(() -> {
            Result<List<Movie>> result = MovieApiDAO.getMoviesWithFilterAndSort(sortingChoice, adult,
                    releaseYear, minRating, maxRating, genre);
            if (result instanceof Result.Success) {
                setDiscoverFilterAndSortMovies(((Result.Success<List<Movie>>) result).getData());
            }
            callback.onComplete(result);
        });
    }

    public void setSearchResultMovies(List<Movie> movies) { this.searchResultMovies = movies; }

    public List<Movie> getSearchResultMovies() { return this.searchResultMovies; }

    public void getSearchResult(String query, final RepositoryCallback<List<Movie>> callback) {
        executor.execute(() -> {
            Result<List<Movie>> result = MovieApiDAO.getSearchResult(query);
            if (result instanceof Result.Success) {
                setSearchResultMovies(((Result.Success<List<Movie>>) result).getData());
            }
            callback.onComplete(result);
        });
    }

    public void setMovieById(Movie movie) { this.movieById = movie; }

    public Movie getMovieByIdResultMovie() {
        return this.movieById;
    }

    public void getMovieById(int id, final RepositoryCallback<Movie> callback){
        executor.execute(() -> {
            Result<Movie> result = MovieApiDAO.getMovieById(id);
            if (result instanceof Result.Success) {
                setMovieById(((Result.Success<Movie>) result).getData());
            }
            callback.onComplete(result);
        });
    }

    public void getVideoLinkAndReviews(Movie movie, final RepositoryCallback<Movie> callback){
        executor.execute(() -> {
            Result<Movie> result = MovieApiDAO.getVideoLinkAndReviews(movie);
            callback.onComplete(result);
        });
    }


}
