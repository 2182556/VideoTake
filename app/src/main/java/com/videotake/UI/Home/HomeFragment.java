package com.videotake.UI.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.videotake.Domain.Movie;
import com.videotake.Domain.MovieList;
import com.videotake.Logic.User.EmptyResult;
import com.videotake.Logic.User.LoggedInUserViewModel;
import com.videotake.Logic.User.LoginViewModel;
import com.videotake.UI.Adapters.MovieListAdapter;
import com.videotake.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {
    private final String TAG_NAME = HomeFragment.class.getSimpleName();
//    private HomeViewModel homeViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInUserViewModel loggedInUserViewModel;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loggedInUserViewModel = new ViewModelProvider(this).get(LoggedInUserViewModel.class);

        MovieListAdapter mAdapter = new MovieListAdapter("HomeFragment",
                inflater.getContext(), loggedInUserViewModel);
        RecyclerView mRecyclerView = binding.recyclerview;
        mRecyclerView.setAdapter(mAdapter);

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        MovieList trendingList = homeViewModel.getTrendingMovieList();
        if (trendingList!=null) {
            mAdapter.setData(trendingList.getMovies());
        } else {
            homeViewModel.getTrendingMovies();
            homeViewModel.getTrendingListResult().observe(getViewLifecycleOwner(), new Observer<EmptyResult>() {
                @Override
                public void onChanged(@Nullable EmptyResult result) {
                    if (result == null) {
                        return;
                    }
                    if (result.getError() == null) {
                        List<Movie> movies = homeViewModel.getTrendingMovieList().getMovies();
                        mAdapter.setData(movies);
                    } else {
                        Log.d(TAG_NAME, "An error occurred when trying to load trending movies");

                    }
                }
            });
        }

        //checking if user got saved

        try {
//            LoggedInUserView user = loginViewModel.getLoginResult().getValue().getSuccess();
//            LoggedInUser loggedInUser = loginViewModel.getLoggedInUser();
//            Toast toast = Toast.makeText(mRecyclerView.getContext(), "The user: " +
//                    loggedInUser.getUserId() + " " + loggedInUser.getDisplayName(), Toast.LENGTH_LONG);
//            toast.show();
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
