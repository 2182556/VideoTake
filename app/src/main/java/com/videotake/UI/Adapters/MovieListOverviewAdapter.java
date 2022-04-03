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

import com.videotake.Domain.MovieList;
import com.videotake.R;
import com.videotake.UI.Lists.ListOverviewFragmentDirections;

import java.util.List;

public class MovieListOverviewAdapter extends RecyclerView.Adapter<MovieListOverviewAdapter.MovieListViewHolder> {
    private final String TAG_NAME = MovieListOverviewAdapter.class.getSimpleName();
    private List<MovieList> allLists;
    private final LayoutInflater mInflater;

    public MovieListOverviewAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            }

    @NonNull
    @Override
    public MovieListOverviewAdapter.MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View mItemView = mInflater.inflate(R.layout.listoverview_item,parent,false);
            return new MovieListViewHolder(mItemView,this);
            }

    @Override
    public void onBindViewHolder(@NonNull MovieListOverviewAdapter.MovieListViewHolder holder, int position) {
        MovieList mCurrent = allLists.get(position);
        holder.titleList.setText(mCurrent.getListName());
    }

    public void setData(List<MovieList> items) {
        Log.d(TAG_NAME,"Data updated");
        allLists = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (allLists != null) {
            return allLists.size();
        } else {
            return 0;
        }
    }

    class MovieListViewHolder extends RecyclerView.ViewHolder {
        final MovieListOverviewAdapter adapter;
        public final TextView titleList;

        public MovieListViewHolder(@NonNull View itemView, MovieListOverviewAdapter adapter) {
            super(itemView);
            titleList = itemView.findViewById(R.id.rec_list_title);
            this.adapter = adapter;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListOverviewFragmentDirections.ActionNavListOverviewToNavMovieList action =
                            ListOverviewFragmentDirections.actionNavListOverviewToNavMovieList(getLayoutPosition());
                    Navigation.findNavController(v).navigate(action);
                }
            });
        }
    }
}
