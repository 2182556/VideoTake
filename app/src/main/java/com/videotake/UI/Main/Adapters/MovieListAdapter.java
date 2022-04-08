package com.videotake.UI.Main.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.videotake.Domain.Movie;
import com.videotake.UI.ViewModels.LoggedInUserViewModel;
import com.videotake.R;
import com.videotake.UI.ViewModels.MovieDetailsViewModel;
import com.videotake.UI.Main.Home.HomeFragmentDirections;
import com.videotake.VideoTake;

import java.util.List;

public class MovieListAdapter extends
        RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private final String TAG_NAME = MovieListAdapter.class.getSimpleName();
    private List<Movie> allMovies;
    private final LayoutInflater mInflater;
    private LoggedInUserViewModel loggedInUserViewModel = null;
    private MovieDetailsViewModel movieDetailsViewModel = null;
    private final String parent;

    public MovieListAdapter(Context context, MovieDetailsViewModel movieDetailsViewModel, String parent) {
        this.mInflater = LayoutInflater.from(context);
        this.movieDetailsViewModel = movieDetailsViewModel;
        this.parent = parent;
    }

    public MovieListAdapter(Context context, LoggedInUserViewModel loggedInUserViewModel,
                            MovieDetailsViewModel movieDetailsViewModel, String parent) {
        this.mInflater = LayoutInflater.from(context);
        this.loggedInUserViewModel = loggedInUserViewModel;
        this.movieDetailsViewModel = movieDetailsViewModel;
        this.parent = parent;
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
        holder.priceMovie.setText(String.valueOf(mCurrent.getVoteAverage()));
        holder.releasedateMovie.setText(mCurrent.getReleaseDate());
        Picasso.with(mInflater.getContext())
                .load("https://image.tmdb.org/t/p/w500/" + mCurrent.getPosterPath())
                .into(holder.imgMovie);

        if (loggedInUserViewModel!=null){
            holder.addToListImage.setVisibility(View.VISIBLE);
            holder.addToListImage.setOnClickListener(view -> {
                LayoutInflater popUpInflater = (LayoutInflater) mInflater.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                VideoTake.showAddToListPopup(popUpInflater,view, mCurrent.getMovieID(),loggedInUserViewModel,
                        TAG_NAME,mInflater);
            });
        }

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
                HomeFragmentDirections.ActionNavHomeToMovieDetailPageFragment action =
                        HomeFragmentDirections.actionNavHomeToMovieDetailPageFragment(
                                getLayoutPosition(),parent);
                Navigation.findNavController(v).navigate(action);
            });
        }
    }
}
