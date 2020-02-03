package com.example.shms1.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME="HealthInfo";
    private static final int DB_VERSION=1;

    //User registration data table
    private static final String TABLE_NAME="Register";
    private static final String ID_COL="id";
    private static final String FIRST_NAME_COL="first_name";
    private static final String LAST_NAME_COL="last_name";
    private static final String EMAIL_COL="email";
    private static final String USER_NAME_COL="user_name";
    private static final String PASSWORD_COL="password";
    private static final String GENDER_COL="gender";

    public DBHandler(Context context){
        super(context,DB_NAME,null,DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating Registration table
        String query="CREATE TABLE "+TABLE_NAME+"("+ID_COL+" INTEGER PRIMARY KEY AUTOINCREMENT,"+FIRST_NAME_COL+" TEXT,"+
                LAST_NAME_COL+" TEXT,"+EMAIL_COL+" TEXT,"+USER_NAME_COL+" TEXT,"+PASSWORD_COL+" TEXT,"+GENDER_COL+" TEXT )";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        onCreate(db);
    }

    public void InsertRegistrationRecord(String fname,String lname, String email, String username,String pswrd,String gender){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(FIRST_NAME_COL,fname);
        values.put(LAST_NAME_COL,lname);
        values.put(EMAIL_COL,email);
        values.put(USER_NAME_COL,username);
        values.put(PASSWORD_COL,pswrd);
        values.put(GENDER_COL,gender);

        db.insert(TABLE_NAME,null,values);
        db.close();

    }
//this one is only for checking user account
    public Cursor CheckUserExist(String username, String Password){
        String query="SELECT * FROM Register where user_name ='"+username+"' and password ='"+Password+"'";

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor c=db.rawQuery(query,null);
        return c;

    }

    //This one is for checking if username is duplicate

    public Cursor checkDuplicateUsername(String username){

            String query = "SELECT * FROM Register where user_name ='"+username+"'";
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor c = db.rawQuery(query, null);

            return c;



    }


}
