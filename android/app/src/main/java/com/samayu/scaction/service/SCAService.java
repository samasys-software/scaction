package com.samayu.scaction.service;

import com.samayu.scaction.domain.CreateUser;
import com.samayu.scaction.dto.City;
import com.samayu.scaction.dto.Country;
import com.samayu.scaction.dto.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by NandhiniGovindasamy on 8/22/18.
 */

public interface SCAService {

    @POST("user/register")
    @FormUrlEncoded

    public Call<User> registerNewUser(@Field("fbUser") String fbUser,
                                      @Field("screenName") String screenName,
                                      @Field("fbName") String name,
                                      @Field("fbEmail") String fbEmail,
                                      @Field("countryCode") String countryCode,
                                      @Field("city") String city,
                                      @Field("phoneNumber") String phoneNumber,
                                      @Field("whatsappNumber") String whatsappNumber,
                                      @Field("gender") String gender,
                                      @Field("dateOfBirth") String dateOfBirth,
                                      @Field("searchable") String searchable,
                                      @Field("profilePic") String profilePic,
                                      @Field("roles") String[] roles);

    @POST("user/method5")
    @FormUrlEncoded

    public Call<String> sample(@Field("id") int id);

    @GET("user/checkUser/{fbUser}")
    public Call<User> checkUser(@Path("fbUser") String fbUser);

    @GET("global/countries")
    public Call<List<Country>> getCountries();

    @GET("global/cities/{countryId}")
    public Call<List<City>> getCities(@Path("countryId") String countryId);



}
