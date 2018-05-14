package com.borisruzanov.popularmovies.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("3/movie/popular")
    Call<BasePojo> getPhotosList(@Query("api_key") String key);

    @GET("3/movie/top_rated")
    Call<BasePojo> getPopularList(@Query("api_key") String key);
}
