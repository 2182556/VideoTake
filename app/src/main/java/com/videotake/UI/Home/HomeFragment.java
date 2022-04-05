package com.videotake.UI.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.videotake.Domain.MovieList;
import com.videotake.Logic.User.LoggedInUserViewModel;
import com.videotake.UI.Adapters.MovieListAdapter;
import com.videotake.UI.DetailPage.MovieDetailsViewModel;
import com.videotake.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private final String TAG_NAME = HomeFragment.class.getSimpleName();
    private HomeViewModel homeViewModel;
    private LoggedInUserViewModel loggedInUserViewModel;
    private MovieDetailsViewModel movieDetailsViewModel;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loggedInUserViewModel = new ViewModelProvider(this).get(LoggedInUserViewModel.class);
        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        MovieListAdapter mAdapter = new MovieListAdapter("HomeFragment",
                inflater.getContext(), loggedInUserViewModel,movieDetailsViewModel);
        RecyclerView mRecyclerView = binding.recyclerview;
        mRecyclerView.setAdapter(mAdapter);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        MovieList trendingList = homeViewModel.getTrendingMovieList();
        if (trendingList!=null) {
            mAdapter.setData(trendingList.getMovies());
        } else {
            homeViewModel.getTrendingMovies();
            homeViewModel.getTrendingListResult().observe(getViewLifecycleOwner(), result -> {
                if (result == null) { return; }
                if (result.getError() == null) {
                    mAdapter.setData(homeViewModel.getTrendingMovieList().getMovies());
                } else {
                    Log.d(TAG_NAME, "An error occurred when trying to load trending movies");
                }
            });
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
