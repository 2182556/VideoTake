package com.videotake.UI.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.videotake.DAL.MovieApiDAO;
import com.videotake.DAL.MovieRepository;
import com.videotake.DAL.Result;
import com.videotake.Domain.Movie;
import com.videotake.Logic.EmptyResult;
import com.videotake.R;
import com.videotake.VideoTake;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final String TAG_NAME = HomeViewModel.class.getSimpleName();
    private MovieApiDAO movieDAO;
    private MutableLiveData<EmptyResult> searchMoviesResult = new MutableLiveData<>();
    private MutableLiveData<EmptyResult> discoverMoviesResult = new MutableLiveData<>();
    private MutableLiveData<EmptyResult> discoverMoviesFilterAndSortResult = new MutableLiveData<>();
    private MutableLiveData<EmptyResult> genreListResult = new MutableLiveData<>();

    private MovieRepository movieRepository;

    public HomeViewModel() {
        this.movieRepository = MovieRepository.getInstance(new MovieApiDAO(), VideoTake.executorService);
    }

    public List<Movie> getDiscoverMovieList(){
        return this.movieRepository.getDiscoverMoviesList();
    }

    public LiveData<EmptyResult> getDiscoverMovieResult() {
        return discoverMoviesResult;
    }

    public void getDiscoverMovies() {
        movieRepository.getDiscoverMovies(result -> {
            if (result instanceof Result.Success) {
                discoverMoviesResult.postValue(new EmptyResult());
            } else {
                discoverMoviesResult.postValue(new EmptyResult(R.string.getting_movies_failed));
            }
        });
    }

    public List<Movie> getDiscoverFilterAndSortMoviesList(){ return this.movieRepository.getDiscoverFilterAndSortMoviesList(); }

    public LiveData<EmptyResult> getDiscoverFilterAndSortMoviesResult() {
        return discoverMoviesFilterAndSortResult;
    }

    public void getDiscoverFilterAndSortMovies(String sortingChoice, boolean adult, String releaseYear,
                                               String minRating, String maxRating, String genre) {
        movieRepository.getDiscoverFilterAndSortMovies(sortingChoice, adult, releaseYear, minRating,
                maxRating, genre, result -> {
            if (result instanceof Result.Success) {
                discoverMoviesFilterAndSortResult.postValue(new EmptyResult());
            } else {
                discoverMoviesFilterAndSortResult.postValue(new EmptyResult(R.string.getting_movies_failed));
            }
        });
    }

    public List<Movie> getSearchResultMovies(){ return this.movieRepository.getSearchResultMovies(); }

    public LiveData<EmptyResult> getSearchResultResult() {
        return searchMoviesResult;
    }

    public void getSearchResult(String query) {
        movieRepository.getSearchResult(query, result -> {
            if (result instanceof Result.Success) {
                searchMoviesResult.postValue(new EmptyResult());
            } else {
                searchMoviesResult.postValue(new EmptyResult(R.string.getting_movies_failed));
            }
        });
    }


}
