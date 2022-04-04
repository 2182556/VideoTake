package com.videotake.Logic.User;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.videotake.DAL.RepositoryCallback;
import com.videotake.Domain.GuestUser;
import com.videotake.Domain.LoggedInUser;
import com.videotake.DAL.UserRepository;
import com.videotake.DAL.Result;
import com.videotake.Domain.MovieList;
import com.videotake.Logic.Movie.MovieResult;
import com.videotake.R;

import java.util.List;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private MutableLiveData<GuestSessionResult> guestSessionResult = new MutableLiveData<>();
    private UserRepository userRepository;

    private MutableLiveData<MovieResult> listsResult = new MutableLiveData<>();
    private MutableLiveData<StringResult> addListResult = new MutableLiveData<>();
    private MutableLiveData<StringResult> removeListResult = new MutableLiveData<>();
    private MutableLiveData<StringResult> addMovieToListResult = new MutableLiveData<>();

    LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoggedInUser getLoggedInUser(){
        return this.userRepository.getLoggedInUser();
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public LiveData<StringResult> getAddListResult(){ return this.addListResult; }

    public LiveData<StringResult> getRemoveListResult(){ return this.removeListResult; }

    public LiveData<StringResult> getAddMovieToListResult(){ return this.addMovieToListResult; }

    public List<MovieList> getUserLists(){ return userRepository.getUserLists(); }

    public LiveData<MovieResult> getListsResult(){ return this.listsResult; }

    public void login(String username, String password) {
        userRepository.login(username, password, new RepositoryCallback<LoggedInUser>() {
            @Override
            public void onComplete(Result<LoggedInUser> result) {
                if (result instanceof Result.Success) {
                    LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                    loginResult.postValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
                } else {
                    loginResult.postValue(new LoginResult(R.string.login_failed));
                }
            }
        });
    }

    public void lists() {
        userRepository.lists( new RepositoryCallback<List<MovieList>>() {
            @Override
            public void onComplete(Result<List<MovieList>> result) {
                if (result instanceof Result.Success) {
                    List<MovieList> data = ((Result.Success<List<MovieList>>) result).getData();
                    listsResult.postValue(new MovieResult());
                } else {
                    listsResult.postValue(new MovieResult(R.string.get_lists_failed));
                }
            }
        });
    }

    public void addList(String title, String description){
        userRepository.addList(title, description, new RepositoryCallback<String>() {
            @Override
            public void onComplete(Result<String> result) {
                if (result instanceof Result.Success) {
                    addListResult.postValue(new StringResult());
                } else {
                    addListResult.postValue(new StringResult(R.string.add_lists_failed));
                }
            }
        });
    }

    public void removeList(String listId){
        userRepository.removeList(listId, new RepositoryCallback<String>() {
            @Override
            public void onComplete(Result<String> result) {
                if (result instanceof Result.Success) {
                    removeListResult.postValue(new StringResult());
                } else {
                    removeListResult.postValue(new StringResult(R.string.remove_list_failed));
                }
            }
        });
    }

    public void addMovieToList(String list_id, int movie_id){
        userRepository.addMovieToList(list_id,movie_id, new RepositoryCallback<String>() {
            @Override
            public void onComplete(Result<String> result) {
                if (result instanceof Result.Success) {
                    addMovieToListResult.postValue(new StringResult());
                } else {
                    addMovieToListResult.postValue(new StringResult(R.string.add_movie_to_list_failed));
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
                    guestSessionResult.postValue(new GuestSessionResult());
                } else {
                    guestSessionResult.postValue(new GuestSessionResult(R.string.login_failed));
                }
            }
        });
    }

    public LiveData<GuestSessionResult> getGuestSessionResult() {
        return this.guestSessionResult;
    }
}
