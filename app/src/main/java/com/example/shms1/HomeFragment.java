package com.example.shms1;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    public View view;
    @BindView(R.id.bmi_calculatetextview)
    TextView textViewbmi;
    @BindView(R.id.textviewStepcountr)
    TextView textviewStepcounter;
    @BindView(R.id.textviewdietplan)
    TextView textViewdiestplan;
    @BindView(R.id.Stepcountercardview)
    CardView stepcountercardview;
    @BindView(R.id.Bmicardview)
    CardView bmicardview;
    @BindView(R.id.Dietplancardview)
    CardView dietplancardview;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        //Setting up listener for cardviews
        dietplancardview.setOnClickListener(this::onClick);
        stepcountercardview.setOnClickListener(this::onClick);
        bmicardview.setOnClickListener(this::onClick);


        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Dietplancardview:
                Intent i = new Intent(getContext(), DietActivity.class);
                startActivity(i);
                break;
            case R.id.Stepcountercardview:
                Intent i2 = new Intent(getContext(), StepCounterActivity.class);
                startActivity(i2);
                break;
            case R.id.Bmicardview:
                Intent i3 = new Intent(getContext(), BMIFragmentActivity.class);
                startActivity(i3);


        }
    }
}
