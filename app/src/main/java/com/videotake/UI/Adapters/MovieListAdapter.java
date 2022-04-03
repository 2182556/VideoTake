package com.videotake.UI.Adapters;

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
import com.videotake.R;
import com.videotake.UI.Home.HomeFragmentDirections;

import java.util.List;

public class MovieListAdapter extends
        RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private final String TAG_NAME = MovieListAdapter.class.getSimpleName();
    private List<Movie> allMovies;
    private final LayoutInflater mInflater;

    public MovieListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
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
        holder.titleMeal.setText(mCurrent.getMovieName());
        holder.priceMeal.setText(String.valueOf(mCurrent.getRating()));
        Picasso.with(mInflater.getContext())
                .load("https://image.tmdb.org/t/p/original/" + mCurrent.getPosterPath())
                .into(holder.imgMeal);
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
        public final TextView titleMeal;
        public final TextView priceMeal;
        public ImageView imgMeal;

        public MovieViewHolder(@NonNull View itemView, MovieListAdapter adapter) {
            super(itemView);
            titleMeal = itemView.findViewById(R.id.rec_movie_title);
            priceMeal = itemView.findViewById(R.id.rec_movie_rating);
            imgMeal = itemView.findViewById(R.id.rec_movie_image);
            this.adapter = adapter;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    allMovies.get(getLayoutPosition()).getMovieID())
                    HomeFragmentDirections.ActionNavHomeToMovieDetailPageFragment action =
                            HomeFragmentDirections.actionNavHomeToMovieDetailPageFragment(
                                    getLayoutPosition());
                    Navigation.findNavController(v).navigate(action);
                }
            });
        }
    }
}
