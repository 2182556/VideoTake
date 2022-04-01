package com.videotake.Logic.User;

import com.videotake.Domain.Movie;

import java.util.List;
import java.util.Map;

public class LoggedInUserView {
    private String displayName;
    private Map<String, List<Movie>> movieLists;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Map<String, List<Movie>> getMovieLists() {
        return movieLists;
    }
}
