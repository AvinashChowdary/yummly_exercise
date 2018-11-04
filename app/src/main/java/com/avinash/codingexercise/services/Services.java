package com.avinash.codingexercise.services;

import android.telecom.Call;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

public interface Services {

    // api to recipes search
    @GET("/api/recipes")
    @Headers({"Content-Type : application/json", "Accept-Language : en_US", "X-Yummly-App-ID:62407325",
            "X-Yummly-App-Key:955296fc681db8f8b82e0698d02a50c0"})
    void getSearchResults(@Query("_app_id") String appId, @Query("_app_key") String appKey,
                                      @Query("q") String query, @Query("requirePictures") boolean isPictureRequired,
                                      Callback<Response> callback);

}
