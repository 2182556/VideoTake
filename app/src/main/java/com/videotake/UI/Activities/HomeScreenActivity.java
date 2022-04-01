package com.videotake.UI.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.videotake.Domain.LoggedInUser;
import com.videotake.Domain.Movie;
import com.videotake.Domain.MovieList;
import com.videotake.Logic.Movie.MovieResult;
import com.videotake.Logic.Movie.MovieViewModel;
import com.videotake.Logic.Movie.MovieViewModelFactory;
import com.videotake.Logic.User.LoggedInUserView;
import com.videotake.Logic.User.LoginResult;
import com.videotake.Logic.User.LoginViewModel;
import com.videotake.Logic.User.LoginViewModelFactory;
import com.videotake.R;
import com.videotake.UI.Adapters.MovieListAdapter;

import java.util.List;

public class HomeScreenActivity extends AppCompatActivity {
    private final String TAG_NAME = HomeScreenActivity.class.getSimpleName();
    private LoginViewModel loginViewModel;
    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MovieListAdapter mAdapter = new MovieListAdapter(this);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setAdapter(mAdapter);

        movieViewModel = new ViewModelProvider(this, new MovieViewModelFactory())
                .get(MovieViewModel.class);
        movieViewModel.getTrendingMovies();
        movieViewModel.getTrendingListResult().observe(this, new Observer<MovieResult>() {
            @Override
            public void onChanged(@Nullable MovieResult movieResult) {
                if (movieResult == null) {
                    return;
                }
//                loadingProgressBar.setVisibility(View.GONE);
                if (movieResult.getError() == null) {
                    Log.d(TAG_NAME, "Do we even get here?");
                    List<Movie> movies = movieViewModel.getTrendingMovieList().getMovies();
                    mAdapter.setData(movies);
                } else {
                    Log.d(TAG_NAME, "An error occurred when trying to load trending movies");
//                    showLoginFailed(loginResult.getError());
                }
                setResult(Activity.RESULT_OK);
            }
        });

        //checking if user got saved
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        try {
            LoggedInUserView user = loginViewModel.getLoginResult().getValue().getSuccess();
            LoggedInUser loggedInUser = loginViewModel.getLoggedInUser();
            Toast toast = Toast.makeText(mRecyclerView.getContext(), "The user: " +
                    loggedInUser.getUserId() + " " + loggedInUser.getDisplayName(), Toast.LENGTH_LONG);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //add items to action bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle selected items from action bar
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //save items when rotating application
        super.onSaveInstanceState(outState);
    }

}
