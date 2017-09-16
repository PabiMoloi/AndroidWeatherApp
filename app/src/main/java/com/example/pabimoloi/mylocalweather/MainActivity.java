package com.example.pabimoloi.mylocalweather;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.Manifest;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfoMobile;
    static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 0;
    LocationManager locationManager;
    AlertDialog.Builder alertDialogBuilder;
    boolean isConnected;
    int permissionResult;
    TextView versionNumber;
    private void instantiateVariables()
    {
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfoMobile = connectivityManager.getActiveNetworkInfo();
        isConnected = networkInfoMobile != null && networkInfoMobile.isConnectedOrConnecting();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        permissionResult = this.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION");
        alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        versionNumber = (TextView)findViewById(R.id.textViewVersionNumber);
        versionNumber.setText(BuildConfig.VERSION_NAME);
    }

    private void checkPermissionsAndConnectivity(int permissionResult, LocationManager locationManager, boolean internetConnectivity) {
        if (permissionResult == 0)
        {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                if(internetConnectivity)
                {
                    stimulateSomeWork();
                }
                else
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
            }
            else
            {
                alertDialogBuilder.setMessage(R.string.enableGPS).setCancelable(false)
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
        } else
        {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instantiateVariables();
        checkPermissionsAndConnectivity(permissionResult,locationManager,isConnected);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    instantiateVariables();
                    checkPermissionsAndConnectivity(permissionResult,locationManager,isConnected);

                }
                else
                {
                    alertDialogBuilder.setMessage(R.string.locationServicesPermissions).setCancelable(false)
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

            }
        }
    }
    private void stimulateSomeWork()
    {
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                startActivity(new Intent(MainActivity.this,weatherDisplay.class));
                finish();
            }
        }, 1000);
    }
}

