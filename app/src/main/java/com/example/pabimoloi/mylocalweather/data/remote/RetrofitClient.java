package com.example.pabimoloi.mylocalweather.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pabi Moloi on 9/12/2017.
 */

public class RetrofitClient {
    private static Retrofit client = null;
    public static Retrofit getClient(String baseURL)
    {
        if (client == null)
        {
            client = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return client;
    }
}
