package com.example.moviemania.api;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .addHeader("accept", "application/json")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNzA2YzYxNzA3MTJhODBlNDYyMjUzYWQwNGE3MDRlMyIsIm5iZiI6MTc0NDcwMjIzMC40MTgsInN1YiI6IjY3ZmUwYjE2N2MyOWFlNWJjM2Q5OGRhZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.3CMQn5HzNqDd2d74XBpO-rRIjsHOxW3dmBLFjQR4tio")
                .build();
        return chain.proceed(request);
    }

}
