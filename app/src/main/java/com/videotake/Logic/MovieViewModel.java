package com.videotake.Logic;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.videotake.DAL.APIConnection;

public class MovieViewModel extends AndroidViewModel {
    private final String TAG_NAME = MovieViewModel.class.getSimpleName();

    public MovieViewModel(@NonNull Application application) {
        super(application);
    }

    public void requestSession(String username, String password) { new MovieViewModel.TrialAsyncTask(username, password).execute();}

    public void createList() { APIConnection.createList(); }


    private static class TrialAsyncTask extends AsyncTask<Void, Void, Void> {
        private final String TAG_NAME = MovieViewModel.TrialAsyncTask.class.getSimpleName();
        private final String username;
        private final String password;

        protected TrialAsyncTask(String username, String password){
            this.username = username;
            this.password = password;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            APIConnection.requestSession(username, password);
//            APIConnection.createList();
            return null;
        }
    }

}
