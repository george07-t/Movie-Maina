package com.example.moviemania;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviemania.adapter.MovieAdapter;
import com.example.moviemania.api.ApiInterface;
import com.example.moviemania.api.RetroInstance;
import com.example.moviemania.classes.MovieResponse;
import com.example.moviemania.databinding.ActivityMainBinding;
import com.example.moviemania.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private MovieAdapter movieAdapter = null;
    private ActivityMainBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupAdapter();
        loadMovieData();
    }

    private void setupAdapter(){
        movieAdapter = new MovieAdapter(this, movie -> {
            Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        });
        binding.recyclerViewMovies.setAdapter(movieAdapter);
    }

    private void loadMovieData(){
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading movies...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final ApiInterface apiInterface = RetroInstance.getApiInterface();

        apiInterface.getUpcomingMovies().enqueue(new Callback<>() {
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
                Utility.showToast(MainActivity.this, t.getMessage());
            }
        });
    }

    private void addToAdapter(MovieResponse response){
        if(response == null) return;

        movieAdapter.submitList( response.results );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            Intent intent= new Intent(this, FavoriteActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}