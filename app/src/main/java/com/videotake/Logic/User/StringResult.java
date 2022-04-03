package com.videotake.Logic.User;

import androidx.annotation.Nullable;

public class StringResult {

    @Nullable
    private Integer error;

    public StringResult(@Nullable Integer error) {
        this.error = error;
    }

    public StringResult() { }

    @Nullable
    public Integer getError() {
        return error;
    }
}
