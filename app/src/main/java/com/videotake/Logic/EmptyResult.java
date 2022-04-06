package com.videotake.Logic;

import androidx.annotation.Nullable;

public class EmptyResult {

    @Nullable
    private Integer error;

    public EmptyResult(@Nullable Integer error) {
        this.error = error;
    }

    public EmptyResult() { }

    @Nullable
    public Integer getError() {
        return error;
    }
}
