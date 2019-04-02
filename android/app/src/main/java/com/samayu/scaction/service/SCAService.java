package com.samayu.scaction.service;

import android.content.res.Resources;

import com.samayu.scaction.domain.CreateUser;
import com.samayu.scaction.dto.CastingCall;
import com.samayu.scaction.dto.CastingCallApplication;
import com.samayu.scaction.dto.City;
import com.samayu.scaction.dto.Country;
import com.samayu.scaction.dto.Portfolio;
import com.samayu.scaction.dto.PortfolioPicture;
import com.samayu.scaction.dto.ProfileDefaults;
import com.samayu.scaction.dto.ProfileType;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.dto.UserNotification;
import com.squareup.picasso.Downloader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by NandhiniGovindasamy on 8/22/18.
 */

public interface SCAService {

//User Rest Calls

    @POST("user/register")
    @FormUrlEncoded

    public Call<User> registerNewUser(@Field("fbUser") String fbUser,
                                      @Field("screenName") String screenName,
                                      @Field("fbName") String name,
                                      @Field("fbEmail") String fbEmail,
                                      @Field("countryCode") String countryCode,
                                      @Field("cityId") String city,
                                      @Field("phoneNumber") String phoneNumber,
                                      @Field("whatsappNumber") String whatsappNumber,
                                      @Field("gender") String gender,
                                      @Field("dateOfBirth") String dateOfBirth,
                                      @Field("searchable") String searchable,
                                      @Field("profilePic") String profilePic,
                                      @Field("roles") int[] roles);
//
//    @POST("user/method5")
//    @FormUrlEncoded
//
//    public Call<String> sample(@Field("id") int id);

    @GET("user/checkUser/{fbUser}")
    public Call<User> checkUser(@Path("fbUser") String fbUser);

    @GET("user/notifications/{userId}")
    public Call<List<UserNotification>> getUserNotifications(@Path("userId") long userId);

    @POST("user/applyForCastingCall")
    @FormUrlEncoded
    public Call<Boolean> applyCastingCall(
            @Field("castingCallId") long castingCallId ,
            @Field("userId") long userId ,
            @Field("roleId") int roleId
    );

    @POST("user/uploadPicture")
    @Multipart
    public Call<List<PortfolioPicture>> uploadPicture(
            @Part("userId") long userId ,
            @Part("pictureType") int pictureType ,
            @Part MultipartBody.Part file );

    @POST("user/deletePicture")
    @FormUrlEncoded
    public Call<List<PortfolioPicture>> deletePicture(
            @Field("userId") long userId ,
            @Field("portfolioId") long portfolioId );

    @GET("user/downloadFile/{userId}/{filename}")
    public Call<ResponseBody> downloadFile(@Path("filename") String filename , @Path("userId") long userId);

    @GET("user/getAllPortfolio/{userId}" )
    public Call<List<PortfolioPicture>> findAllPortfolio(@Path("userId") long userId );

    @POST("global/updatePortfolio/{userId}")
    public Call<Portfolio> updatePortfolio(
            @Path("userId") long userId,
            @Field("shortDesc") String shortDesc,
            @Field("areaOfExpertise") String areaOfExpertise,
            @Field("projectWorked") String projectWorked,
            @Field("message") String message );

    @GET("global/getPortfolio/{userId}")
    public Call<Portfolio> getPortfolio(@Path("userId") long userId );


//Coordinator Rest calls

    @GET( "coordinator/castingcalls/{userId}")
    public Call<List<CastingCall>> getMyCastingCalls(@Path("userId" ) long userId);

    @GET("coordinator/castingCallApplications/{castingCallId}")
    public Call<List<CastingCallApplication >> getCastingCallApplications(@Path("castingCallId") long castingCallId );


//Global Rest Calls

    @GET("global/countries")
    public Call<List<Country>> getCountries();

    @GET("global/cities/{countryId}")
    public Call<List<City>> getCities(@Path("countryId") String countryId);

    @GET("global/profileTypes")
    public Call<List<ProfileType>> getProfileTypes() ;

    @GET("global/profileDefaults")
    public Call<ProfileDefaults> getProfileDefaults();

    @POST("global/castingCall")
    @FormUrlEncoded
    public Call<CastingCall> createCastingCall(
            @Field("castingCallId") long castingCallId,
            @Field("projectName") String projectName,
            @Field("projectDetails") String projectDetails,
            @Field("productionCompany") String productionCompany,
            @Field("roleDetails") String roleDetails,
            @Field("startAge") int startAge,
            @Field("endAge") int endAge,
            @Field("gender") int gender,
            @Field("cityId") int cityId,
            @Field("countryId") int countryId,
            @Field("address") String address,
            @Field("startDate") String startDate,
            @Field("endDate")  String endDate,
            @Field("hours") String hours,
            @Field("userId") long userId,
            @Field("roleIds") String[] roleIds
    ) ;


    @GET("global/talentImages")
    public Call<List<User>> getTopProfiles();

    @GET("global/castingcalls")
    public Call<List<CastingCall>> getAllCastingCalls();

    @GET( "global/castingcallDetails/{castingCallId}")
    public Call<CastingCall> getCastingCall(@Path("castingCallId") long castingCallId,@Query("userId") long userId );

    @GET( "global/search/{pageNo}/{profilesPerPage}")
    public Call<List<User>> getActorProfiles(@Path("pageNo") int pageNo, @Path("profilesPerPage") int resultSize );






}
