package com.burakarslan.yakalacoforcorp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;
    private static Gson gson=null;
    private static final String BASE_URL = "http://mobileapi.yakala.co/api/";

    private RetrofitInstance() {}

    public static Gson getRetrofitGson(){
        gson=new GsonBuilder()
                .setLenient()
                .create();
        return gson;
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getRetrofitGson()))
                    .build();
        }
        return retrofit;
    }
}
