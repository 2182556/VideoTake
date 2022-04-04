package com.videotake.UI.Lists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.videotake.Domain.Movie;
import com.videotake.Domain.MovieList;
import com.videotake.Logic.User.LoggedInUserViewModel;
import com.videotake.Logic.User.LoginViewModel;
import com.videotake.UI.Adapters.MovieListAdapter;
import com.videotake.databinding.FragmentMovieListBinding;

import java.util.List;

public class MovieListFragment extends Fragment {
    private final String TAG_NAME = MovieListFragment.class.getSimpleName();
    private LoginViewModel loginViewModel;
    private LoggedInUserViewModel loggedInUserViewModel;
    private List<Movie> trendingMovies;

    private FragmentMovieListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMovieListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        int movieListPosition = MovieListFragmentArgs.fromBundle(getArguments()).getMovieListId();


        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loggedInUserViewModel = new ViewModelProvider(this).get(LoggedInUserViewModel.class);


        MovieListAdapter mAdapter = new MovieListAdapter("HomeFragment",inflater.getContext(), loggedInUserViewModel);
        RecyclerView mRecyclerView = binding.recyclerview;
        mRecyclerView.setAdapter(mAdapter);

        MovieList currentList = loggedInUserViewModel.getUserLists().get(movieListPosition);
        mAdapter.setData(currentList.getMovies());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}
