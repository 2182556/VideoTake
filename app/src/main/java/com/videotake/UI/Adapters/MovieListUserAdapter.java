package com.videotake.UI.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.videotake.Domain.Movie;
import com.videotake.UI.ViewModels.LoggedInUserViewModel;
import com.videotake.R;
import com.videotake.UI.ViewModels.MovieDetailsViewModel;
import com.videotake.UI.Lists.MovieListFragmentDirections;
import com.videotake.VideoTake;

import java.util.List;
import java.util.Objects;

public class MovieListUserAdapter extends
        RecyclerView.Adapter<MovieListUserAdapter.MovieViewHolder> {
    private final String TAG_NAME = MovieListUserAdapter.class.getSimpleName();
    private List<Movie> allMovies;
    private final LayoutInflater inflater;
    private String list_id = "0";
    private LoggedInUserViewModel loggedInUserViewModel = null;
    private MovieDetailsViewModel movieDetailsViewModel;

    public MovieListUserAdapter(Context context, MovieDetailsViewModel movieDetailsViewModel) {
        this.inflater = LayoutInflater.from(context);
        this.movieDetailsViewModel = movieDetailsViewModel;
    }

    public MovieListUserAdapter(Context context, LoggedInUserViewModel loggedInUserViewModel,
                            MovieDetailsViewModel movieDetailsViewModel, String list_id) {
        this.inflater = LayoutInflater.from(context);
        this.loggedInUserViewModel = loggedInUserViewModel;
        this.movieDetailsViewModel = movieDetailsViewModel;
        this.list_id = list_id;
    }

    @NonNull
    @Override
    public MovieListUserAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = inflater.inflate(R.layout.movielist_item,parent,false);
        return new MovieViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListUserAdapter.MovieViewHolder holder, int position) {
        Movie mCurrent = allMovies.get(position);
        holder.titleMovie.setText(mCurrent.getMovieName());
        holder.priceMovie.setText(String.valueOf(mCurrent.getVoteAverage()));
        holder.releasedateMovie.setText(mCurrent.getReleaseDate());
        Picasso.with(inflater.getContext())
                .load("https://image.tmdb.org/t/p/w500/" + mCurrent.getPosterPath())
                .into(holder.imgMovie);

        holder.addToListImage.setVisibility(View.VISIBLE);
        holder.addToListImage.setOnClickListener(view -> {
            LayoutInflater popUpInflater = (LayoutInflater) inflater.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            VideoTake.showAddToListPopup(popUpInflater,view, mCurrent.getMovieID(),loggedInUserViewModel,
                    TAG_NAME,inflater);
        });
        holder.removeFromListImage.setVisibility(View.VISIBLE);
        holder.removeFromListImage.setOnClickListener(view -> {
            loggedInUserViewModel.removeMovieFromList(list_id,mCurrent.getMovieID());
            loggedInUserViewModel.getRemoveMovieFromListResult().observe(Objects.requireNonNull(ViewTreeLifecycleOwner.get(view)), result -> {
                if (result == null) { return; }
                if (result.getError() == null) {
                    Toast.makeText(inflater.getContext(), "Successfully removed movie from list", Toast.LENGTH_LONG).show();
                    allMovies.remove(position);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(inflater.getContext(), "Could not remove movie from list", Toast.LENGTH_LONG).show();
                    Log.d(TAG_NAME, "An error occurred when trying to remove the movie from the list");
                }
            });
        });
    }

    public void setData(List<Movie> items) {
        Log.d(TAG_NAME,"Data updated");
        allMovies = items;
        movieDetailsViewModel.setCurrentList(allMovies);
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
        final MovieListUserAdapter adapter;
        public final TextView titleMovie;
        public final TextView priceMovie;
        public final TextView releasedateMovie;
        public ImageView imgMovie;
        public ImageView addToListImage;
        public ImageView removeFromListImage;

        public MovieViewHolder(@NonNull View itemView, MovieListUserAdapter adapter) {
            super(itemView);
            titleMovie = itemView.findViewById(R.id.rec_movie_title);
            priceMovie = itemView.findViewById(R.id.rec_movie_rating);
            imgMovie = itemView.findViewById(R.id.rec_movie_image);
            releasedateMovie = itemView.findViewById(R.id.rec_movie_releasedate);
            addToListImage = itemView.findViewById(R.id.add_to_list);
            removeFromListImage = itemView.findViewById(R.id.remove_from_list);
            this.adapter = adapter;

            itemView.setOnClickListener(v -> {
                MovieListFragmentDirections.ActionNavMovieListToMovieDetailPageFragment action =
                        MovieListFragmentDirections.actionNavMovieListToMovieDetailPageFragment(
                                getLayoutPosition(),allMovies.get(getLayoutPosition()).getMovieName());
                Navigation.findNavController(v).navigate(action);
            });
        }
    }
}
