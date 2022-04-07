package com.videotake.Domain;

public class Review {
    private String reviewID;
    private String userName;
    private String content;
    private int movieID;

    public Review(String reviewID, String userName, String content) {
        this.reviewID = reviewID;
        this.userName = userName;
        this.content = content;
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

}
