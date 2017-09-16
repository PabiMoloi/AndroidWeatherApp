package com.example.pabimoloi.mylocalweather;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import org.json.JSONArray;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


import data.model.model.Weather;
import data.model.model.WeatherResponse;
import data.model.remote.RetrofitService;
import data.model.remote.RetrofitUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class weatherDisplay extends AppCompatActivity
{
    LocationManager locationManager;
    ProgressBar progressBarRetrieveData;
    Location location;
    double latitude, longitude;
    TextView currentLocation, minimumTemperature, maximumTemperature, weatherDescription, currentDate,progressBarText;
    Geocoder geocoder;
    RetrofitService retrofitService;
    String APIKEY= "ee53d4cfdf918d921aa0aed8d5d32f80";
    StringBuilder addressStringBuilder;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_display);
        InitializeVariables();

        LocationListener locationListener = new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location)
            {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {

            }

            @Override
            public void onProviderEnabled(String provider)
            {

            }

            @Override
            public void onProviderDisabled(String provider)
            {
                alertDialogBuilder.setMessage(R.string.connectToNetwork).setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                finish();

                            }
                        });
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();
            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
        int permissionCheck = ContextCompat.checkSelfPermission(weatherDisplay.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck == 0)
        {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if(location != null )
        {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        try
        {
            geocoder  = new Geocoder(this,Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(latitude,longitude,1);
            addressStringBuilder = new StringBuilder();
            if(addressList.size() > 0)
            {
                Address locationAddress = addressList.get(0);
                for(int i =0 ; i <= locationAddress.getMaxAddressLineIndex(); i++)
                {
                    locationAddress.getAddressLine(i);
                    addressStringBuilder.append(locationAddress.getSubLocality()).append(",");
                    addressStringBuilder.append(locationAddress.getLocality());
                }
                currentLocation.setText(addressStringBuilder);
            }
        }
        catch (IOException e)
        {

        }
        getWeather();
    }
    public void InitializeVariables()
    {
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        retrofitService = RetrofitUtility.getRetrofitService();
        currentLocation = (TextView)findViewById(R.id.LocationTextView);
        minimumTemperature = (TextView)findViewById(R.id.txtMinimumValue);
        maximumTemperature = (TextView)findViewById(R.id.txtMaximumValue);
        weatherDescription = (TextView) findViewById(R.id.textViewWeatherDescription);
        currentDate = (TextView)findViewById(R.id.textViewDate);
        progressBarText = (TextView)findViewById((R.id.textViewProgressBarText));
        progressBarRetrieveData = (ProgressBar)findViewById(R.id.progressBarRetrieveData);
        alertDialogBuilder = new AlertDialog.Builder(weatherDisplay.this);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("E,LLLL d y");
        currentDate.setText(simpleDateFormat.format(calendar.getTime()));

    }

public void getWeather()
{
    retrofitService.getTemp(addressStringBuilder,APIKEY,"metric").enqueue(new Callback<WeatherResponse>()
    {
        @Override
        public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response)
        {
            if(response.isSuccessful())
            {
                JSONArray arr = new JSONArray(response.body().getWeather());
                List<Weather> list;
                list = response.body().getWeather();
                weatherDescription.setText(list.get(0).getDescription().toUpperCase());
                maximumTemperature.setText(response.body().getMain().getTempMax().toString()+ " \u2103");
                minimumTemperature.setText(response.body().getMain().getTempMin().toString() + " \u2103");
                progressBarRetrieveData.setVisibility(View.INVISIBLE);
                ShowDisplayElements();

            }
        }

        @Override
        public void onFailure(Call<WeatherResponse> call, Throwable t) {

            Log.d("weatherDisplayActivity","Error retrieving information from API");
        }
    });
}
private void ShowDisplayElements()
{
    currentLocation.setVisibility(View.VISIBLE);
    weatherDescription.setVisibility(View.VISIBLE);
    maximumTemperature.setVisibility(View.VISIBLE);
    minimumTemperature.setVisibility(View.VISIBLE);
    progressBarText.setVisibility(View.INVISIBLE);
}

}
