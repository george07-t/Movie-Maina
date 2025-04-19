package com.example.moviemania.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {

    private static ApiInterface apiInterface = null;

    public static ApiInterface getApiInterface(){
        if(apiInterface != null) return apiInterface;

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return apiInterface = retrofit.create(ApiInterface.class);
    }

}
