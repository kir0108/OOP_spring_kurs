package com.university.client.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.university.client.entity.Article;
import com.university.client.entity.Balance;
import com.university.client.entity.Operation;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static Network instance = new Network();
    private Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.1.115:8080";
    public boolean isAdmin;

    private Network(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL   )
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void addToken(String token){
        HttpClient httpClient = new HttpClient(token);

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient.getHttpClient().build())
                .build();
    }

    public JsonPlaceHolderApi getJsonApi(){
        return retrofit.create(JsonPlaceHolderApi.class);
    }

    public static Network getInstance() {
        return instance;
    }
}

