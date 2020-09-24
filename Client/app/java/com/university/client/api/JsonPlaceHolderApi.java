package com.university.client.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.university.client.entity.Operation;
import com.university.client.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("/operations/all")
    Call<JsonArray> getAllOperations();

    @POST("/auth/signin")
    Call<User> auth(@Body User user);

    @POST("/auth/signup")
    Call<User> registration(@Body User user);

    @POST("/operations/add")
    Call<JsonElement> addOperation(@Body Operation operation);

    @PUT("/operations/operation/{id}")
    Call<JsonElement> editOperation(@Path("id") Long Id, @Body Operation operation);

    @DELETE("/operations/operation/{id}")
    Call<JsonElement> deleteOperation(@Path("id") Long Id);
}
