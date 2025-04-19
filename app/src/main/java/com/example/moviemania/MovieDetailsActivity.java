package com.example.moviemania;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviemania.adapter.MovieAdapter;
import com.example.moviemania.api.ApiInterface;
import com.example.moviemania.api.RetroInstance;
import com.example.moviemania.classes.Movie;
import com.example.moviemania.classes.MovieDetails;
import com.example.moviemania.classes.MovieResponse;
import com.example.moviemania.databinding.ActivityMovieDetailsBinding;
import com.example.moviemania.utility.MySharedPref;
import com.example.moviemania.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    private ActivityMovieDetailsBinding binding = null;
    private MovieAdapter movieAdapter = null;
    private MySharedPref mySharedPref = null;

    private Movie movie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Movie Details");
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mySharedPref = new MySharedPref(this);

        setupAdapter();
        setClickListener();

        movie = (Movie) getIntent().getSerializableExtra("movie");

        updateFavouriteStatus();
        readMovieDetails();
        showRecommendation();
    }

    private void updateFavouriteStatus(){
        if( mySharedPref.isInFavourite(movie) ){
            binding.ivFavorite.setImageResource(R.drawable.heart_filled);
        }
        else{
            binding.ivFavorite.setImageResource(R.drawable.heart_gap);
        }
    }

    private void setupAdapter() {
        movieAdapter = new MovieAdapter(this, movie -> {
            Intent intent = new Intent(this, MovieDetailsActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        });
        binding.recyclerRecommendations.setAdapter(movieAdapter);
    }

    private void setClickListener(){

        final MySharedPref mySharedPref = new MySharedPref(this);

        binding.ivFavorite.setOnClickListener(v ->{

            if( mySharedPref.isInFavourite(movie) ){
                mySharedPref.removeFromFavourite(movie);
            }
            else{
                mySharedPref.addToFavourite(movie);
            }

            updateFavouriteStatus();
        });
    }

    private void readMovieDetails(){

        final ApiInterface apiInterface = RetroInstance.getApiInterface();

        apiInterface.getMovieDetails(movie.id).enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetails> call, @NonNull Response<MovieDetails> response) {
                setData(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetails> call, @NonNull Throwable t) {
                Utility.showToast(MovieDetailsActivity.this, t.getMessage());
            }
        });

    }

    private void setData(MovieDetails data){
        if(data == null) return;


        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w200/"+data.getImage())
                .into(binding.imagePoster);

        binding.textTitle.setText( data.getTitle() );
        binding.textTagline.setText( data.getTagLine() );
        binding.textDetails.setText( data.getFormattedGenres() );
        binding.textOverview.setText( data.getOverview() );
        binding.textRating.setText( data.getRating() );
    }

    private void showRecommendation(){
        progressDialog = new ProgressDialog(MovieDetailsActivity.this);
        progressDialog.setMessage("Loading movies...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiInterface = RetroInstance.getApiInterface();

        apiInterface.getMovieRecommendation(movie.id).enqueue(new Callback<>() {

            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                addToAdapter(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Utility.showToast(MovieDetailsActivity.this, t.getMessage());
            }
        });

    }

    private void addToAdapter(MovieResponse response){
        if(response == null) return;

        movieAdapter.submitList( response.results );
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Use the default system behavior
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Default back behavior
        finish(); // Close this activity
    }
}
