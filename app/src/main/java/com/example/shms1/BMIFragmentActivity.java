package com.example.shms1;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.scwang.wave.MultiWaveHeader;

import de.hdodenhof.circleimageview.CircleImageView;

public class BMIFragmentActivity extends AppCompatActivity {

    MultiWaveHeader waveHeader;
    Button buttonmale, buttonfemale;
    CircleImageView circleImageView;
    AnimationDrawable animationDrawable;

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
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

        buttonfemale = findViewById(R.id.buttonfemale);
        buttonmale = findViewById(R.id.buttonmale);

        circleImageView = findViewById(R.id.imgview_anim);
        circleImageView.setBackgroundResource(R.drawable.enter_refresh);
        animationDrawable = (AnimationDrawable) circleImageView.getBackground();

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationDrawable.start();
            }
        });


        buttonfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonfemale.setBackground(getResources().getDrawable(R.drawable.mybuttons2));
                buttonmale.setBackground(getResources().getDrawable(R.drawable.mybuttons));
            }
        });

        buttonmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonfemale.setBackground(getResources().getDrawable(R.drawable.mybuttons));
                buttonmale.setBackground(getResources().getDrawable(R.drawable.mybuttons2));
            }
        });

        waveHeader = findViewById(R.id.MultiWaveheader);

        waveHeader.setVelocity(1);
        waveHeader.setProgress(1);
        waveHeader.isRunning();
        waveHeader.setGradientAngle(45);
        waveHeader.setWaveHeight(40);


    }

}
