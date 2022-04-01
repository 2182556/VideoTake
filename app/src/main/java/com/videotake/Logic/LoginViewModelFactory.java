package com.videotake.Logic;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.videotake.DAL.LoginRepository;
import com.videotake.DAL.UserDAO;
import com.videotake.VideoTake;

import java.util.concurrent.Executor;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(LoginRepository.getInstance(new UserDAO(), VideoTake.executorService));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
