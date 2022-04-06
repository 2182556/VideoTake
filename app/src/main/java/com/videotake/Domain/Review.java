package com.videotake.Domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "reviewID", primaryKeys = {"reviewID"})
public class Review {

    //Defining Data
    @NonNull
    @ColumnInfo(name = "reviewID")
    private String reviewID;

    @Ignore
    @ColumnInfo(name = "movieID")
    private String movieID;

    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "rating")
    private double rating;



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
