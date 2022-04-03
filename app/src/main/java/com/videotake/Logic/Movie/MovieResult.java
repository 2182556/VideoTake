package com.videotake.Logic.Movie;

import androidx.annotation.Nullable;

public class MovieResult {
    @Nullable
    private Integer error;

    public MovieResult(@Nullable Integer error) {
        this.error = error;
    }

    public MovieResult() { }

//    @Nullable
//    public LoggedInUserView getSuccess() {
//        return success;
//    }

    @Nullable
    public Integer getError() {
        return error;
    }

}
