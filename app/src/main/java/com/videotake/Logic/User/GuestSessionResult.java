package com.videotake.Logic.User;

import androidx.annotation.Nullable;

import com.videotake.Domain.GuestUser;

public class GuestSessionResult {
    @Nullable
    private Integer error;

    GuestSessionResult(@Nullable Integer error) {
        this.error = error;
    }

    GuestSessionResult() { }

//    @Nullable
//    public LoggedInUserView getSuccess() {
//        return success;
//    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
