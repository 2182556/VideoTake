package com.videotake.UI.Home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.videotake.DAL.MovieApiDAO;
import com.videotake.DAL.MovieRepository;
import com.videotake.Logic.Movie.MovieViewModel;
import com.videotake.VideoTake;

public class HomeViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(MovieRepository.getInstance(new MovieApiDAO(), VideoTake.executorService));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
