package com.videotake.UI.Main.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.videotake.Domain.Movie;
import com.videotake.UI.ViewModels.HomeViewModel;
import com.videotake.UI.ViewModels.LoggedInUserViewModel;
import com.videotake.UI.ViewModels.LoginViewModel;
import com.videotake.UI.Main.Adapters.MovieListAdapter;
import com.videotake.UI.ViewModels.MovieDetailsViewModel;
import com.videotake.databinding.FragmentSearchBinding;

import java.util.List;

public class SearchFragment extends Fragment {
    private final String TAG_NAME = SearchFragment.class.getSimpleName();
    public static final String ARG_OBJECT = "SEARCH";
    private HomeViewModel homeViewModel;
    private LoginViewModel loginViewModel;
    private MovieDetailsViewModel movieDetailsViewModel;
    private MovieListAdapter adapter;
    FragmentSearchBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        if (loginViewModel.getLoggedInUser()!=null){
            LoggedInUserViewModel loggedInUserViewModel = new ViewModelProvider(this).get(LoggedInUserViewModel.class);
            adapter = new MovieListAdapter(
                    inflater.getContext(), loggedInUserViewModel,movieDetailsViewModel, "Search");
        } else {
            adapter = new MovieListAdapter(inflater.getContext(),movieDetailsViewModel, "Search");
        }

        RecyclerView mRecyclerView = binding.recyclerview;
        mRecyclerView.setAdapter(adapter);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        List<Movie> searchResultList = homeViewModel.getSearchResultMovies();
        if (searchResultList!=null) {
            adapter.setData(searchResultList);
        } else {
            homeViewModel.getDiscoverMovies();
            homeViewModel.getDiscoverMovieResult().observe(getViewLifecycleOwner(), result -> {
                if (result == null) { return; }
                if (result.getError() == null) {
                    adapter.setData(homeViewModel.getDiscoverMovieList());
                } else {
                    Log.d(TAG_NAME, "An error occurred when trying to load movies");
                }
            });
        }

        SearchView search = binding.searchbar;

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                String queryText = search.getQuery().toString();
                query = query.replace(" ", "+");
                homeViewModel.getSearchResult(query);
                homeViewModel.getSearchResultResult().observe(getViewLifecycleOwner(), result -> {
                    if (result == null) { return; }
                    if (result.getError() == null) {
                        adapter.setData(homeViewModel.getSearchResultMovies());
                    } else {
                        Log.d(TAG_NAME, "An error occurred when trying to get search results");
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        return root;
    }

}
