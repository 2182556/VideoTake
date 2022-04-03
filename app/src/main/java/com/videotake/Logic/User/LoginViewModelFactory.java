package com.videotake.Logic.User;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.videotake.DAL.UserRepository;
import com.videotake.DAL.UserApiDAO;
import com.videotake.VideoTake;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(UserRepository.getInstance(new UserApiDAO(), VideoTake.executorService));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
