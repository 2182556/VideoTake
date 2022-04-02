package com.videotake.UI.Home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.videotake.Domain.LoggedInUser;
import com.videotake.Domain.Movie;
import com.videotake.Logic.Movie.MovieResult;
import com.videotake.Logic.Movie.MovieViewModel;
import com.videotake.Logic.Movie.MovieViewModelFactory;
import com.videotake.Logic.User.LoggedInUserView;
import com.videotake.Logic.User.LoginViewModel;
import com.videotake.Logic.User.LoginViewModelFactory;
import com.videotake.R;
import com.videotake.UI.Adapters.MovieListAdapter;
import com.videotake.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {
    private final String TAG_NAME = HomeFragment.class.getSimpleName();
    private HomeViewModel homeViewModel;
    private LoginViewModel loginViewModel;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        setContentView(R.layout.activity_home);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        MovieListAdapter mAdapter = new MovieListAdapter(inflater.getContext());
        RecyclerView mRecyclerView = binding.recyclerview;
        mRecyclerView.setAdapter(mAdapter);

        homeViewModel = new ViewModelProvider(this, new HomeViewModelFactory())
                .get(HomeViewModel.class);
        homeViewModel.getTrendingMovies();
        homeViewModel.getTrendingListResult().observe(getViewLifecycleOwner(), new Observer<MovieResult>() {
            @Override
            public void onChanged(@Nullable MovieResult movieResult) {
                if (movieResult == null) {
                    return;
                }
//                loadingProgressBar.setVisibility(View.GONE);
                if (movieResult.getError() == null) {
                    List<Movie> movies = homeViewModel.getTrendingMovieList().getMovies();
                    mAdapter.setData(movies);
                } else {
                    Log.d(TAG_NAME, "An error occurred when trying to load trending movies");
//                    showLoginFailed(loginResult.getError());
                }
//                setResult(Activity.RESULT_OK);
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

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
