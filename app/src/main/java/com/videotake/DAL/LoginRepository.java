package com.videotake.DAL;

import com.videotake.Domain.LoggedInUser;

import java.util.concurrent.Executor;

public class LoginRepository {

    private static volatile LoginRepository instance;

    private final Executor executor;
    private UserDAO userDAO;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
//    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(UserDAO userDAO, Executor executor) {
        this.executor = executor;
        this.userDAO = userDAO;
    }

    public static LoginRepository getInstance(UserDAO userDAO, Executor executor) {
        if (instance == null) {
            instance = new LoginRepository(userDAO,executor);
        }
        return instance;
    }

//    public boolean isLoggedIn() {
//        return user != null;
//    }

    public void logout() {
//        user = null;
        userDAO.logout();
    }

//    private void setLoggedInUser(LoggedInUser user) {
//        this.user = user;
//        // If user credentials will be cached in local storage, it is recommended it be encrypted
//        // @see https://developer.android.com/training/articles/keystore
//    }

    public void login(String username, String password, final RepositoryCallback<LoggedInUser> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Result<LoggedInUser> result = userDAO.login(username, password);
                if (result instanceof Result.Success) {
                    callback.onComplete(result);
//                    setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
                }
            }
        });
    }
}
