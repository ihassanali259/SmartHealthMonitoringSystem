package com.example.shms1;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EmailVerificationClass {

    JSONObject jsonObject;
    String EmailString = "email=";
    String myresponse;
    String email;
    String status;

    String result = "cvvsa", reslfromjson;

    public void emailVerifyRequest(String email) {

        EmailString += email;

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, EmailString);
        Request request = new Request.Builder()
                .url("https://siimplemail.p.rapidapi.com/api/verified_emails")
                .post(body)
                .addHeader("x-rapidapi-host", "siimplemail.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "6b49d08578msh0b266a5f3568182p104f48jsncb428f33aec2")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Log.d("OnFailure", "Failed");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    String myResponse = response.body().toString();
                    Log.d("Response From server", myResponse);
                }

            }
        });


    }


}
