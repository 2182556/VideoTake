package com.videotake.UI.DetailPage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.videotake.DAL.MovieApiDAO;
import com.videotake.DAL.MovieRepository;
import com.videotake.DAL.RepositoryCallback;
import com.videotake.DAL.Result;
import com.videotake.Domain.Movie;
import com.videotake.Domain.MovieList;
import com.videotake.Logic.User.EmptyResult;
import com.videotake.R;
import com.videotake.VideoTake;

public class MovieDetailsViewModel extends ViewModel {
    private final String TAG_NAME = MovieDetailsViewModel.class.getSimpleName();
    private MovieApiDAO movieDAO;
    private final MutableLiveData<EmptyResult> movieByIdResult = new MutableLiveData<>();
    private final MutableLiveData<EmptyResult> movieVideoLinkAndReviewsResult = new MutableLiveData<>();

    private final MovieRepository movieRepository;
    private final MovieList trendingMovies = null;
    private final Movie getMovieById = null;

    public MovieDetailsViewModel() {
        this.movieRepository = MovieRepository.getInstance(new MovieApiDAO(), VideoTake.executorService);
    }

    public Movie getMovieByIdResultMovie(){ return this.movieRepository.getMovieByIdResultMovie(); }

    public LiveData<EmptyResult> getMovieByIdResult() { return this.movieByIdResult; }

    public LiveData<EmptyResult> getVideoLinkAndReviewsResult() { return this.movieVideoLinkAndReviewsResult; }


    public void getMovieById(int id){
        movieRepository.getMovieById(id, new RepositoryCallback<Movie>() {
            @Override
            public void onComplete(Result<Movie> result) {
                if (result instanceof Result.Success) {
                    movieByIdResult.postValue(new EmptyResult());
                } else {
                    movieByIdResult.postValue(new EmptyResult(R.string.getting_movie_by_id_failed));
                }
            }
        });
    }

    public void getVideoLinkAndReviews(Movie movie){
        movieRepository.getVideoLinkAndReviews(movie, new RepositoryCallback<Movie>() {
            @Override
            public void onComplete(Result<Movie> result) {
                if (result instanceof Result.Success) {
                    movieVideoLinkAndReviewsResult.postValue(new EmptyResult());
                } else {
                    movieVideoLinkAndReviewsResult.postValue(new EmptyResult(R.string.getting_movie_by_id_failed));
                }
            }
        });
    }
}
