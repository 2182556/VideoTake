package com.videotake.Logic.Movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.videotake.DAL.MovieApiDAO;
import com.videotake.DAL.MovieRepository;
import com.videotake.DAL.RepositoryCallback;
import com.videotake.DAL.Result;
import com.videotake.Domain.MovieList;
import com.videotake.R;

public class MovieViewModel extends ViewModel {
    private final String TAG_NAME = MovieViewModel.class.getSimpleName();
    private MovieApiDAO movieDAO;
    private MutableLiveData<MovieResult> trendingListResult = new MutableLiveData<>();

    private MovieRepository movieRepository;
    private MovieList trendingMovies = null;

    MovieViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

//    public LoggedInUser getLoggedInUser(){
//        return this.movieRepository.getLoggedInUser();
//    }

    public MovieList getTrendingMovieList(){
        return this.movieRepository.getTrendingMovieList();
    }

    public LiveData<MovieResult> getTrendingListResult() {
        return trendingListResult;
    }

    public void getTrendingMovies() {
        movieRepository.getTrendingMovies(new RepositoryCallback<MovieList>() {
            @Override
            public void onComplete(Result<MovieList> result) {
                if (result instanceof Result.Success) {
                    trendingMovies = ((Result.Success<MovieList>) result).getData();
                    trendingListResult.postValue(new MovieResult());
                } else {
                    trendingListResult.postValue(new MovieResult(R.string.getting_trending_movies_failed));
                }
            }
        });
    }

}
