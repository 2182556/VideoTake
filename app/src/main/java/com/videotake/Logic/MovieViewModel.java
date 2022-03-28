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

    public void trial() { new MovieViewModel.TrialAsyncTask().execute();}




    private static class TrialAsyncTask extends AsyncTask<Void, Void, Void> {
        private String TAG_NAME = MovieViewModel.TrialAsyncTask.class.getSimpleName();

        @Override
        protected Void doInBackground(Void... voids) {
            APIConnection.trial();
            return null;
        }
    }

}
