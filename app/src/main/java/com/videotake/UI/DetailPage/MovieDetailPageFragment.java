package com.videotake.UI.DetailPage;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
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
import com.videotake.UI.Home.HomeViewModel;
import com.videotake.UI.Home.HomeViewModelFactory;
import com.videotake.databinding.FragmentDetailPageBinding;
import com.videotake.databinding.FragmentHomeBinding;

import java.util.List;

public class MovieDetailPageFragment extends Fragment {
    private final String TAG_NAME = MovieDetailPageFragment.class.getSimpleName();
    private FragmentDetailPageBinding binding;
    private MovieViewModel movieViewModel;
    private Movie movie;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDetailPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        int moviePosition = MovieDetailPageFragmentArgs.fromBundle(getArguments()).getMovieId();

        //resources for strings
        Resources res = getResources();

        ImageView image = binding.mealImage;
        TextView title = binding.mealTitle;
        TextView description = binding.mealDescription;
        TextView price = binding.mealPrice;
        TextView spotsLeft = binding.mealSpotsleft;
        TextView allergenInfo = binding.mealAllergeninfo;
        TextView date = binding.mealDate;

        TextView cookName = binding.cookName;
        TextView cookCity = binding.cookCity;

        movieViewModel = new ViewModelProvider(this, new MovieViewModelFactory())
                .get(MovieViewModel.class);

        List<Movie> movies = movieViewModel.getTrendingMovieList().getMovies();
        if (movies!=null) {
            movie = movies.get(moviePosition);
            title.setText(movie.getMovieName());
            description.setText(movie.getMovieDescription());

                    //examples from shareameal
//        title.setText(meal.getTitle());
//        description.setText(meal.getDescription());
//        price.setText(String.format(res.getString(R.string.price_full_string),meal.getFormattedPrice()));
//        allergenInfo.setText(String.format(res.getString(R.string.allergeninfo_full_string),meal.getAllergens()));
//        date.setText(meal.getFormattedDatetime());
//        spotsLeft.setText(res.getString(R.string.spots_left)+meal.getSpotsLeft());
//
//        cookName.setText(meal.getCook().getFirstName() + " " + meal.getCook().getLastName());
//        cookCity.setText(meal.getCook().getCity());
        }


//        movieViewModel.getMovieById(movieId);
//        movieViewModel.getMovieByIdResult().observe(getViewLifecycleOwner(), new Observer<MovieResult>() {
//            @Override
//            public void onChanged(@Nullable MovieResult movieResult) {
//                if (movieResult == null) {
//                    return;
//                }
////                loadingProgressBar.setVisibility(View.GONE);
//                if (movieResult.getError() == null) {
//                    movie = movieViewModel.getMovieByIdResultMovie();
//                    title.setText(movie.getMovieName());
//                    description.setText(movie.getMovieDescription());
//
//                } else {
//                    Log.d(TAG_NAME, "An error occurred when trying to load movie with id: " + movieId);
////                    showLoginFailed(loginResult.getError());
//                }
////                setResult(Activity.RESULT_OK);
//            }
//        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
