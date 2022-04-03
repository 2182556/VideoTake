package com.videotake.UI.Home;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.videotake.DAL.MovieApiDAO;
import com.videotake.DAL.MovieRepository;
import com.videotake.DAL.RepositoryCallback;
import com.videotake.DAL.Result;
import com.videotake.Domain.LoggedInUser;
import com.videotake.Domain.Movie;
import com.videotake.Domain.MovieList;
import com.videotake.Logic.Movie.MovieResult;
import com.videotake.Logic.Movie.MovieViewModel;
import com.videotake.Logic.Movie.MovieViewModelFactory;
import com.videotake.Logic.User.LoggedInUserView;
import com.videotake.Logic.User.LoginViewModel;
import com.videotake.Logic.User.LoginViewModelFactory;
import com.videotake.R;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final String TAG_NAME = HomeViewModel.class.getSimpleName();
    private MovieApiDAO movieDAO;
    private MutableLiveData<MovieResult> trendingListResult = new MutableLiveData<>();

    private MovieRepository movieRepository;
    private MovieList trendingMovies = null;

    HomeViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

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
