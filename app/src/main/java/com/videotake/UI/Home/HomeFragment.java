package com.videotake.UI.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.videotake.Domain.MovieList;
import com.videotake.Logic.User.LoggedInUserViewModel;
import com.videotake.Logic.User.LoginViewModel;
import com.videotake.UI.Adapters.MovieListAdapter;
import com.videotake.UI.DetailPage.MovieDetailsViewModel;
import com.videotake.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private final String TAG_NAME = HomeFragment.class.getSimpleName();
    private HomeViewModel homeViewModel;
    private LoginViewModel loginViewModel;
    private MovieDetailsViewModel movieDetailsViewModel;
    private MovieListAdapter adapter;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        if (loginViewModel.getLoggedInUser()!=null){
            LoggedInUserViewModel loggedInUserViewModel = new ViewModelProvider(this).get(LoggedInUserViewModel.class);
            adapter = new MovieListAdapter(
                    inflater.getContext(), loggedInUserViewModel,movieDetailsViewModel);
        } else {
            adapter = new MovieListAdapter(inflater.getContext(),movieDetailsViewModel);
        }


        RecyclerView mRecyclerView = binding.recyclerview;
        mRecyclerView.setAdapter(adapter);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        MovieList trendingList = homeViewModel.getTrendingMovieList();
        if (trendingList!=null) {
            adapter.setData(trendingList.getMovies());
        } else {
            homeViewModel.getTrendingMovies();
            homeViewModel.getTrendingListResult().observe(getViewLifecycleOwner(), result -> {
                if (result == null) { return; }
                if (result.getError() == null) {
                    adapter.setData(homeViewModel.getTrendingMovieList().getMovies());
                } else {
                    Log.d(TAG_NAME, "An error occurred when trying to load trending movies");
                }
            });
        }

        ImageView filter_button = binding.btnExpandFilterOptions;
        filter_button.setOnClickListener(v -> {
            if (binding.hiddenView.getVisibility() == View.GONE){
                binding.hiddenView.setVisibility(View.VISIBLE);
            } else {
                binding.hiddenView.setVisibility(View.GONE);
            }

        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
