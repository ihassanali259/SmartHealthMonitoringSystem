package com.example.shms1;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private androidx.appcompat.widget.Toolbar mToolbar;
    private BottomNavigationView bottomnavigation;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        bottomnavigation = findViewById(R.id.navigationview);
        bottomnavigation.setOnNavigationItemSelectedListener(this);

        username = getIntent().getStringExtra("USER_NAME");
        Log.d("Username", "onCreate: " + username);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        loadFragment(new HomeFragment());



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }


    //Load the fragments in framelayout
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.framelayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    //NavigationView item Click Listener
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                loadFragment(new HomeFragment());

                break;
            case R.id.navigation_report:
                Toast.makeText(getApplicationContext(), "Click is working Fine", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_profile:
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                i.putExtra("USER_NAME", username);
                startActivity(i, options.toBundle());
                //Toast.makeText(getApplicationContext(), "Click is working Fine of profile", Toast.LENGTH_SHORT).show();

        }

        return false;
    }
}
