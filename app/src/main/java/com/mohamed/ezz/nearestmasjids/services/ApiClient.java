package com.mohamed.ezz.nearestmasjids.services;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://dev.prayer-now.com/";

    public static Retrofit getClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        if (retrofit == null)
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }
}
