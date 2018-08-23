package com.samayu.scaction.service;

import com.samayu.scaction.domain.CreateUser;
import com.samayu.scaction.dto.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by NandhiniGovindasamy on 8/22/18.
 */

public interface SCAService {

    @POST("user/register")
    public Call<User> registerNewUser(@Body CreateUser createUser);

    @GET("user/checkUser/{fbUser}")
    public Call<User> checkUser(@Path("fbUser") String fbUser);


}
