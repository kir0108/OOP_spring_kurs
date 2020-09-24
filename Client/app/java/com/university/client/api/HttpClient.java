package com.university.client.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClient {
    private final String token;
    private OkHttpClient.Builder httpClient;

    public HttpClient(String tk) {
        token = tk;

        httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + token).build();

                Response response = chain.proceed(request);

                return response;
            }
        });
    }

    public String getToken() {
        return token;
    }

    public OkHttpClient.Builder getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(OkHttpClient.Builder httpClient) {
        this.httpClient = httpClient;
    }
}
