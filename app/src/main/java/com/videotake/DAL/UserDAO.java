package com.videotake.DAL;

import android.util.Log;

import com.videotake.Domain.LoggedInUser;
import com.videotake.Domain.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class UserDAO extends DAO {
    private static final String TAG_NAME = UserDAO.class.getSimpleName();
    public final String TOKEN_REQUEST = "authentication/token/new";
    public final String VALIDATE_TOKEN = "authentication/token/validate_with_login";
    public final String AUTHENTICATE_SESSION = "authentication/session/new";
    public final String ACCOUNT = "account";
    public final String SESSION_ID_STRING = "session_id";
    public String session_Id;

//    public UserDAO(String username, String password){
//        ;
//    }

    public Result<LoggedInUser> login(String username, String password) {
        Log.d(TAG_NAME, username + " " +password);
        try {
            LoggedInUser user = requestSession(username,password);
            if (user!=null){
                return new Result.Success<>(user);
            } else {
                throw new IOException();
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // revoke authentication
        // delete session
    }

    protected LoggedInUser requestSession(String username, String password) {
        try {
            String REQUEST_TOKEN = "";
            Request token_request = new Request.Builder()
                    .url(BASE_URL+TOKEN_REQUEST+API_KEY)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Log.d(TAG_NAME, "Trying to get request token.");
            try (Response token_request_response = client.newCall(token_request).execute()) {
                ResponseBody token_request_body = token_request_response.body();
                JSONObject token_request_json = new JSONObject(token_request_body.string());
                Log.d(TAG_NAME,token_request_json.toString());
                boolean token_request_success = token_request_json.getBoolean("success");
                if (token_request_success) {
                    REQUEST_TOKEN = token_request_json.getString("request_token");
                    Log.d(TAG_NAME,"Successfully retrieved request token.");

                    RequestBody validate_token_requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("username", username)
                            .addFormDataPart("password", password)
                            .addFormDataPart("request_token", REQUEST_TOKEN)
                            .build();
                    Request validate_token_request = new Request.Builder()
                            .url(BASE_URL + VALIDATE_TOKEN + API_KEY)
                            .post(validate_token_requestBody)
                            .build();

                    try (Response validate_token_response = client.newCall(validate_token_request).execute()) {
                        ResponseBody validate_token_body = validate_token_response.body();
                        JSONObject validate_token_json = new JSONObject(validate_token_body.string());
                        Log.d(TAG_NAME, validate_token_json.toString());
                        boolean validate_token_success = validate_token_json.getBoolean("success");
                        if (validate_token_success) {
                            Log.d(TAG_NAME, "Successfully validated request token.");
                            RequestBody authenticate_session_requestBody = new MultipartBody.Builder()
                                    .addFormDataPart("request_token", REQUEST_TOKEN)
                                    .build();
                            Request authenticate_session_request = new Request.Builder()
                                    .url(BASE_URL + AUTHENTICATE_SESSION + API_KEY)
                                    .post(authenticate_session_requestBody)
                                    .build();
                            try (Response authenticate_session_response = client.newCall(authenticate_session_request).execute()) {
                                ResponseBody authenticate_session_body = authenticate_session_response.body();
                                JSONObject authenticate_session_json = new JSONObject(authenticate_session_body.string());
                                Log.d(TAG_NAME, authenticate_session_json.toString());
                                boolean authenticate_session_success = authenticate_session_json.getBoolean("success");
                                if (authenticate_session_success){
                                    Log.d(TAG_NAME, "Successfully retrieved session id.");
                                    session_Id = authenticate_session_json.getString("session_id");
                                    JSONObject user_details = getUserDetails();
                                    return new LoggedInUser(user_details.getInt("id"),username,password,session_Id);
                                } else {
                                    Log.e(TAG_NAME,"Unable to retrieve session id.");
                                }
                            }
                        } else {
                            Log.e(TAG_NAME,"Unable to validate request token.");
                        }
                    }
                } else {
                    Log.e(TAG_NAME,"Unable to retrieve request token.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected JSONObject getUserDetails(){
        if (session_Id!=null) {
            Request request = new Request.Builder()
                    .url(BASE_URL + ACCOUNT + API_KEY + "&" + SESSION_ID_STRING + "=" + session_Id )
                    .build();

            OkHttpClient client = new OkHttpClient();
            try (Response response = client.newCall(request).execute()) {
                ResponseBody body = response.body();
                JSONObject json = new JSONObject(body.string());
                Log.d(TAG_NAME,json.toString());
                return json;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    protected void createList(){
        Log.d(TAG_NAME, "Current session id: " + session_Id);
        if (session_Id!=null) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("name", "An example list :)")
                    .addFormDataPart("description", "This is a test")
                    .build();

            Request request = new Request.Builder()
                    .url(BASE_URL + LIST + API_KEY + "&" + SESSION_ID_STRING + "=" + session_Id )
                    .addHeader("Content-Type", "application/json;charset=utf-8")
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            try (Response response = client.newCall(request).execute()) {
                ResponseBody body = response.body();
                JSONObject json = new JSONObject(body.string());
                Log.d(TAG_NAME,json.toString());
                boolean success = json.getBoolean("success");
                if (success) Log.d(TAG_NAME,"The list was added to the API");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    protected void getAllLists(){
        while (true) {
            // if page is empty:
            break;
        }
    }
}