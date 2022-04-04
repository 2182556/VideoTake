package com.videotake.UI.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.videotake.Domain.Movie;
import com.videotake.Domain.MovieList;
import com.videotake.Logic.User.EmptyResult;
import com.videotake.Logic.User.LoggedInUserViewModel;
import com.videotake.R;
import com.videotake.UI.Home.HomeFragmentDirections;
import com.videotake.UI.Lists.MovieListFragmentDirections;

import java.util.List;
import java.util.Objects;

public class MovieListAdapter extends
        RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private final String TAG_NAME = MovieListAdapter.class.getSimpleName();
    private List<Movie> allMovies;
    private final LayoutInflater mInflater;
    private String parentName = "";
    private LoggedInUserViewModel loggedInUserViewModel = null;

    public MovieListAdapter(String parentName, Context context) {
        this.parentName = parentName;
        this.mInflater = LayoutInflater.from(context);
    }

    public MovieListAdapter(String parentName, Context context, LoggedInUserViewModel loggedInUserViewModel) {
        this.parentName = parentName;
        this.mInflater = LayoutInflater.from(context);
        this.loggedInUserViewModel = loggedInUserViewModel;
    }

    @NonNull
    @Override
    public MovieListAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.movielist_item,parent,false);
        return new MovieViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListAdapter.MovieViewHolder holder, int position) {
        Movie mCurrent = allMovies.get(position);
        holder.titleMovie.setText(mCurrent.getMovieName());
        holder.priceMovie.setText(String.valueOf(mCurrent.getRating()));
        holder.releasedateMovie.setText(mCurrent.getReleaseDate());
        Picasso.with(mInflater.getContext())
                .load("https://image.tmdb.org/t/p/w500/" + mCurrent.getPosterPath())
                .into(holder.imgMovie);

        holder.addToListImage.setOnClickListener(view -> {
            LayoutInflater popUpInflater = (LayoutInflater) mInflater.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = popUpInflater.inflate(R.layout.popup_window_add_movie_to_list, null);
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
            popupWindow.setElevation(20);
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            MovieListOverviewAdapter mAdapter = new MovieListOverviewAdapter(popupView.getContext(),
                    mCurrent.getMovieID(), loggedInUserViewModel,ViewTreeLifecycleOwner.get(view));
            RecyclerView mRecyclerView = popupView.findViewById(R.id.recyclerview_list);
            mRecyclerView.setAdapter(mAdapter);

            loggedInUserViewModel.lists();
            loggedInUserViewModel.getListsResult().observe(Objects.requireNonNull(ViewTreeLifecycleOwner.get(view)), result -> {
                if (result == null) { return; }
                if (result.getError() == null) {
                    mAdapter.setData(loggedInUserViewModel.getUserLists());
                } else {
                    Log.d(TAG_NAME, "An error occurred when trying to load trending movies");
                }
            });

            loggedInUserViewModel.getAddMovieToListResult().observe(Objects.requireNonNull(ViewTreeLifecycleOwner.get(view)), result -> {
                if (result == null) { return; }
                if (result.getError() == null) {
                    Toast.makeText(mInflater.getContext(), "Succesfully added movie to list!", Toast.LENGTH_LONG).show();
                    popupWindow.dismiss();
                } else {
                    Toast.makeText(mInflater.getContext(), "Could not add movie to list", Toast.LENGTH_LONG).show();
                    Log.d(TAG_NAME, "An error occurred when trying add the movie to the list");
                }
                loggedInUserViewModel.resetAddMovieToListResult();
            });
            final Button cancelButton = popupView.findViewById(R.id.cancel_button);
            cancelButton.setOnClickListener(v -> popupWindow.dismiss());
        });

    }

    public void setData(List<Movie> items) {
        Log.d(TAG_NAME,"Data updated");
        allMovies = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (allMovies != null) {
            return allMovies.size();
        } else {
            return 0;
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        final MovieListAdapter adapter;
        public final TextView titleMovie;
        public final TextView priceMovie;
        public final TextView releasedateMovie;
        public ImageView imgMovie;
        public ImageView addToListImage;

        public MovieViewHolder(@NonNull View itemView, MovieListAdapter adapter) {
            super(itemView);
            titleMovie = itemView.findViewById(R.id.rec_movie_title);
            priceMovie = itemView.findViewById(R.id.rec_movie_rating);
            imgMovie = itemView.findViewById(R.id.rec_movie_image);
            releasedateMovie = itemView.findViewById(R.id.rec_movie_releasedate);
            addToListImage = itemView.findViewById(R.id.add_to_list);
            this.adapter = adapter;

            itemView.setOnClickListener(v -> {
                try {
                    HomeFragmentDirections.ActionNavHomeToMovieDetailPageFragment action =
                            HomeFragmentDirections.actionNavHomeToMovieDetailPageFragment(
                                    getLayoutPosition(),allMovies.get(getLayoutPosition()).getMovieName());
                    Navigation.findNavController(v).navigate(action);
                } catch (Exception e) {
                    try {
                        MovieListFragmentDirections.ActionNavMovieListToMovieDetailPageFragment action =
                                MovieListFragmentDirections.actionNavMovieListToMovieDetailPageFragment(
                                        getLayoutPosition(),allMovies.get(getLayoutPosition()).getMovieName());
                        Navigation.findNavController(v).navigate(action);
                    } catch (Exception e1) {
                        Log.d(TAG_NAME, "Unable to navigate to a detailpage");
                    }
                }
            });
        }
    }
}
