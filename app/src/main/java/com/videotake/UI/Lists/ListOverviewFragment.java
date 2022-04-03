package com.videotake.UI.Lists;

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

import com.videotake.Domain.LoggedInUser;
import com.videotake.Domain.MovieList;
import com.videotake.Logic.Movie.MovieResult;
import com.videotake.Logic.Movie.MovieViewModel;
import com.videotake.Logic.Movie.MovieViewModelFactory;
import com.videotake.Logic.User.LoggedInUserView;
import com.videotake.Logic.User.LoginViewModel;
import com.videotake.Logic.User.LoginViewModelFactory;
import com.videotake.UI.Adapters.MovieListOverviewAdapter;
import com.videotake.databinding.FragmentHomeBinding;

import java.util.List;

public class ListOverviewFragment extends Fragment {
    private final String TAG_NAME = ListOverviewFragment.class.getSimpleName();
    private MovieViewModel movieViewModel;
    private LoginViewModel loginViewModel;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MovieListOverviewAdapter mAdapter = new MovieListOverviewAdapter(inflater.getContext());
        RecyclerView mRecyclerView = binding.recyclerview;
        mRecyclerView.setAdapter(mAdapter);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        loginViewModel.lists();
        loginViewModel.getListsResult().observe(getViewLifecycleOwner(), new Observer<MovieResult>() {
            @Override
            public void onChanged(@Nullable MovieResult movieResult) {
                if (movieResult == null) {
                    return;
                }
                if (movieResult.getError() == null) {
                    List<MovieList> allLists = loginViewModel.getUserLists();
                    mAdapter.setData(allLists);
                } else {
                    Log.d(TAG_NAME, "An error occurred when trying to load trending movies");
                }
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
