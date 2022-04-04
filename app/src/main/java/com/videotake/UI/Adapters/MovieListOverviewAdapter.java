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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.videotake.Domain.MovieList;
import com.videotake.Logic.Movie.MovieResult;
import com.videotake.Logic.User.LoginViewModel;
import com.videotake.Logic.User.LoginViewModelFactory;
import com.videotake.Logic.User.StringResult;
import com.videotake.R;
import com.videotake.UI.Lists.ListOverviewFragmentDirections;

import java.util.List;
import java.util.Objects;

public class MovieListOverviewAdapter extends RecyclerView.Adapter<MovieListOverviewAdapter.MovieListViewHolder> {
    private final String TAG_NAME = MovieListOverviewAdapter.class.getSimpleName();
    private List<MovieList> allLists;
    private final LayoutInflater mInflater;
    private int movieId = 0;
    private LoginViewModel loginViewModel = null;
    private LifecycleOwner lifecycleOwner = null;

    public MovieListOverviewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public MovieListOverviewAdapter(Context context, int movieId, LoginViewModel loginViewModel, LifecycleOwner owner) {
        mInflater = LayoutInflater.from(context);
        this.movieId = movieId;
        this.loginViewModel = loginViewModel;
        this.lifecycleOwner = owner;
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
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopupMenu(holder.menu, holder.getAdapterPosition());
            }
        });
    }

    public void showPopupMenu(View view, int position){
        PopupMenu popup = new PopupMenu(mInflater.getContext(), view, Gravity.END);
        MenuInflater inflater = popup.getMenuInflater();

        inflater.inflate(R.menu.menu_popup_list, popup.getMenu());

        //set menu item click listener here
        popup.setOnMenuItemClickListener(new PopupMenuListOnClickListener(position));
        popup.show();
    }

    static class PopupMenuListOnClickListener implements PopupMenu.OnMenuItemClickListener {
        int position;

        PopupMenuListOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.delete_list:
                    // ...
                    return true;
//                case R.id.edit:
//                    // ...
//                    return true;
//                case R.id.delete:
//                    // ...
//                    return true;
//                case R.id.favourite:
//                    // ...
//                    return true;
//                default:
            }
            return false;
        }
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
        public final ImageView menu;

        public MovieListViewHolder(@NonNull View itemView, MovieListOverviewAdapter adapter) {
            super(itemView);
            titleList = itemView.findViewById(R.id.rec_list_title);
            menu = itemView.findViewById(R.id.list_settings);
            this.adapter = adapter;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (movieId==0){
                        ListOverviewFragmentDirections.ActionNavListOverviewToNavMovieList action =
                                ListOverviewFragmentDirections.actionNavListOverviewToNavMovieList(getLayoutPosition(),allLists.get(getLayoutPosition()).getListName());
                        Navigation.findNavController(v).navigate(action);
                    } else {
                        MovieList listToAddTo = allLists.get(getLayoutPosition());
                        loginViewModel.addMovieToList(listToAddTo.getListId(),movieId);
                    }
                }
            });
        }
    }
}
