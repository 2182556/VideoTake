package com.videotake.UI.Home;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.videotake.DAL.MovieApiDAO;
import com.videotake.Domain.Movie;
import com.videotake.Logic.InputFilterMinMax;
import com.videotake.Logic.LoggedInUserViewModel;
import com.videotake.Logic.LoginViewModel;
import com.videotake.UI.Adapters.MovieListAdapter;
import com.videotake.UI.Adapters.MovieListUserAdapter;
import com.videotake.UI.DetailPage.MovieDetailsViewModel;
import com.videotake.databinding.FragmentDiscoverBinding;

import java.util.Arrays;
import java.util.List;

public class DiscoverFragment extends Fragment {
    private final String TAG_NAME = DiscoverFragment.class.getSimpleName();
    public static final String ARG_OBJECT = "DISCOVER";
    private HomeViewModel homeViewModel;
    private LoginViewModel loginViewModel;
    private MovieDetailsViewModel movieDetailsViewModel;
    private MovieListAdapter adapter;
    private String[] sortOptions = new String[]{};

    FragmentDiscoverBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        if (loginViewModel.getLoggedInUser()!=null){
            LoggedInUserViewModel loggedInUserViewModel = new ViewModelProvider(this).get(LoggedInUserViewModel.class);
            adapter = new MovieListAdapter(
                    inflater.getContext(), loggedInUserViewModel,movieDetailsViewModel, "Discover");
        } else {
            adapter = new MovieListAdapter(inflater.getContext(),movieDetailsViewModel, "Discover");
        }


        RecyclerView mRecyclerView = binding.recyclerview;
        mRecyclerView.setAdapter(adapter);
        Spinner genreSpinner = binding.genreSpinner;

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        List<Movie> discoverList = homeViewModel.getDiscoverFilterAndSortMoviesList();
        if (discoverList!=null) {
            adapter.setData(discoverList);
        } else {
            homeViewModel.getDiscoverMovies();
            homeViewModel.getDiscoverMovieResult().observe(getViewLifecycleOwner(), result -> {
                if (result == null) { return; }
                if (result.getError() == null) {
                    adapter.setData(homeViewModel.getDiscoverMovieList());
                    String[] genreArray = MovieApiDAO.getGenres();
                    Log.d(TAG_NAME, Arrays.toString(genreArray));
                    ArrayAdapter<String> aaGenre = new ArrayAdapter<>(inflater.getContext(),
                            android.R.layout.simple_spinner_item, genreArray);
                    aaGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    genreSpinner.setAdapter(aaGenre);
                } else {
                    Log.d(TAG_NAME, "An error occurred when trying to load movies");
                }
            });
        }

        Spinner sortSpinner = binding.sortSpinner;
        ArrayAdapter<String> aaSort = new ArrayAdapter<>(inflater.getContext(),
                android.R.layout.simple_spinner_item, MovieApiDAO.getSortOptions());
        aaSort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(aaSort);

        String[] genreArray = MovieApiDAO.getGenres();
        if (genreArray!=null){
            ArrayAdapter<String> aaGenre = new ArrayAdapter<>(inflater.getContext(),
                    android.R.layout.simple_spinner_item, genreArray);
            aaGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            genreSpinner.setAdapter(aaGenre);
        }

        EditText minRatingField = binding.minimumRating;
        EditText maxRatingField = binding.maximumRating;
        minRatingField.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});
        maxRatingField.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});

        ImageView expand_filter_options = binding.btnExpandFilterOptions;
        expand_filter_options.setOnClickListener(v -> {
            if (binding.hiddenView.getVisibility() == View.GONE){
                binding.hiddenView.setVisibility(View.VISIBLE);
            } else {
                binding.hiddenView.setVisibility(View.GONE);
            }
        });

        Button filter_button = binding.filterButton;
        filter_button.setOnClickListener(v -> {
            String yearInput = "";
            String genre = "";
            String minRating = "";
            String maxRating = "";
            String sort = "";
            EditText releaseYear = binding.releaseYear;
            if (releaseYear.getText()!=null) yearInput = releaseYear.getText().toString();
            if (genreSpinner.getSelectedItem()!=null) genre = genreSpinner.getSelectedItem().toString();
            if (minRatingField.getText()!=null) minRating = minRatingField.getText().toString();
            if (maxRatingField.getText()!=null) maxRating = maxRatingField.getText().toString();
            boolean adult = binding.adultSwitch.isChecked();
            if (sortSpinner.getSelectedItem()!=null) sort = sortSpinner.getSelectedItem().toString();
            homeViewModel.getDiscoverFilterAndSortMovies(sort,adult,yearInput,minRating,maxRating,genre);
            homeViewModel.getDiscoverFilterAndSortMoviesResult().observe(getViewLifecycleOwner(), result -> {
                if (result == null) { return; }
                if (result.getError() == null) {
                    adapter.setData(homeViewModel.getDiscoverFilterAndSortMoviesList());
                } else {
                    Log.d(TAG_NAME, "An error occurred when trying to load movies");
                }
            });

        });
        return root;
    }

}
