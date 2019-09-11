package com.mohamed.ezz.nearestmasjids.services;

import com.mohamed.ezz.nearestmasjids.models.MyResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @Headers("Content-Type: application/json")
    @GET("/api/v2/mosques?page=1&limit=10")
    Call<MyResult> getData(@Query("latitude") double latitude, @Query("longitude") double longitude, @Query("radius") long radius);
}
