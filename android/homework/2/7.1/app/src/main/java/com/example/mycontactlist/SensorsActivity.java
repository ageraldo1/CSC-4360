package com.example.mycontactlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SensorsActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener gpsListener;
    LocationListener networkListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        initGetLocationButton();
    }

    private void initGetLocationButton() {

        Button networkButton = (Button) findViewById(R.id.buttonNetwork);

        networkButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    locationManager = (LocationManager) getBaseContext().getSystemService(Context.LOCATION_SERVICE);

                    networkListener = new LocationListener() {
                        public void onLocationChanged(Location location) {
                            TextView txtLatitude = (TextView) findViewById(R.id.textLatitude);
                            TextView txtLongitude = (TextView) findViewById(R.id.textLongitude);
                            TextView txtAccuracy = (TextView) findViewById(R.id.textAccuracy);
                            txtLatitude.setText(String.valueOf(location.getLatitude()));
                            txtLongitude.setText(String.valueOf(location.getLongitude()));
                            txtAccuracy.setText(String.valueOf(location.getAccuracy()));
                        }

                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                        public void onProviderEnabled(String provider) {
                        }

                        public void onProviderDisabled(String provider) {
                        }
                    };

                    if (ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, networkListener);


                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Error, Location not available", Toast.LENGTH_LONG).show();
                }

            }
        });

        Button gpsButton = (Button) findViewById(R.id.buttonGPS);

        gpsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    locationManager = (LocationManager) getBaseContext().getSystemService(Context.LOCATION_SERVICE);

                    gpsListener = new LocationListener() {
                        public void onLocationChanged(Location location) {
                            TextView txtLatitude = (TextView) findViewById(R.id.textLatitude);
                            TextView txtLongitude = (TextView) findViewById(R.id.textLongitude);
                            TextView txtAccuracy = (TextView) findViewById(R.id.textAccuracy);
                            txtLatitude.setText(String.valueOf(location.getLatitude()));
                            txtLongitude.setText(String.valueOf(location.getLongitude()));
                            txtAccuracy.setText(String.valueOf(location.getAccuracy()));
                        }

                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                        public void onProviderEnabled(String provider) {
                        }

                        public void onProviderDisabled(String provider) {
                        }
                    };

                    if (ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, gpsListener);


                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Error, Location not available", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public void onPause() {
        try {
            locationManager.removeUpdates(gpsListener);
            locationManager.removeUpdates(networkListener);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }
}
