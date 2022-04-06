package com.videotake;

import android.app.Application;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.videotake.Logic.LoggedInUserViewModel;
import com.videotake.UI.Adapters.MovieListOverviewInPopupAdapter;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VideoTake extends Application {
    public static ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static void showAddToListPopup(LayoutInflater popUpInflater, View view, int movieId,
                                          LoggedInUserViewModel loggedInUserViewModel, String TAG_NAME,
                                          LayoutInflater inflater){
        View popupView = popUpInflater.inflate(R.layout.popup_window_add_movie_to_list, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.setElevation(20);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        MovieListOverviewInPopupAdapter mAdapter = new MovieListOverviewInPopupAdapter(popupView.getContext(),
                movieId, loggedInUserViewModel,ViewTreeLifecycleOwner.get(view));
        RecyclerView mRecyclerView = popupView.findViewById(R.id.recyclerview_list);
        mRecyclerView.setAdapter(mAdapter);

        loggedInUserViewModel.lists();
        loggedInUserViewModel.getListsResult().observe(Objects.requireNonNull(ViewTreeLifecycleOwner.get(view)), result -> {
            if (result == null) { return; }
            if (result.getError() == null) {
                mAdapter.setData(loggedInUserViewModel.getUserLists());
            } else {
                Log.d(TAG_NAME, "An error occurred when trying to load user lists");
            }
        });

        loggedInUserViewModel.getAddMovieToListResult().observe(Objects.requireNonNull(ViewTreeLifecycleOwner.get(view)), result -> {
            if (result == null) { return; }
            if (result.getError() == null) {
                Toast.makeText(inflater.getContext(), "Succesfully added movie to list!", Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            } else {
                Toast.makeText(inflater.getContext(), "Could not add movie to list", Toast.LENGTH_LONG).show();
                Log.d(TAG_NAME, "An error occurred when trying add the movie to the list");
            }
            loggedInUserViewModel.resetAddMovieToListResult();
        });
        final Button cancelButton = popupView.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> popupWindow.dismiss());
    }
}
