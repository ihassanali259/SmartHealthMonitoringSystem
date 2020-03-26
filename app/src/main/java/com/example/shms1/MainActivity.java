package com.example.shms1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    public Button dietplanbtn;
    public Button logoutbtn;
    Button bmibtn;
    Button btn_step_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dietplanbtn=findViewById(R.id.Dietplanbutton);
        logoutbtn=findViewById(R.id.logoutbtn);
        bmibtn = findViewById(R.id.bmicalculatorbtn);
        btn_step_counter = findViewById(R.id.footstpcounterbtn);

        bmibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BMIFragmentActivity.class);
                startActivity(i);
            }
        });

        dietplanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.success(getApplicationContext(),"Hi there you have done an amazing work", Toast.LENGTH_SHORT).show();
            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPref", 0);
                sharedPreferences.edit().clear().apply();

                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_step_counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StepCounterActivity.class);
                startActivity(i);
            }
        });
    }
}
