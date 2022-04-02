package com.videotake.Domain;

import java.util.List;
import java.util.Map;

public class LoggedInUser implements User {

    private int userId;
    private String displayName;
    private String password;
    private String session_Id;
    private Map<String, List<Movie>> movieLists;

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
}
