package com.videotake.DAL;

public abstract class ApiDAO {
    public static final String TAG_NAME = ApiDAO.class.getSimpleName();
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "?api_key=5144de6e9e1919536a34c7c1e2736453";
    public static final String SEARCH_MOVIE = "search/movie";
    public static final String TRENDING = "trending/movie/week";
    public static final String MOVIE_GENRES = "genre/movie/list";
    public static final String QUERY_PARAM = "&query=";
    public static final String PAGE = "page";
    public static final String LIST = "list";
    public static final String MOVIE = "movie/";
}
