package com.videotake.Domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

// Annotations for DataBase
@Entity(tableName = "movie_table")
public class Movie {

    // Key values
    @ColumnInfo(name = "movie")
    @PrimaryKey private int movieID;
    private String movieName;
    private String description;
    private String posterPath;
    private String originalLanguage;
    @Ignore
    private List<String> genres;
    private String releaseDate;
    private Double rating;
    private String videoPath;
    private String shareableLink;
    @Ignore
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

    public Movie(int movieID, String movieName, String description, String posterPath,
                 String originalLanguage, String releaseDate, Double rating, String videoPath,
                 String shareableLink) {
        this.movieID = movieID;
        this.movieName = movieName;
        this.description = description;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.videoPath = videoPath;
        this.shareableLink = shareableLink;
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

    public String getDescription() {
        return description;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
