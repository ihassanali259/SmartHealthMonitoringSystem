package com.example.shms1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;

public class ResultFragment extends Fragment {

    private static DecimalFormat df = new DecimalFormat("0.00");
    public final Double UNDER_WEIGHT_LIMIT = 18.5;
    public final Double OVER_WEIGHT_LIMIT = 24.9;
    public final Double OBESE_LIMIT = 29.9;
    public ImageView image;
    String result_statement;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.result_fragment, container, false);


        //initializing components
        TextView result = view.findViewById(R.id.resulttextview);
        image = view.findViewById(R.id.imageviewbmi);


        //Getting data from activity
        Double weight = getArguments().getDouble("WEIGHT");
        Double height = getArguments().getDouble("HEIGHT");

        //Calculating BMI
        Double Bmi_level;

        height = height / 100;

        Bmi_level = weight / (height * height);

        Bmi_level = Double.valueOf(df.format(Bmi_level));

        if (Bmi_level < UNDER_WEIGHT_LIMIT) {

            result_statement = " You are Underweight. Your BMI level is " + Bmi_level;
            result.setText(result_statement);

        } else if (Bmi_level >= UNDER_WEIGHT_LIMIT && Bmi_level <= OVER_WEIGHT_LIMIT) {

            result_statement = "Your BMI level is " + Bmi_level + ". Your are healthy.";
            image.setImageResource(R.drawable.healthy);
            result.setText(result_statement);

        } else if (Bmi_level > OVER_WEIGHT_LIMIT && Bmi_level < OBESE_LIMIT) {

            result_statement = " You are Overweight. Your BMI level is " + Bmi_level;
            result.setText(result_statement);
        } else if (Bmi_level >= OBESE_LIMIT) {

            result_statement = " You are Highly Overweight. Your BMI level is " + Bmi_level;
            result.setText(result_statement);
        }


        return view;
    }
}
