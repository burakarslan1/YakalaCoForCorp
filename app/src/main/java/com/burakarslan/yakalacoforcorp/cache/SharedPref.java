package com.burakarslan.yakalacoforcorp.cache;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private static SharedPref myInstance=new SharedPref();

    private SharedPref(){

    }

    public static SharedPref getMyInstance(){
        return myInstance;
    }

    public static Context context;
    private static final String MYPREFERENCES = "MyPreferences";
    private SharedPreferences sharedPreferences;
    private boolean recordType = false;
    private String processType;

    private String email, password,token;

    public String getToken() {
        processType="token";
        recordType=false;
        setGetSharedpreferences();
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        processType="token";
        recordType=true;
        setGetSharedpreferences();
    }

    public String getEmail() {
        processType = "email";
        recordType = false;
        setGetSharedpreferences();
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        processType = "email";
        recordType = true;
        setGetSharedpreferences();
    }

    public String getPassword() {
        processType = "password";
        recordType = false;
        setGetSharedpreferences();
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        processType = "password";
        recordType = true;
        setGetSharedpreferences();
    }

    private void setGetSharedpreferences() {
        sharedPreferences = context.getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (recordType) {

            switch (processType) {
                case "email":
                    editor.putString("email", email);
                    editor.apply();
                    break;
                case "password":
                    editor.putString("password", password);
                    editor.apply();
                    break;
                case "token":
                    editor.putString("token",token);
                    editor.apply();
                    break;
            }
        } else {

            switch (processType) {
                case "email":
                    email = sharedPreferences.getString("email", email);
                    break;
                case "password":
                    password = sharedPreferences.getString("password", password);
                    break;
                case "token":
                    token = sharedPreferences.getString("token", token);
                    break;
            }
        }
    }

}
