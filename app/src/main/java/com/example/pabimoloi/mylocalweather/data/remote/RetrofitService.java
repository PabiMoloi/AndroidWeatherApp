package com.example.pabimoloi.mylocalweather.data.remote;
import com.example.pabimoloi.mylocalweather.data.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Pabi Moloi on 9/12/2017.
 */

public interface RetrofitService
{
    @GET("weather")
    Call<WeatherResponse> getTemp(@Query("q") StringBuilder cityName, @Query("appid")String APIKEY, @Query("units")String Metric);
}
