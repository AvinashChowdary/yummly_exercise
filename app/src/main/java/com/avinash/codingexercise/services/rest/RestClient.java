package com.avinash.codingexercise.services.rest;

import com.avinash.codingexercise.services.Services;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;


public class RestClient implements RequestInterceptor {

    private Services svService;

    public static final String BASE_URL = "https://api.yummly.com/v1";

    public static final String TAG = RestClient.class.getSimpleName();

    public static RestClient getInstance() {
        return new RestClient();
    }


    /**
     * Creating rest adapter
     */
    public Services getYummlyService() {
        final String svBaseUrl = BASE_URL;
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(svBaseUrl)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(this)
                .setClient(new retrofit.client.UrlConnectionClient())
                .build();
        svService = restAdapter.create(Services.class);
        return svService;
    }

    @Override
    public void intercept(RequestFacade request) {
        // nothing to do here
    }

}
