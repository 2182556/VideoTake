package com.videotake.DAL;

import android.util.Log;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class APIConnection {
    private static final String TAG_NAME = APIConnection.class.getSimpleName();
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "?api_key=5144de6e9e1919536a34c7c1e2736453";
    private static final String SEARCH_MOVIE = "search/movie";
    private static final String TRENDING = "trending/all/week";
    private static final String QUERY_PARAM = "query";
    private static final String PAGE = "page";
    private static final String TOKEN_REQUEST = "authentication/token/new";
    private static final String VALIDATE_TOKEN = "authentication/token/validate_with_login";
    private static final String AUTHENTICATE_SESSION = "authentication/session/new";
    private static final String SESSION_ID_STRING = "session_id";
    private static String session_Id;
    private static final String LIST = "list";


    public static void requestSession(String username, String password) {
        try {
            String REQUEST_TOKEN = "";
            Request token_request = new Request.Builder()
                    .url(BASE_URL+TOKEN_REQUEST+API_KEY)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Log.d(TAG_NAME, "Trying to get request token.");
            try (Response token_request_response = client.newCall(token_request).execute()) {
                ResponseBody token_request_body = token_request_response.body();
                Log.d(TAG_NAME, token_request_body.toString());
                JSONObject token_request_json = new JSONObject(token_request_body.string());
                Log.d(TAG_NAME,token_request_json.toString());
                boolean token_request_success = token_request_json.getBoolean("success");
                if (token_request_success) {
                    REQUEST_TOKEN = (String) token_request_json.get("request_token");
                    Log.d(TAG_NAME,"Successfully retrieved request token");

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
                        Log.d(TAG_NAME, validate_token_body.toString());
                        JSONObject validate_token_json = new JSONObject(validate_token_body.string());
                        Log.d(TAG_NAME, validate_token_json.toString());
                        boolean validate_token_success = validate_token_json.getBoolean("success");
                        if (validate_token_success) {
                            Log.d(TAG_NAME, "Successfully authenticated request token");

                            RequestBody authenticate_session_requestBody = new MultipartBody.Builder()
                                    .addFormDataPart("request_token", REQUEST_TOKEN)
                                    .build();
                            Request authenticate_session_request = new Request.Builder()
                                    .url(BASE_URL + AUTHENTICATE_SESSION + API_KEY)
                                    .post(authenticate_session_requestBody)
                                    .build();
                            try (Response authenticate_session_response = client.newCall(authenticate_session_request).execute()) {
                                ResponseBody authenticate_session_body = authenticate_session_response.body();
                                Log.d(TAG_NAME, authenticate_session_body.toString());
                                JSONObject authenticate_session_json = new JSONObject(authenticate_session_body.string());
                                Log.d(TAG_NAME, authenticate_session_json.toString());
                                boolean authenticate_session_success = authenticate_session_json.getBoolean("success");
                                if (authenticate_session_success){
                                    session_Id = (String) authenticate_session_json.get("session_id");
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createList(){
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
                Log.d(TAG_NAME, body.toString());
                JSONObject json = new JSONObject(body.string());
                Log.d(TAG_NAME,json.toString());
                boolean success = json.getBoolean("success");
                if (success) Log.d(TAG_NAME,"The list was added to the API");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
