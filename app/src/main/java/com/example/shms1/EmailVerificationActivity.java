package com.example.shms1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EmailVerificationActivity extends AppCompatActivity {
    @BindView(R.id.buttonverify)
    Button btn;
    JSONObject jsonObject;
    String EmailString = "email=";
    String myresponse;
    String email;
    String status;

    String result = "cvvsa", reslfromjson;


    String myresp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        ButterKnife.bind(this);
        String email = getIntent().getStringExtra("Email");


        EmailVerificationClass emf = new EmailVerificationClass();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EmailVerifyResponse(email);
            }
        });
    }


    //Get All the Verified emails for api then pass it to JSONParser
    public void EmailVerifyResponse(String emailString) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://siimplemail.p.rapidapi.com/api/verified_emails")
                .get()
                .addHeader("x-rapidapi-host", "siimplemail.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "6b49d08578msh0b266a5f3568182p104f48jsncb428f33aec2")
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()) {

                    String username = getIntent().getStringExtra("Username");

                    myresponse = response.body().string();
                    //Log.d("RESPONSE", myresponse);

                    result = parsejSON(myresponse, emailString);

                    if (result.equals("true")) {
                        Log.d("Success", "Verified");
                        Intent i = new Intent(getApplicationContext(), ProgressBarActivity.class);
                        i.putExtra("USER_NAME", username);
                        startActivity(i);
                    } else {
                        new Thread() {
                            @Override
                            public void run() {
                                EmailVerificationActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Please verify your email address", Toast.LENGTH_SHORT).show();


                                    }
                                });
                            }
                        }.start();
                        // Toast.makeText(getApplicationContext(),"Please verify your email address",Toast.LENGTH_SHORT).show();
                    }
                    Log.d("Failed", "onClick: verification Failed ");


                }

            }
        });


    }


    //Parse Json to extract data from it
    public String parsejSON(String response, String emailString) {

        try {
            // Log.d("ParseJson", "parsejSON:Caliing From it ");

            JSONObject o = new JSONObject(response);


            JSONArray jsonarray = o.getJSONArray("verified_emails");
            email = jsonarray.getJSONObject(0).getString("email");
            //Log.d("Email",email);

            for (int i = 0; i < jsonarray.length(); i++) {

                jsonObject = jsonarray.getJSONObject(i);

                email = jsonObject.getString("email");
                status = jsonObject.getString("status");

                if (email.equals(emailString)) {

                    break;

                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        Log.d("Email", email);
        Log.d("Status", status);

        if (email.equals(emailString) && status.equals("verified")) {
            reslfromjson = "true";
            return reslfromjson;
        } else {
            reslfromjson = "false";
            Log.d(TAG, "parsejSON: " + reslfromjson);
            return reslfromjson;
        }


    }


}
