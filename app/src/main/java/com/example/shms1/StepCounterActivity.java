package com.example.shms1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.core.ui.ChartScroller;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {


    public static final String Step_Storage = "Step_Storage";
    SensorManager sensorManager;
    Sensor countersensor;
    double distance = 0.0;
    int counter = 0;
    SharedPreferences sharedPreferences;
    ProgressBar bar;

    TextView textView, textviewdate, textviewdistance;
    String Location_Provider = LocationManager.GPS_PROVIDER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Slide());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        countersensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        textView = findViewById(R.id.textViewstepcount);
        //   bar = findViewById(R.id.progressBar);
        textviewdate = findViewById(R.id.textviewdate);
        textviewdistance = findViewById(R.id.textViewdistance);
        AnyChartView anyChartView = findViewById(R.id.anychartview);
        sharedPreferences = getSharedPreferences(Step_Storage, MODE_PRIVATE);
        textView.setText(String.valueOf(counter));

        counter = sharedPreferences.getInt("Steps", 0);
        distance = sharedPreferences.getFloat("Distance", 0);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 200);
        }


        textviewdistance.setText(String.valueOf(distance));


        //Getting system date and time
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        textviewdate.setText(formattedDate);


        //region Implements AnychartView
        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Sunday", 300));
        data.add(new ValueDataEntry("Monday", 600));
        data.add(new ValueDataEntry("Tuesday", 500));
        data.add(new ValueDataEntry("Wednesday", 900));
        data.add(new ValueDataEntry("Thursday", 888));
        data.add(new ValueDataEntry("Friday", 784));
        data.add(new ValueDataEntry("Saturday", 754));

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: } steps");


        final ChartScroller chartScroller = cartesian.xScroller();
        cartesian.animation(true);
        cartesian.title("Weekly Step Counter Chart");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Days");
        cartesian.yAxis(0).title("Steps");

        anyChartView.setChart(cartesian);

        //endregion


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


        int sensortype = event.sensor.getType();
        int currentValue = (int) event.values[0];
        //int value1=(int) event.values[1];
        // int value3=(int) event.values[2];
        Log.d("Value0", String.valueOf(currentValue));

        //  Log.d("Value1", String.valueOf(value1));


        //String distanceTotal=String.valueOf(distance);
        @SuppressLint("DefaultLocale") String formatedDistance = String.format("%.1f", distance);

        textView.setText(String.valueOf(counter));
        // bar.setProgress(counter);
        counter = counter + 1;


        //1 step is equal to 0.762 meter
        distance = distance + 0.762;


        textviewdistance.setText(formatedDistance);


        startService();
        Log.d("CounterValue", String.valueOf(counter));


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public void startService() {
        Intent serviceIntent = new Intent(this, StepCounterService.class);
        serviceIntent.putExtra("inputExtra", counter - 1);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    //region saving instance of the activity
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("Steps", counter);


    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


    }

    //endregion


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Steps", counter);
        editor.putFloat("Distance", (float) distance);
        editor.apply();


    }
}
