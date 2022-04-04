package com.videotake.Logic.User;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.videotake.DAL.RepositoryCallback;
import com.videotake.DAL.UserApiDAO;
import com.videotake.Domain.GuestUser;
import com.videotake.Domain.LoggedInUser;
import com.videotake.DAL.UserRepository;
import com.videotake.DAL.Result;
import com.videotake.Logic.User.EmptyResult;
import com.videotake.Logic.User.LoginFormState;
import com.videotake.R;
import com.videotake.VideoTake;

import java.util.List;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<EmptyResult> loginResult = new MutableLiveData<>();
    private MutableLiveData<EmptyResult> guestSessionResult = new MutableLiveData<>();
    private UserRepository userRepository;

    public LoginViewModel() {
        this.userRepository = UserRepository.getInstance(new UserApiDAO(), VideoTake.executorService);
    }

    public LoggedInUser getLoggedInUser(){
        return this.userRepository.getLoggedInUser();
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<EmptyResult> getLoginResult() {
        return loginResult;
    }


    public void login(String username, String password) {
        userRepository.login(username, password, new RepositoryCallback<LoggedInUser>() {
            @Override
            public void onComplete(Result<LoggedInUser> result) {
                if (result instanceof Result.Success) {
                    LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                    loginResult.postValue(new EmptyResult());
                } else {
                    loginResult.postValue(new EmptyResult(R.string.login_failed));
                }
            }
        });
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        return username != null && !username.trim().isEmpty();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && !password.trim().isEmpty();
    }

    public void useAsGuest(){
        userRepository.useAsGuest(new RepositoryCallback<GuestUser>() {
            @Override
            public void onComplete(Result<GuestUser> result) {
                if (result instanceof Result.Success) {
                    GuestUser data = ((Result.Success<GuestUser>) result).getData();
                    guestSessionResult.postValue(new EmptyResult());
                } else {
                    guestSessionResult.postValue(new EmptyResult(R.string.login_failed));
                }
            }
        });
    }

    public LiveData<EmptyResult> getGuestSessionResult() {
        return this.guestSessionResult;
    }
}
