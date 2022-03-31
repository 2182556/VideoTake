package com.videotake.DAL;

import java.io.IOException;

public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // revoke authentication
    }
}
