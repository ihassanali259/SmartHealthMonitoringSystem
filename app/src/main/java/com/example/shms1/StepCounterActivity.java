package com.example.shms1;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {


    public static final String Step_Storage = "Step_Storage";
    SensorManager sensorManager;
    Sensor countersensor;
    static int status = 0;
    int counter = 0;
    SharedPreferences sharedPreferences;
    ProgressBar bar;
    static Double lat1 = null;
    static Double lon1 = null;
    static Double lat2 = null;
    static Double lon2 = null;
    static Double distance = 0.0;
    TextView textView, textviewdate, textviewdistance;
    String Location_Provider = LocationManager.GPS_PROVIDER;

    /* TODO: Declare a Locationmanager and LocationListener*/
    LocationManager locationManager;
    LocationListener locationListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Slide());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        countersensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        textView = findViewById(R.id.textViewstepcount);
        bar = findViewById(R.id.progressBar);
        textviewdate = findViewById(R.id.textviewdate);
        textviewdistance = findViewById(R.id.textViewdistance);

        sharedPreferences = getSharedPreferences(Step_Storage, MODE_PRIVATE);
        textView.setText(String.valueOf(counter));


        Location location = new Location(Location_Provider);

        String s = String.valueOf(location.getLatitude());
        Log.d("Oncreate", "onCreate: Latitite" + s);



        //Getting system date and time
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        textviewdate.setText(formattedDate);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (status == 0) {
                    lat1 = location.getLatitude();
                    lon1 = location.getLongitude();
                } else if ((status % 2) != 0) {
                    lat2 = location.getLatitude();
                    lon2 = location.getLongitude();
                    distance += distanceBetweenTwoPoint(lat1, lon1, lat2, lon2);
                } else if ((status % 2) == 0) {
                    lat1 = location.getLatitude();
                    lon1 = location.getLongitude();
                    distance += distanceBetweenTwoPoint(lat2, lon2, lat1, lon1);
                }
                status++;
                String dsnt = String.valueOf(distance);
                textviewdistance.setText(dsnt);


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("ProviderEnabled", "Enabled");

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("ProviderDisabled", "Enable Provider");

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            return;
        }
        locationManager.requestLocationUpdates(Location_Provider, 5000, 1000, locationListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, countersensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        //getDistanceBetweenPoints();


    }
    //@Override
    //protected void onStop() {
    //  super.onStop();
    //sensorManager.unregisterListener(this);
    //}


    @Override
    public void onSensorChanged(SensorEvent event) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        int sensortype = event.sensor.getType();
        int currentValue = (int) event.values[0];
        //int value1=(int) event.values[1];
        // int value3=(int) event.values[2];
        Log.d("Value0", String.valueOf(currentValue));

        //  Log.d("Value1", String.valueOf(value1));
        textView.setText(String.valueOf(counter));
        bar.setProgress(counter);
        counter = counter + 1;

        editor.putInt("Steps", counter);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    double distanceBetweenTwoPoint(double srcLat, double srcLng, double desLat, double desLng) {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(desLat - srcLat);
        double dLng = Math.toRadians(desLng - srcLng);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(srcLat))
                * Math.cos(Math.toRadians(desLat)) * Math.sin(dLng / 2)
                * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        double meterConversion = 1609;

        return (int) (dist * meterConversion);
    }


}
