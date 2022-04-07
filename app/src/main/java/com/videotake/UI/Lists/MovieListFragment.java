package com.videotake.UI.Lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.videotake.Domain.MovieList;
import com.videotake.UI.ViewModels.LoggedInUserViewModel;
import com.videotake.UI.Adapters.MovieListUserAdapter;
import com.videotake.UI.ViewModels.MovieDetailsViewModel;
import com.videotake.databinding.FragmentMovieListBinding;

public class MovieListFragment extends Fragment {
    private final String TAG_NAME = MovieListFragment.class.getSimpleName();
    private LoggedInUserViewModel loggedInUserViewModel;
    private MovieDetailsViewModel movieDetailsViewModel;

    private FragmentMovieListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMovieListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        int movieListPosition = MovieListFragmentArgs.fromBundle(getArguments()).getMovieListId();
        loggedInUserViewModel = new ViewModelProvider(this).get(LoggedInUserViewModel.class);
        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        MovieList currentList = loggedInUserViewModel.getUserLists().get(movieListPosition);
        MovieListUserAdapter mAdapter = new MovieListUserAdapter(inflater.getContext(),
                loggedInUserViewModel, movieDetailsViewModel, currentList.getListId());
        RecyclerView mRecyclerView = binding.recyclerview;
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setData(currentList.getMovies());

        ImageView shareList = binding.shareButton;
        shareList.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);

            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "You should check my list! ");

            intent.putExtra(Intent.EXTRA_TEXT, "https://www.themoviedb.org/list/" + currentList.getListId());
            startActivity(Intent.createChooser(intent, "Share Via"));
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}
