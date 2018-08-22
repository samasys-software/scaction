package com.samayu.scaction.service;

import com.samayu.scaction.domain.CreateUser;
import com.samayu.scaction.dto.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by NandhiniGovindasamy on 8/22/18.
 */

public interface SCAService {

    @POST("user/register")
    public Call<User> registerDevice(@Body CreateUser createUser);


}
