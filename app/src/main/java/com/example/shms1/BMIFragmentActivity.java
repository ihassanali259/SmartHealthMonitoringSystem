package com.example.shms1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BMIFragmentActivity extends AppCompatActivity {

    EditText editTextWeigthfield;
    EditText editTextHeightfield;
    Button bmibtnProceed;

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void loadFragment(Fragment fragment) {
        Bundle data = new Bundle();
        data.putDouble("WEIGHT", Double.parseDouble(editTextWeigthfield.getText().toString()));
        data.putDouble("HEIGHT", Double.parseDouble(editTextHeightfield.getText().toString()));
        fragment.setArguments(data);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentResult, fragment).commit();

    }

//    private void loadhomefragment(Fragment fragment) {
    //      FragmentManager fm = getFragmentManager();

    //    FragmentTransaction fragmentTransaction = fm.beginTransaction();
    //  fragmentTransaction.add(R.id.fragmentcalculator, fragment).commit();


    //  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmifragment);

        //Initializing Components
        editTextWeigthfield = findViewById(R.id.weightfield);
        editTextHeightfield = findViewById(R.id.height_field);
        bmibtnProceed = findViewById(R.id.btn_calculateProceed);


        bmibtnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ResultFragment());
                hideSoftKeyboard(BMIFragmentActivity.this);

            }
        });




    }

}
