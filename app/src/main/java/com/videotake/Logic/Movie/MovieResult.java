package com.videotake.Logic.Movie;

import androidx.annotation.Nullable;

public class MovieResult {
    @Nullable
    private Integer error;

    MovieResult(@Nullable Integer error) {
        this.error = error;
    }

    MovieResult() { }

//    @Nullable
//    public LoggedInUserView getSuccess() {
//        return success;
//    }

    @Nullable
    public Integer getError() {
        return error;
    }

}
