package com.videotake.Domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Movie {

    private int movieID;
    private String movieName;
    private String description;
    private String posterPath;
    private String originalLanguage;
    private List<String> genres;
    private String releaseDate;
    private Double rating;
    private String videoPath;
    private String shareableLink;
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
}
