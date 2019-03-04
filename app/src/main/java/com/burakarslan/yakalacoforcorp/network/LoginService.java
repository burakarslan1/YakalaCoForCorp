package com.burakarslan.yakalacoforcorp.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @POST("customer/login")
    @FormUrlEncoded
    Call<String> userLogin(@Field("Email") String email, @Field("Password") String password);
}
