package com.videotake.UI.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.videotake.DAL.MovieApiDAO;
import com.videotake.DAL.MovieRepository;
import com.videotake.DAL.RepositoryCallback;
import com.videotake.DAL.Result;
import com.videotake.Domain.MovieList;
import com.videotake.Logic.User.EmptyResult;
import com.videotake.R;
import com.videotake.VideoTake;

public class HomeViewModel extends ViewModel {
    private final String TAG_NAME = HomeViewModel.class.getSimpleName();
    private MovieApiDAO movieDAO;
    private MutableLiveData<EmptyResult> trendingListResult = new MutableLiveData<>();

    private MovieRepository movieRepository;
    private MovieList trendingMovies = null;

    public HomeViewModel() {
        this.movieRepository = MovieRepository.getInstance(new MovieApiDAO(), VideoTake.executorService);
    }

    public MovieList getTrendingMovieList(){
        return this.movieRepository.getTrendingMovieList();
    }

    public LiveData<EmptyResult> getTrendingListResult() {
        return trendingListResult;
    }

    public void getTrendingMovies() {
        movieRepository.getTrendingMovies(result -> {
            if (result instanceof Result.Success) {
                trendingMovies = ((Result.Success<MovieList>) result).getData();
                trendingListResult.postValue(new EmptyResult());
            } else {
                trendingListResult.postValue(new EmptyResult(R.string.getting_trending_movies_failed));
            }
        });
    }

}
