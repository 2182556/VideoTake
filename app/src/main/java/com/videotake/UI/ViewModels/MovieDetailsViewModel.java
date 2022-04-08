package com.videotake.UI.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.videotake.DAL.API.MovieAPIDAO;
import com.videotake.DAL.Repository.MovieRepository;
import com.videotake.DAL.Repository.Result;
import com.videotake.Domain.Movie;
import com.videotake.DAL.Repository.EmptyResult;
import com.videotake.R;
import com.videotake.VideoTake;

import java.util.List;

public class MovieDetailsViewModel extends ViewModel {
    private final String TAG_NAME = MovieDetailsViewModel.class.getSimpleName();
    private MovieAPIDAO movieDAO;
    private final MutableLiveData<EmptyResult> movieByIdResult = new MutableLiveData<>();
    private final MutableLiveData<EmptyResult> movieVideoLinkAndReviewsResult = new MutableLiveData<>();

    private final MovieRepository movieRepository;
    private List<Movie> currentList = null;
    private final Movie getMovieById = null;

    public MovieDetailsViewModel() {
        this.movieRepository = MovieRepository.getInstance(new MovieAPIDAO(), VideoTake.executorService);
    }

    public void setCurrentList(List<Movie> list) { this.movieRepository.setCurrentList(list); }

    public List<Movie> getCurrentList() { return this.movieRepository.getCurrentList(); }

    public Movie getMovieByIdResultMovie(){ return this.movieRepository.getMovieByIdResultMovie(); }

    public LiveData<EmptyResult> getMovieByIdResult() { return this.movieByIdResult; }

    public LiveData<EmptyResult> getVideoLinkAndReviewsResult() { return this.movieVideoLinkAndReviewsResult; }

    public void getMovieById(int id){
        movieRepository.getMovieById(id, result -> {
            if (result instanceof Result.Success) {
                movieByIdResult.postValue(new EmptyResult());
            } else {
                movieByIdResult.postValue(new EmptyResult(R.string.getting_movie_by_id_failed));
            }
        });
    }

    public void getVideoLinkAndReviews(Movie movie){
        movieRepository.getVideoLinkAndReviews(movie, result -> {
            if (result instanceof Result.Success) {
                movieVideoLinkAndReviewsResult.postValue(new EmptyResult());
            } else {
                movieVideoLinkAndReviewsResult.postValue(new EmptyResult(R.string.getting_videoLink_and_reviews_failed));
            }
        });
    }
}
