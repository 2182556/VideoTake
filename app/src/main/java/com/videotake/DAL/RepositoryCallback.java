package com.videotake.DAL;

public interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}
