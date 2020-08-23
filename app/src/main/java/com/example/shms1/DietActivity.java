package com.example.shms1;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shms1.Data.CaloriesCalculator;


public class DietActivity extends AppCompatActivity {
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        spinner = findViewById(R.id.spinner_actvities);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.daily_activity_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        CaloriesCalculator cc = new CaloriesCalculator(20, 73, 170, 2, 0);

        double cal = cc.calculateCalories();


        Log.d("CALORIES", String.valueOf(cal));
    }
}
