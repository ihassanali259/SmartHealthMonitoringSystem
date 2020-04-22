package com.example.shms1;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {

    public static final String Step_Storage = "Step_Storage";
    SensorManager sensorManager;
    Sensor countersensor;
    TextView textView, textviewdate;
    int counter = 0;
    SharedPreferences sharedPreferences;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        countersensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        textView = findViewById(R.id.textViewstepcount);
        bar = findViewById(R.id.progressBar);
        textviewdate = findViewById(R.id.textviewdate);

        sharedPreferences = getSharedPreferences(Step_Storage, MODE_PRIVATE);
        textView.setText(String.valueOf(counter));


        //Getting system date and time
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        textviewdate.setText(formattedDate);


    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, countersensor,
                SensorManager.SENSOR_DELAY_NORMAL);


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
}
