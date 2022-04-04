package com.videotake.UI.Lists;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.videotake.Domain.MovieList;
import com.videotake.Logic.User.LoggedInUserViewModel;
import com.videotake.Logic.User.LoginViewModel;
import com.videotake.R;
import com.videotake.UI.Adapters.MovieListOverviewAdapter;
import com.videotake.databinding.FragmentListOverviewBinding;

import java.util.List;

public class ListOverviewFragment extends Fragment {
    private final String TAG_NAME = ListOverviewFragment.class.getSimpleName();
    private LoginViewModel loginViewModel;
    private LoggedInUserViewModel loggedInUserViewModel;

    private FragmentListOverviewBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentListOverviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loggedInUserViewModel = new ViewModelProvider(this).get(LoggedInUserViewModel.class);

        MovieListOverviewAdapter mAdapter = new MovieListOverviewAdapter(inflater.getContext(), loggedInUserViewModel);
        RecyclerView mRecyclerView = binding.recyclerviewList;
        mRecyclerView.setAdapter(mAdapter);

        loggedInUserViewModel.lists();
        loggedInUserViewModel.getListsResult().observe(getViewLifecycleOwner(), result -> {
            if (result == null) {
                return;
            }
            if (result.getError() == null) {
                List<MovieList> allLists = loggedInUserViewModel.getUserLists();
                mAdapter.setData(allLists);
            } else {
                Log.d(TAG_NAME, "An error occurred when trying to load trending movies");
            }
        });

        FloatingActionButton addListButton = binding.addList;
        addListButton.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater popUpInflater = (LayoutInflater) inflater.getContext().getSystemService(inflater.getContext().LAYOUT_INFLATER_SERVICE);
                        View popupView = popUpInflater.inflate(R.layout.popup_window_add_list, null);
                        int width = LinearLayout.LayoutParams.MATCH_PARENT;
                        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
                        popupWindow.setElevation(20);
                        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                        final EditText listTitleEditText = popupView.findViewById(R.id.list_title);
                        final EditText listDescriptionEditText = popupView.findViewById(R.id.list_description);
                        final Button addListButton = popupView.findViewById(R.id.add_list_button);
                        addListButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loggedInUserViewModel.addList(listTitleEditText.getText().toString(),
                                        listDescriptionEditText.getText().toString());
                                loggedInUserViewModel.getAddListResult().
                                        observe(getViewLifecycleOwner(), result -> {
                                    if (result == null) {
                                        return;
                                    }
                                    if (result.getError() == null) {
                                        loggedInUserViewModel.lists();
                                        loggedInUserViewModel.getListsResult().
                                                observe(getViewLifecycleOwner(), result1 -> {
                                            if (result1 == null) {
                                                return;
                                            }
                                            if (result1.getError() == null) {
                                                List<MovieList> allLists = loggedInUserViewModel.getUserLists();
                                                mAdapter.setData(allLists);
                                            } else {
                                                Log.d(TAG_NAME, "An error occurred when trying to load trending movies");
                                            }
                                        });
                                        popupWindow.dismiss();
                                        Toast.makeText(getLayoutInflater().getContext(), "List added!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d(TAG_NAME, "An error occurred when trying to load trending movies");
                                    }
                                });
                            }
                        });

                    }
                });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
