package com.videotake.Domain;

public class Review {
    private String reviewID;
    private String userName;
    private String content;
    private double rating;
    private String movieID;

    public Review(String reviewID, String userName, String content, double rating) {
        this.reviewID = reviewID;
        this.userName = userName;
        this.content = content;
        this.rating = rating;
    }

    public String getReviewID() {
        return reviewID;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public double getRating() {
        return rating;
    }
}
