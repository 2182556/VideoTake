package com.videotake.UI.Main.Adapters;

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
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.videotake.Domain.MovieList;
import com.videotake.UI.ViewModels.LoggedInUserViewModel;
import com.videotake.R;
import com.videotake.UI.Main.Lists.ListOverviewFragmentDirections;

import java.util.List;
import java.util.Objects;

public class MovieListOverviewAdapter extends RecyclerView.Adapter<MovieListOverviewAdapter.MovieListViewHolder> {
    private final String TAG_NAME = MovieListOverviewAdapter.class.getSimpleName();
    private List<MovieList> allLists;
    private final LayoutInflater mInflater;
    private LoggedInUserViewModel loggedInUserViewModel = null;

    public MovieListOverviewAdapter(Context context, LoggedInUserViewModel loggedInUserViewModel) {
        this.loggedInUserViewModel = loggedInUserViewModel;
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
        holder.menu.setOnClickListener(v -> showPopupMenu(holder.menu, holder.getAdapterPosition()));
    }

    public void showPopupMenu(View view, int position){
        PopupMenu popup = new PopupMenu(mInflater.getContext(), view, Gravity.END);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_popup_list, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenuListOnClickListener(position,view));
        popup.show();
    }

    class PopupMenuListOnClickListener implements PopupMenu.OnMenuItemClickListener {
        View view;
        int position;

        PopupMenuListOnClickListener(int position, View view) {
            this.view = view;
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.delete_list:
                    loggedInUserViewModel.removeList(allLists.get(position).getListId());
                    loggedInUserViewModel.getRemoveListResult().observe(Objects.requireNonNull(ViewTreeLifecycleOwner.get(view)), result -> {
                        if (result == null) {
                            return;
                        }
                        if (result.getError() == null) {
                            Log.d(TAG_NAME, "Deleted the list");
                            allLists.remove(position);
                            notifyDataSetChanged();
                        } else {
                            Log.d(TAG_NAME, "An error occurred when trying to delete the list");
                        }
                    });
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
            itemView.setOnClickListener(v -> {
                ListOverviewFragmentDirections.ActionNavListOverviewToNavMovieList action =
                        ListOverviewFragmentDirections.actionNavListOverviewToNavMovieList(getLayoutPosition(),allLists.get(getLayoutPosition()).getListName());
                Navigation.findNavController(v).navigate(action);
            });
        }
    }
}
