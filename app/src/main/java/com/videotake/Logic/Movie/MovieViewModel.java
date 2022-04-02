package com.videotake.Logic.Movie;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.videotake.DAL.MovieApiDAO;
import com.videotake.DAL.MovieRepository;
import com.videotake.DAL.RepositoryCallback;
import com.videotake.DAL.Result;
import com.videotake.Domain.Movie;
import com.videotake.Domain.MovieList;
import com.videotake.R;

public class MovieViewModel extends ViewModel {
    private final String TAG_NAME = MovieViewModel.class.getSimpleName();
    private MovieApiDAO movieDAO;
    private MutableLiveData<MovieResult> trendingListResult = new MutableLiveData<>();
    private MutableLiveData<MovieResult> movieByIdResult = new MutableLiveData<>();

    private MovieRepository movieRepository;
    private MovieList trendingMovies = null;
    private Movie getMovieById = null;

    MovieViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MovieList getTrendingMovieList(){
        return this.movieRepository.getTrendingMovieList();
    }

    public Movie getMovieByIdResultMovie(){ return this.movieRepository.getMovieByIdResultMovie(); }

    public LiveData<MovieResult> getMovieByIdResult() { return this.movieByIdResult; }

    public LiveData<MovieResult> getTrendingListResult() {
        return trendingListResult;
    }

    public void getTrendingMovies() {
        Log.d(TAG_NAME, "Attempting to get trending movies");
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

    public void getMovieById(int id){
        movieRepository.getMovieById(id, new RepositoryCallback<Movie>() {
            @Override
            public void onComplete(Result<Movie> result) {
                if (result instanceof Result.Success) {
                    movieByIdResult.postValue(new MovieResult());
                } else {
                    movieByIdResult.postValue(new MovieResult(R.string.getting_movie_by_id_failed));
                }
            }
        });
    }

}
