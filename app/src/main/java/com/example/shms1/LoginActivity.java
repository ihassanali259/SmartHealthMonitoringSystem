package com.example.shms1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shms1.Data.DBHandler;
import com.example.shms1.Data.UserAuthenticationData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
 private Button loginbutton;
 private EditText usernamefield;
 private Button signupbutton;
 private DBHandler dbHandler;
 private  EditText passwordfield;
 private TextView signuptext;
 SharedPreferences mypref;
 SharedPreferences.Editor editor;
 String uname;
 String pswd;






    public void setStoreloginvalue(boolean storeloginvalue) {
        this.storeloginvalue = storeloginvalue;
    }

    public boolean isStoreloginvalue() {
        return storeloginvalue;
    }

    public boolean storeloginvalue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Component initialization for login activity
        loginbutton=(Button) findViewById(R.id.buttonlogin);
        usernamefield=(EditText)findViewById(R.id.usernamefield);
        //signupbutton=(Button) findViewById(R.id.buttonsignup1);

        signuptext= findViewById(R.id.accountinfotext);
        passwordfield=(EditText) findViewById(R.id.passwordfield);



        loginbutton.setText(R.string.loginbutton);
        dbHandler=new DBHandler(getApplicationContext());




        ////////////////
       mypref= getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
         editor=mypref.edit();

     boolean islogin= mypref.getBoolean("LoginValue",false);



try {
    if (islogin) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

    }
}
catch(NullPointerException ex){
    Log.i("ExceptionMessage", ex.toString());
}



//////////////////////






        //Click events
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginbutton.setText("Please wait...");








                DatabaseReference dbuser=FirebaseDatabase.getInstance().getReference();
                Query query=dbuser.child("UserRegister").orderByChild("username").equalTo(usernamefield.getText().toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                                UserAuthenticationData user = Snapshot.getValue(UserAuthenticationData.class);

                                //  String username=user.getUsername();
                                String password = Snapshot.child("password").getValue().toString();
                              //  String username = Snapshot.child("username").getValue().toString();
                             //    Log.i("password",password);
                               if (password.equals(passwordfield.getText().toString())) {
                                    // Log.i("Message","Wroking ok");
                                    //Log.i("Password",password);
                                    //Log.i("Username", username);











                                    //Toasty.success(getApplicationContext(),"You are genius Ali",Toast.LENGTH_SHORT).show();
                                   String username=usernamefield.getText().toString();


                                   //////////////Storing data in sharedprefernces
                                   setStoreloginvalue(true);
                                   editor.putString("USER_NAME", username);
                                   editor.putString("PASSWORD",password);
                                   editor.putBoolean("LoginValue",isStoreloginvalue());
                                   editor.commit();

                                    Toasty.success(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("USER_NAME", username);
                                    startActivity(intent);
                                    finish();
                                    break;

                                } else {

                                    Toasty.error(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();


                                }


                            }

                        }
                        else{
                            loginbutton.setText(R.string.loginbutton);
                            Toasty.error(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

               /* Cursor c=dbHandler.CheckUserExist(usernamefield.getText().toString(),passwordfield.getText().toString());


                if(c.getCount()>0) {


                    //getting username from field




                }
                else{

                }*/


            }
        });

      /**  signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);
            }
        });
         **/

      signuptext.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i=new Intent(getApplicationContext(), SignupActivity.class);
              startActivity(i);
          }
      });

    }


}
