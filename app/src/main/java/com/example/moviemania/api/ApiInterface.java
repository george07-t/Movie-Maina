package com.example.moviemania.api;

import com.example.moviemania.classes.ChatRequest;
import com.example.moviemania.classes.ChatResponse;
import com.example.moviemania.classes.MovieDetails;
import com.example.moviemania.classes.MovieResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("upcoming")
    Call<MovieResponse> getUpcomingMovies();

    @GET("{movie_id}")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") long movieId);

    @GET("{movie_id}/recommendations")
    Call<MovieResponse> getMovieRecommendation(@Path("movie_id") long movieId);

    // Chat endpoints
    @POST("chat")
    Call<ChatResponse> sendChatMessage(@Body ChatRequest request);

    @DELETE("sessions/{session_id}")
    Call<Void> deleteSession(@Path("session_id") String sessionId);

    @DELETE("sessions")
    Call<Void> clearAllSessions();
}