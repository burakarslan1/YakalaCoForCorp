package com.burakarslan.yakalacoforcorp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.burakarslan.yakalacoforcorp.cache.SharedPref;
import com.burakarslan.yakalacoforcorp.network.LoginService;
import com.burakarslan.yakalacoforcorp.network.RetrofitInstance;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail,etPassword;
    private Button btnLogin;
    private ProgressDialog progressDialog;
    private String token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);

        SharedPref.context=getApplicationContext();

        if(SharedPref.getMyInstance().getEmail()!=null && SharedPref.getMyInstance().getPassword()!=null && SharedPref.getMyInstance().getToken()!=null){
            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
        }

    }

    private boolean isEmpty(EditText editText){
        if (editText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public void userLogin(View view){
        if(!isEmpty(etEmail)&& !isEmpty(etPassword)){
            login();
        }else if(isEmpty(etEmail)){
            etEmail.requestFocus();
            etEmail.setError("Lütfen e-posta adresinizi giriniz!");
        }else {
            etPassword.requestFocus();
            etPassword.setError("Lütfen parolanızı giriniz!");
        }
    }

    public void login(){
        progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Lütfen bekleyiniz..");
        progressDialog.show();


        LoginService loginService= RetrofitInstance.getRetrofitInstance().create(LoginService.class);

        Call<String> call = loginService.userLogin(etEmail.getText().toString(), etPassword.getText().toString());

        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                if(response.body()!=null){
                    if(response.code()== HttpURLConnection.HTTP_OK){
                        token=response.body();

                        SharedPref.getMyInstance().setEmail(etEmail.getText().toString());
                        SharedPref.getMyInstance().setPassword(etPassword.getText().toString());
                        SharedPref.getMyInstance().setToken(token);

                        Intent intent=new Intent(getApplicationContext(),DashboardActivity.class);
                        intent.putExtra("token",token);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Giriş yapılamadı.", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Giriş yapılamadı.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Giriş yapılamadı.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
