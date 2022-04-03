package com.videotake.Domain;

import java.util.List;
import java.util.Map;

public class LoggedInUser implements User {

    private int userId;
    private String displayName;
    private String password;
    private String session_Id;
    private List<MovieList> movieLists;

    public LoggedInUser(int userId, String displayName, String password, String session_Id) {
        this.userId = userId;
        this.displayName = displayName;
        this.password = password;
        this.session_Id = session_Id;
    }

    public int getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<MovieList> getMovieLists() {
        return movieLists;
    }

    public void setMovieLists(List<MovieList> movieLists) {
        this.movieLists = movieLists;
    }

    public String getSession_Id() {
        return session_Id;
    }

    public void setSession_Id(String session_Id) {
        this.session_Id = session_Id;
    }
}
