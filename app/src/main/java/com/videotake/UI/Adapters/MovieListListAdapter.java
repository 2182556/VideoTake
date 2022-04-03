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
import com.videotake.Domain.MovieList;
import com.videotake.R;
import com.videotake.UI.Home.HomeFragmentDirections;
import com.videotake.UI.Lists.ListOverviewFragmentDirections;

import java.util.List;

public class MovieListListAdapter extends RecyclerView.Adapter<MovieListListAdapter.MovieListViewHolder> {
    private final String TAG_NAME = MovieListListAdapter.class.getSimpleName();
    private List<MovieList> allMovies;
    private final LayoutInflater mInflater;

    public MovieListListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            }

    @NonNull
    @Override
    public MovieListListAdapter.MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View mItemView = mInflater.inflate(R.layout.movielist_item,parent,false);
            return new MovieListViewHolder(mItemView,this);
            }

    @Override
    public void onBindViewHolder(@NonNull MovieListListAdapter.MovieListViewHolder holder, int position) {
//            Movie mCurrent = allMovies.get(position);
////            holder.titleMeal.setText(mCurrent.getMovieName());
////            holder.priceMeal.setText(String.valueOf(mCurrent.getRating()));
////            Picasso.with(mInflater.getContext())
////            .load("https://image.tmdb.org/t/p/original/" + mCurrent.getPosterPath())
////            .into(holder.imgMeal);
            }

    public void setData(List<MovieList> items) {
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

    class MovieListViewHolder extends RecyclerView.ViewHolder {
        final MovieListListAdapter adapter;
        public final TextView titleMeal;
        public final TextView priceMeal;
        public ImageView imgMeal;

        public MovieListViewHolder(@NonNull View itemView, MovieListListAdapter adapter) {
            super(itemView);
            titleMeal = itemView.findViewById(R.id.rec_movie_title);
            priceMeal = itemView.findViewById(R.id.rec_movie_rating);
            imgMeal = itemView.findViewById(R.id.rec_movie_image);
            this.adapter = adapter;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
    //                    allMovies.get(getLayoutPosition()).getMovieID())
                    ListOverviewFragmentDirections.ActionNavListOverviewToNavMovieList action =
                            ListOverviewFragmentDirections.actionNavListOverviewToNavMovieList(getLayoutPosition());
                    Navigation.findNavController(v).navigate(action);
                }
            });
        }
    }
}
