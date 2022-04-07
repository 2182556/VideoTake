package com.videotake.UI.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.videotake.DAL.Api.UserApiDAO;
import com.videotake.Domain.GuestUser;
import com.videotake.Domain.LoggedInUser;
import com.videotake.DAL.Repository.UserRepository;
import com.videotake.DAL.Repository.Result;
import com.videotake.DAL.Repository.EmptyResult;
import com.videotake.R;
import com.videotake.UI.Login.LoginFormState;
import com.videotake.VideoTake;

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

    public GuestUser getGuestUser() { return this.userRepository.getGuestUser(); }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<EmptyResult> getLoginResult() {
        return loginResult;
    }


    public void login(String username, String password) {
        userRepository.login(username, password, result -> {
            if (result instanceof Result.Success) {
                loginResult.postValue(new EmptyResult());
            } else {
                loginResult.postValue(new EmptyResult(R.string.login_failed));
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
        return password != null && password.trim().toCharArray().length>=4;
    }

    public void useAsGuest(){
        userRepository.useAsGuest(result -> {
            if (result instanceof Result.Success) {
                guestSessionResult.postValue(new EmptyResult());
            } else {
                guestSessionResult.postValue(new EmptyResult(R.string.login_failed));
            }
        });
    }

    public LiveData<EmptyResult> getGuestSessionResult() {
        return this.guestSessionResult;
    }
}
