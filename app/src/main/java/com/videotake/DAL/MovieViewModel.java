//package com.videotake.DAL;
//
//import android.app.Application;
//
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//
//import com.videotake.DAL.MovieDao;
//import com.videotake.Domain.Movie;
//
//import java.util.List;
//
//public class MovieViewModel extends AndroidViewModel {
//
//    private MovieRepository mRepository;
//    private LiveData<List<Movie>> mAllMovies;
//
//    public MovieViewModel (Application application) {
//        super(application);
//        mRepository = new MovieRepository(application);
//        mAllMovies = mRepository.getAllMovies();
//    }
//
//    LiveData<List<Movie>> getAllMovies() { return mAllMovies; }
//
//    public void insert(Movie movie) { mRepository.insert(movie); }
//
//}
