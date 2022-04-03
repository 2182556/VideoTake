package com.videotake.DAL;

import android.util.Log;

import com.videotake.Domain.GuestUser;
import com.videotake.Domain.LoggedInUser;
import com.videotake.Domain.Movie;
import com.videotake.Domain.MovieList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class UserApiDAO extends ApiDAO {
    private static final String TAG_NAME = UserApiDAO.class.getSimpleName();
    public final String TOKEN_REQUEST = "authentication/token/new";
    public final String VALIDATE_TOKEN = "authentication/token/validate_with_login";
    public final String AUTHENTICATE_SESSION = "authentication/session/new";
    public final String GUEST_SESSION = "authentication/guest_session/new";
    public final String ACCOUNT = "account";
    public final String SESSION_ID_STRING = "session_id";

//    public UserDAO(String username, String password){
//        ;
//    }

    public Result<LoggedInUser> login(String username, String password) {
        Log.d(TAG_NAME, username + " " +password);
        try {
            String session_Id = requestSession(username,password);
            JSONObject user_details = getUserDetails(session_Id);
            LoggedInUser user =  new LoggedInUser(user_details.getInt("id"),username,password,session_Id);
//            LoggedInUser user = requestSession(username,password);
            Log.d(TAG_NAME, "The value of user is: " + user);
            if (user!=null) {
                return new Result.Success<>(user);
            } else {
                return new Result.Error(new IOException("Error logging in", new NullPointerException()));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // revoke authentication
        // delete session
    }

    protected String requestSession(String username, String password) {
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
                                    return authenticate_session_json.getString("session_id");
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

    protected JSONObject getUserDetails(String session_Id){
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

    public void addMovieToList(String session_Id, int list_id, int movie_id){
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("media_id", String.valueOf(movie_id))
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + LIST + "/" + list_id + "/add_item" + API_KEY + "&" + SESSION_ID_STRING + "=" + session_Id)
                .addHeader("Content-Type", "application/json;charset=utf-8")
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            JSONObject json = new JSONObject(body.string());
            Log.d(TAG_NAME,json.toString());
            boolean success = json.getBoolean("success");
            if (success) Log.d(TAG_NAME,"The movie was added to the list");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void createList(String session_Id){
        Log.d(TAG_NAME, "Attempting to add list to API");
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

    protected Result<List<MovieList>> lists(String session_Id){
        List<MovieList> allLists = new ArrayList<>();
        int page = 1;
        while (true) {
            Request request = new Request.Builder()
                    .url(BASE_URL + ACCOUNT + "/{account_id}/" + "lists" + API_KEY + "&" +
                            SESSION_ID_STRING + "=" + session_Id + "&" + PAGE + "=" + page )
                    .build();
            OkHttpClient client = new OkHttpClient();
            try (Response response = client.newCall(request).execute()) {
                ResponseBody body = response.body();
                JSONObject json = new JSONObject(body.string());
                Log.d(TAG_NAME,json.toString());
                int amountOfPages = json.getInt("total_pages");
                JSONArray json_lists = json.getJSONArray("results");
                for (int i=0; i<json_lists.length(); i++){
                    JSONObject list = json_lists.getJSONObject(i);
                    Request list_request = new Request.Builder()
                            .url(BASE_URL + LIST + "/" + list.getInt("id") + API_KEY )
                            .build();
                    try (Response list_response = client.newCall(list_request).execute()) {
                        ResponseBody list_body = list_response.body();
                        JSONObject list_json = new JSONObject(list_body.string());
                        Log.d(TAG_NAME, list_json.toString());
                        JSONArray movies_in_list_json  = list_json.getJSONArray("items");
                        List<Movie> movies = new ArrayList<>();
                        movies.addAll(MovieApiDAO.getListOfMoviesFromJSONArray(movies_in_list_json));
                        allLists.add(new MovieList(list_json.getString("name"),list_json.getString("description"), movies));
                    }
                }
                return new Result.Success<>(allLists);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result.Error(new IOException("Could not get lists of this user",e));
            }
        }
    }

    public Result<GuestUser> createGuestSession(){
        try {
            Request guest_session_request = new Request.Builder()
                    .url(BASE_URL + GUEST_SESSION + API_KEY)
                    .build();
            OkHttpClient client = new OkHttpClient();
            try (Response authenticate_session_response = client.newCall(guest_session_request).execute()) {
                ResponseBody authenticate_session_body = authenticate_session_response.body();
                JSONObject authenticate_session_json = new JSONObject(authenticate_session_body.string());
                Log.d(TAG_NAME, authenticate_session_json.toString());
                boolean authenticate_session_success = authenticate_session_json.getBoolean("success");
                if (authenticate_session_success){
                    Log.d(TAG_NAME, "Successfully retrieved guest session id.");
                    String guest_session_id = authenticate_session_json.getString("guest_session_id");
                    GuestUser user = new GuestUser(guest_session_id);
                    return new Result.Success<>(user);
                } else {
                    Log.e(TAG_NAME,"Unable to retrieve guest session id.");
                }
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }

        return null;
    }
}
