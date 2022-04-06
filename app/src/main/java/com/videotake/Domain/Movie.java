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
    @ColumnInfo(name = "movieID")
    @PrimaryKey private int movieID;
    @ColumnInfo(name = "movie_name")
    private String movieName;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "original_language")
    private String originalLanguage;

    @ColumnInfo(name = "genres")
    private String genres;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "vote_average")
    private Double voteAverage;

    @ColumnInfo(name= "vote_count")
    private int voteCount;

    @ColumnInfo(name = "video_path")
    private String videoPath;

    @ColumnInfo(name = "shareable_link")
    private String shareableLink;
    @Ignore
    private List<Review> reviews;

    public Movie(int movieID, String movieName, String description, String posterPath,
                 String originalLanguage, List<String> genres, String releaseDate, Double voteAverage, int voteCount) {
        this.movieID = movieID;
        this.movieName = movieName;
        this.description = description;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.genres = genresToString(genres);
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    private String genresToString(List<String> genres){
        String genreString = "";
        if (genres.size()>0) genreString += genres.get(0);
        for (int i=1; i<genres.size(); i++){
            genreString += ", " + genres.get(i);
        }
        return genreString;
    }

    public Movie(int movieID, String movieName, String description, String posterPath,
                 String originalLanguage, String releaseDate, Double voteAverage, int voteCount, String videoPath,
                 String shareableLink) {
        this.movieID = movieID;
        this.movieName = movieName;
        this.description = description;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.videoPath = videoPath;
        this.shareableLink = shareableLink;
    }

    public String getMovieName() {
        return this.movieName;
    }

    public String getMovieDescription() {
        return this.description;
    }

    public Double getVoteAverage() {
        return this.voteAverage;
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

    public String getGenres() {
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

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
