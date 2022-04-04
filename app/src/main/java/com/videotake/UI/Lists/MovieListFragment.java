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

import com.videotake.Domain.Movie;
import com.videotake.Domain.MovieList;
import com.videotake.Logic.Movie.MovieResult;
import com.videotake.Logic.Movie.MovieViewModel;
import com.videotake.Logic.Movie.MovieViewModelFactory;
import com.videotake.Logic.User.LoginViewModel;
import com.videotake.Logic.User.LoginViewModelFactory;
import com.videotake.UI.Adapters.MovieListAdapter;
import com.videotake.UI.DetailPage.MovieDetailPageFragmentArgs;
import com.videotake.UI.Home.HomeFragment;
import com.videotake.databinding.FragmentHomeBinding;
import com.videotake.databinding.FragmentMovieListBinding;

import java.util.List;

public class MovieListFragment extends Fragment {
    private final String TAG_NAME = MovieListFragment.class.getSimpleName();
    private MovieViewModel movieViewModel;
    private LoginViewModel loginViewModel;
    private List<Movie> trendingMovies;

    private FragmentMovieListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMovieListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        int movieListPosition = MovieListFragmentArgs.fromBundle(getArguments()).getMovieListId();


        MovieListAdapter mAdapter = new MovieListAdapter("MovieListFragment",inflater.getContext());
        RecyclerView mRecyclerView = binding.recyclerview;
        mRecyclerView.setAdapter(mAdapter);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        MovieList currentList = loginViewModel.getUserLists().get(movieListPosition);
        mAdapter.setData(currentList.getMovies());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}
