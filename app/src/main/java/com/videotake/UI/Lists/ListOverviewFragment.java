package com.videotake.UI.Lists;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.videotake.Domain.LoggedInUser;
import com.videotake.Domain.MovieList;
import com.videotake.Logic.Movie.MovieResult;
import com.videotake.Logic.Movie.MovieViewModel;
import com.videotake.Logic.Movie.MovieViewModelFactory;
import com.videotake.Logic.User.LoggedInUserView;
import com.videotake.Logic.User.LoginViewModel;
import com.videotake.Logic.User.LoginViewModelFactory;
import com.videotake.Logic.User.StringResult;
import com.videotake.R;
import com.videotake.UI.Adapters.MovieListOverviewAdapter;
import com.videotake.UI.MainActivity;
import com.videotake.databinding.FragmentHomeBinding;
import com.videotake.databinding.FragmentListOverviewBinding;

import java.util.List;

public class ListOverviewFragment extends Fragment {
    private final String TAG_NAME = ListOverviewFragment.class.getSimpleName();
    private MovieViewModel movieViewModel;
    private LoginViewModel loginViewModel;

    private FragmentListOverviewBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentListOverviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MovieListOverviewAdapter mAdapter = new MovieListOverviewAdapter(inflater.getContext());
        RecyclerView mRecyclerView = binding.recyclerviewList;
        mRecyclerView.setAdapter(mAdapter);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        loginViewModel.lists();
        loginViewModel.getListsResult().observe(getViewLifecycleOwner(), new Observer<MovieResult>() {
            @Override
            public void onChanged(@Nullable MovieResult movieResult) {
                if (movieResult == null) {
                    return;
                }
                if (movieResult.getError() == null) {
                    List<MovieList> allLists = loginViewModel.getUserLists();
                    mAdapter.setData(allLists);
                } else {
                    Log.d(TAG_NAME, "An error occurred when trying to load trending movies");
                }
            }
        });

        FloatingActionButton addListButton = binding.addToListFab;
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
                                loginViewModel.addList(listTitleEditText.getText().toString(),
                                        listDescriptionEditText.getText().toString());
                                loginViewModel.getAddListResult().observe(getViewLifecycleOwner(), new Observer<StringResult>() {
                                    @Override
                                    public void onChanged(@Nullable StringResult stringResult) {
                                        if (stringResult == null) {
                                            return;
                                        }
                                        if (stringResult.getError() == null) {
                                            loginViewModel.lists();
                                            loginViewModel.getListsResult().observe(getViewLifecycleOwner(), new Observer<MovieResult>() {
                                                @Override
                                                public void onChanged(@Nullable MovieResult movieResult) {
                                                    if (movieResult == null) {
                                                        return;
                                                    }
                                                    if (movieResult.getError() == null) {
                                                        List<MovieList> allLists = loginViewModel.getUserLists();
                                                        mAdapter.setData(allLists);
                                                    } else {
                                                        Log.d(TAG_NAME, "An error occurred when trying to load trending movies");
                                                    }
                                                }
                                            });
                                            popupWindow.dismiss();
                                            Toast.makeText(getLayoutInflater().getContext(), "List added!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.d(TAG_NAME, "An error occurred when trying to load trending movies");
                                        }
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
