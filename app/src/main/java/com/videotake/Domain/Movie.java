package com.videotake.Domain;

import androidx.annotation.NonNull;
import androidx.annotation.UiContext;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

// Annotations for DataBase
@Entity(tableName = "movie_table")
public class Movie {

    @ColumnInfo(name = "movie_ID")
    @PrimaryKey
    private int movieID;

    @ColumnInfo(name = "movie_name")
    private String movieName;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "original_language")
    private String originalLanguage;

    //TODO, make a stringBuilder for genres to store in database as a single string
    private List<String> genres;

    private String genreString;

    @ColumnInfo(name = "releaseDate")
    private String releaseDate;
    @ColumnInfo(name = "rating")
    private Double rating;
    @ColumnInfo(name = "videoPath")
    private String videoPath;
    @ColumnInfo(name = "shareableLink")
    private String shareableLink;

    //TODO, think about saving reviews or not and how
    private List<Review> reviews;

    public Movie(int movieID, String movieName, String description, String posterPath,
                 String originalLanguage, List<String> genres, String releaseDate, Double rating) {
        this.movieID = movieID;
        this.movieName = movieName;
        this.description = description;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public String getMovieName() {
        return this.movieName;
    }

    public String getMovieDescription() {
        return this.description;
    }

    public Double getRating() {
        return this.rating;
    }

    public String getPosterPath() { return this.posterPath; }

    public int getMovieID() { return this.movieID; }

    public String getOriginalLanguage() { return this.originalLanguage; }

    public void setVideoPath(String videoPath){ this.videoPath = videoPath; }

    public String getVideoPath(){ return this.videoPath; }

    public String getShareableLink() {
        return shareableLink;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public void setShareableLink(String shareableLink) {
        this.shareableLink = shareableLink;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void createGenresString(List<String> genres) {
        StringBuilder builder = new StringBuilder();
        for (String genre : genres) { builder.append(genre).append(","); }
        this.genreString = builder.toString();
    }
}
