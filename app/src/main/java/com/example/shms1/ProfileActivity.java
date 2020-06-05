package com.example.shms1;

import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shms1.Data.UserAuthenticationData;
import com.example.shms1.Data.UserHealthData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

public class ProfileActivity extends AppCompatActivity {

    String username;
    String fname;
    String lname;
    String full_name;
    @BindView(R.id.textviewprofilename)
    TextView textViewname;

    @RequiresApi(api = LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        //set an enter transition

        getWindow().setEnterTransition(new Slide());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        username = getIntent().getStringExtra("USER_NAME");


        DatabaseReference dbreference = FirebaseDatabase.getInstance().getReference();

        Query query = dbreference.child("UserRegister").orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        UserAuthenticationData userdata = dataSnapshot1.getValue(UserAuthenticationData.class);

                        fname = dataSnapshot1.child("firstname").getValue().toString();
                        lname = dataSnapshot1.child("lastname").getValue().toString();
                        Log.d("Firstname", fname);

                        full_name = fname + " " + lname;
                        Log.d("FullName", full_name);

                        textViewname.setText(full_name);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        UserHealthData userHealthData = new UserHealthData(400, 5.34, 700);
        dbreference.child("UserRegister").child(username).setValue(userHealthData);
    }
}
