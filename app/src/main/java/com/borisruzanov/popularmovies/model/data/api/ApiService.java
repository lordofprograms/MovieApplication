package com.borisruzanov.popularmovies.model.data.api;

import com.borisruzanov.popularmovies.entity.BasePojo;
import com.borisruzanov.popularmovies.entity.ReviewModel;
import com.borisruzanov.popularmovies.entity.TrailerModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("3/movie/popular")
    Call<BasePojo> getPhotosList(@Query("api_key") String key);

    @GET("3/movie/top_rated")
    Call<BasePojo> getPopularList(@Query("api_key") String key);

    @GET("/3/movie/{id}/videos")
    Call<TrailerModel> loadTrailers(@Path("id") String id, @Query("api_key") String key);

    @GET("/3/movie/{id}/reviews")
    Call<ReviewModel> loadReviews(@Path("id") String id, @Query("api_key") String key);
}
