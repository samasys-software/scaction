package com.samayu.scaction.service;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.samayu.scaction.BuildConfig;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by NandhiniGovindasamy on 8/22/18.
 */

public class SCAClient {
    public SCAService getClient(){


        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.MINUTES)
                .readTimeout(15L, TimeUnit.MINUTES)
                .writeTimeout(15L, TimeUnit.MINUTES);
        httpClientBuilder.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                try{
                    Request request = chain.request();
                    Response response = chain.proceed(request);
                    String body = response.body().string();
                    System.out.println("current API Call"+body);

                    return response.newBuilder().body(ResponseBody.create(response.body().contentType(), body)).build();
                }
                catch(Exception er ){
                    //er.printStackTrace();
                    System.out.println("Error:"+er);
                    return null;
                }

            }
        });

        String baseUrl = BuildConfig.SERVER_URL;
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//                .excludeFieldsWithoutExposeAnnotation()
//                .excludeFieldsWithModifiers(Modifier.FINAL,Modifier.STATIC,Modifier.TRANSIENT)
                .serializeNulls()
                .create();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl( baseUrl )
                .addConverterFactory( GsonConverterFactory.create(gson));

        Retrofit retrofit = builder.client( httpClientBuilder.build()).build();

        SCAService client = retrofit.create(SCAService.class);

        return client;

    }
}
//