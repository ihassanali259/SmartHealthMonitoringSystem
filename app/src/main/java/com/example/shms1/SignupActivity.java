package com.example.shms1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class SignupActivity extends AppCompatActivity {
private EditText textfirstname;
private EditText textlastname;
private EditText textemail;
private EditText textusername;
private EditText textpassword;
private RadioGroup myRadiogroup;
private RadioButton radioButton;
private Button buttonsignup;
private DatabaseReference dbuser, dbuser2;

    DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

         textfirstname=  findViewById(R.id.firstname);
         textlastname=   findViewById(R.id.lstname);
         textemail=       findViewById(R.id.email);
         textusername=   findViewById(R.id.username);
         textpassword=   findViewById(R.id.passwordfield);
         myRadiogroup=    findViewById(R.id.radiogroup);
         buttonsignup=    findViewById(R.id.signupbutton);
         dbuser2=FirebaseDatabase.getInstance().getReference("UserInfo");


         dbuser= FirebaseDatabase.getInstance().getReference("UserRegister");



         db=new DBHandler(getApplicationContext());
       //  Log.i("Welcome","hi");

         buttonsignup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                if(CheckValidity()) {
                    CheckUnameifNoExistInsert();
                }
                else{
                    Toasty.error(getApplicationContext(),"Please enter valid data",Toast.LENGTH_SHORT).show();
                }
             }
         });

    }

//FunctionTOInsetData
    public void InsertData()
    {

            String fname = textfirstname.getText().toString();
            String lname = textlastname.getText().toString();
            String email = textemail.getText().toString();
            String uname = textusername.getText().toString();
            String passwrd = textpassword.getText().toString();
            String uId=dbuser.push().getKey();

            int id = myRadiogroup.getCheckedRadioButtonId();

            Log.i("Fname", fname);
        radioButton = findViewById(id);
            String myselectedbutton = radioButton.getText().toString();

            UserAuthenticationData user=new UserAuthenticationData(uId,fname,lname,email, uname, passwrd,myselectedbutton);
            dbuser.child(uId).setValue(user);

           // db.InsertRegistrationRecord(fname, lname, email, uname, passwrd, myselectedbutton);
            Toasty.success(getApplicationContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("Username",uname);
        startActivity(i);
        finish();



    }

     //Fields validation
    public boolean CheckValidity()
    {
//        Cursor c=db.checkDuplicateUsername(textusername.getText().toString());

        String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


     if(textfirstname.getText().toString().length()<3) {
         textfirstname.setError("First name should be at least 3 characters ");
         return false;
     }
     else if(textlastname.getText().toString().length()<3){
         textlastname.setError("Last name should be at least 3 characters ");
         return false;
     }
     else if(textpassword.getText().toString().length()<6){
         textpassword.setError("Password length should be more than 5 characters long");
         return false;
     }
     else if(textemail.getText().toString().trim().matches(emailpattern)==false){
         textemail.setError("Please enter a valid email address");
         return false;
     }
     else if(textusername.getText().toString().length()<5){
         textusername.setError("Username must be of length 4");
         return false;

     }
//     else if(c.getCount()>0){
  //       textusername.setError("Username already exist");
    //     return false;
     //}


     else if( myRadiogroup.getCheckedRadioButtonId()==-1){
         Toasty.error(getApplicationContext(),"Please select gender",Toast.LENGTH_SHORT).show();
         return false;
     }

     else{
         return true;
     }









    }





    //Check duplicate username
    public void CheckUnameifNoExistInsert(){



        Query query=dbuser.orderByChild("username").equalTo(textusername.getText().toString());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toasty.error(getApplicationContext(),"Please choose an other username",Toast.LENGTH_SHORT).show();

                }
                else{

                    CheckEmailDuplictionIfNOthenInsert();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    //check duplicate email

    public void CheckEmailDuplictionIfNOthenInsert(){

        Query query=dbuser.orderByChild("email").equalTo(textemail.getText().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toasty.error(getApplicationContext(),"Email address already exists",Toast.LENGTH_SHORT).show();

                }
                else{
                    InsertData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}

