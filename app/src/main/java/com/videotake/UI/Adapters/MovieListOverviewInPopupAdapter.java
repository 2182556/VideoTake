package com.videotake.UI.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.videotake.Domain.MovieList;
import com.videotake.Logic.User.EmptyResult;
import com.videotake.Logic.User.LoggedInUserViewModel;
import com.videotake.R;
import com.videotake.UI.Lists.ListOverviewFragmentDirections;

import java.util.List;
import java.util.Objects;

public class MovieListOverviewInPopupAdapter extends RecyclerView.Adapter<MovieListOverviewInPopupAdapter.MovieListViewHolder> {
    private final String TAG_NAME = MovieListOverviewInPopupAdapter.class.getSimpleName();
    private List<MovieList> allLists;
    private final LayoutInflater mInflater;
    private int movieId = 0;
    private LoggedInUserViewModel loggedInUserViewModel = null;
    private LifecycleOwner lifecycleOwner = null;

    public MovieListOverviewInPopupAdapter(Context context, int movieId, LoggedInUserViewModel loggedInUserViewModel, LifecycleOwner owner) {
        mInflater = LayoutInflater.from(context);
        this.movieId = movieId;
        this.loggedInUserViewModel = loggedInUserViewModel;
        this.lifecycleOwner = owner;
    }

    @NonNull
    @Override
    public MovieListOverviewInPopupAdapter.MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.add_movie_to_list_item,parent,false);
        return new MovieListViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListOverviewInPopupAdapter.MovieListViewHolder holder, int position) {
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
        final MovieListOverviewInPopupAdapter adapter;
        public final TextView titleList;
        public final ImageView menu;

        public MovieListViewHolder(@NonNull View itemView, MovieListOverviewInPopupAdapter adapter) {
            super(itemView);
            titleList = itemView.findViewById(R.id.rec_list_title);
            menu = itemView.findViewById(R.id.list_settings);
            this.adapter = adapter;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieList listToAddTo = allLists.get(getLayoutPosition());
                    loggedInUserViewModel.addMovieToList(listToAddTo.getListId(),movieId);
                }
            });
        }
    }
}
