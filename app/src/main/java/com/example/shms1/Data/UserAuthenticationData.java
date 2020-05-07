package com.example.shms1.Data;

public class UserAuthenticationData

{
 String UserID;
 String firstname;
 String lastname;
 String email;
 String username;
 String Password;
 String gender;
    //int total_Steps;

 public UserAuthenticationData(){

 }

 public UserAuthenticationData(String uid, String fname, String lname,
                               String email, String username, String Password, String gender) {

     this.UserID=uid;
     this.firstname=fname;
     this.lastname=lname;
     this.email=email;
     this.Password=Password;
     this.username=username;
     this.gender=gender;
 }

    public String getUserID() {
        return UserID;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return Password;
    }

    public String getGender() {
        return gender;
    }

    //public int getTotal_Steps(){return total_Steps;}
}
