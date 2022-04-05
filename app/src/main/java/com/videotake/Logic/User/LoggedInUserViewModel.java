package com.videotake.Logic.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.videotake.DAL.MovieApiDAO;
import com.videotake.DAL.MovieRepository;
import com.videotake.DAL.RepositoryCallback;
import com.videotake.DAL.Result;
import com.videotake.DAL.UserApiDAO;
import com.videotake.DAL.UserRepository;
import com.videotake.Domain.MovieList;
import com.videotake.Logic.User.EmptyResult;
import com.videotake.R;
import com.videotake.VideoTake;

import java.util.List;

public class LoggedInUserViewModel extends ViewModel {
    private UserRepository userRepository;

    private MutableLiveData<EmptyResult> listsResult = new MutableLiveData<>();
    private MutableLiveData<EmptyResult> addListResult = new MutableLiveData<>();
    private MutableLiveData<EmptyResult> removeListResult = new MutableLiveData<>();
    private MutableLiveData<EmptyResult> addMovieToListResult = new MutableLiveData<>();
    private MutableLiveData<EmptyResult> removeMovieFromListResult = new MutableLiveData<>();

    public LoggedInUserViewModel() {
        this.userRepository = UserRepository.getInstance(new UserApiDAO(), VideoTake.executorService);
    }

    public List<MovieList> getUserLists(){ return userRepository.getUserLists(); }
    public LiveData<EmptyResult> getListsResult(){ return this.listsResult; }

    public void lists() {
        userRepository.lists(result -> {
            if (result instanceof Result.Success) {
                List<MovieList> data = ((Result.Success<List<MovieList>>) result).getData();
                listsResult.postValue(new EmptyResult());
            } else {
                listsResult.postValue(new EmptyResult(R.string.get_lists_failed));
            }
        });
    }

    public LiveData<EmptyResult> getAddListResult(){ return this.addListResult; }

    public void addList(String title, String description){
        userRepository.addList(title, description, result -> {
            if (result instanceof Result.Success) {
                addListResult.postValue(new EmptyResult());
            } else {
                addListResult.postValue(new EmptyResult(R.string.add_lists_failed));
            }
        });
    }

    public LiveData<EmptyResult> getRemoveListResult(){ return this.removeListResult; }

    public void removeList(String listId){
        userRepository.removeList(listId, result -> {
            if (result instanceof Result.Success) {
                removeListResult.postValue(new EmptyResult());
            } else {
                removeListResult.postValue(new EmptyResult(R.string.remove_list_failed));
            }
        });
    }

    public LiveData<EmptyResult> getAddMovieToListResult(){ return this.addMovieToListResult; }
    public void resetAddMovieToListResult(){ this.addMovieToListResult = new MutableLiveData<>(); }

    public void addMovieToList(String list_id, int movie_id){
        userRepository.addMovieToList(list_id,movie_id, result -> {
            if (result instanceof Result.Success) {
                addMovieToListResult.postValue(new EmptyResult());
            } else {
                addMovieToListResult.postValue(new EmptyResult(R.string.add_movie_to_list_failed));
            }
        });
    }

    public LiveData<EmptyResult> getRemoveMovieFromListResult(){ return this.removeMovieFromListResult; }

    public void removeMovieFromList(String list_id, int movie_id){
        userRepository.removeMovieFromList(list_id,movie_id, result -> {
            if (result instanceof Result.Success) {
                removeMovieFromListResult.postValue(new EmptyResult());
            } else {
                removeMovieFromListResult.postValue(new EmptyResult(R.string.add_movie_to_list_failed));
            }
        });
    }
}
