package com.videotake.DAL.Repository;

public interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}
