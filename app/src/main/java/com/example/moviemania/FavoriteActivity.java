package com.example.moviemania;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moviemania.adapter.MovieAdapter;
import com.example.moviemania.classes.Movie;
import com.example.moviemania.databinding.ActivityFavoriteBinding;
import com.example.moviemania.utility.MySharedPref;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FavoriteActivity extends AppCompatActivity {

    protected ActivityFavoriteBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Favourite Movies");
        super.onCreate(savedInstanceState);

        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    @Override
    protected void onResume() {
        super.onResume();
        showData();
    }

    private void showData(){
        final Set<Movie> set = new MySharedPref(this).getFavouriteList();

        final MovieAdapter movieAdapter = new MovieAdapter(this, movie -> {
            Intent intent = new Intent(FavoriteActivity.this, MovieDetailsActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        });

        binding.recyclerRecommendations.setAdapter(movieAdapter);

        List<Movie> movieList = new ArrayList<>(set);
        movieAdapter.submitList(movieList);
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
