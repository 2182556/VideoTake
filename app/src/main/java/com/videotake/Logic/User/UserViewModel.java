package com.videotake.Logic.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.videotake.DAL.RepositoryCallback;
import com.videotake.DAL.Result;
import com.videotake.DAL.UserApiDAO;
import com.videotake.DAL.UserRepository;
import com.videotake.R;
import com.videotake.VideoTake;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;

    private MutableLiveData<EmptyResult> postRatingResult = new MutableLiveData<>();

    public UserViewModel() {
        this.userRepository = UserRepository.getInstance(new UserApiDAO(), VideoTake.executorService);
    }

    public LiveData<EmptyResult> getPostRatingResult(){ return this.postRatingResult; }

    public void postRating(int movie_Id, double rating){
        userRepository.postRating(movie_Id, rating, new RepositoryCallback<String>() {
            @Override
            public void onComplete(Result<String> result) {
                if (result instanceof Result.Success) {
                    postRatingResult.postValue(new EmptyResult());
                } else {
                    postRatingResult.postValue(new EmptyResult(R.string.post_rating_failed));
                }
            }
        });
    }
}
