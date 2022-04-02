//package com.videotake.UI.DetailPage;
//
//import android.content.Intent;
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import com.videotake.Domain.Movie;
//import com.videotake.R;
//
//public class MovieDetailPageActivity extends AppCompatActivity {
//    private final String TAG_NAME = MovieDetailPageActivity.class.getSimpleName();
//    private Movie movie;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detailpage);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);
//
//        Intent intent = getIntent();
//        int mealId = intent.getIntExtra("movie_id",0);
//
////        Log.d(TAG_NAME, "Id of meal loaded in: " + String.valueOf(this.meal.getMealID()));
//
//        ImageView image = findViewById(R.id.meal_image);
//        TextView title = findViewById(R.id.meal_title);
//        TextView description = findViewById(R.id.meal_description);
//        TextView price = findViewById(R.id.meal_price);
//        TextView spotsLeft = findViewById(R.id.meal_spotsleft);
//        TextView allergenInfo = findViewById(R.id.meal_allergeninfo);
//        TextView date = findViewById(R.id.meal_date);
//
//        TextView cookName = findViewById(R.id.cook_name);
//        TextView cookCity = findViewById(R.id.cook_city);
//
//
//        Resources res = getResources();
//
////        title.setText(meal.getTitle());
////        description.setText(meal.getDescription());
////        price.setText(String.format(res.getString(R.string.price_full_string),meal.getFormattedPrice()));
////        allergenInfo.setText(String.format(res.getString(R.string.allergeninfo_full_string),meal.getAllergens()));
////        date.setText(meal.getFormattedDatetime());
////        spotsLeft.setText(res.getString(R.string.spots_left)+meal.getSpotsLeft());
////
////        cookName.setText(meal.getCook().getFirstName() + " " + meal.getCook().getLastName());
////        cookCity.setText(meal.getCook().getCity());
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
