package com.videotake.UI.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.videotake.Logic.MovieViewModel;
import com.videotake.R;
import com.videotake.UI.Adapters.MovieListAdapter;

import java.util.ArrayList;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MovieListAdapter mAdapter = new MovieListAdapter(this);
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setAdapter(mAdapter);

        MovieViewModel model = new ViewModelProvider(this).get(MovieViewModel.class);
        model.requestSession("ExampleAccount", "anexample-1");
//        model.createList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //add items to action bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle selected items from action bar
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //save items when rotating application
        super.onSaveInstanceState(outState);
    }

}
