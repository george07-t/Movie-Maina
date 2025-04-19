package com.example.moviemania.api;

import com.example.moviemania.classes.MovieDetails;
import com.example.moviemania.classes.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("upcoming")
    Call<MovieResponse> getUpcomingMovies();

    @GET("{movie_id}")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") long movieId);

    @GET("{movie_id}/recommendations")
    Call<MovieResponse> getMovieRecommendation(@Path("movie_id") long movieId);

}
