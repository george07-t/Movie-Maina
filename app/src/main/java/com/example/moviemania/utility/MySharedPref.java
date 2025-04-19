package com.example.moviemania.utility;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.moviemania.classes.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;

public class MySharedPref {

    private final Activity activity;

    public MySharedPref(Activity activity) {
        this.activity = activity;
    }

    public void addToFavourite(Movie movie){
        SharedPreferences sharedPreferences = activity.getSharedPreferences("my_shared_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<Movie> curList = getFavouriteList();
        curList.add(movie);

        Gson gson = new Gson();
        String json = gson.toJson(curList);

        editor.putString("favourite_list", json);
        editor.apply();
    }

    public void removeFromFavourite(Movie movie){
        SharedPreferences sharedPreferences = activity.getSharedPreferences("my_shared_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<Movie> curList = getFavouriteList();
        curList.remove(movie);

        Gson gson = new Gson();
        String json = gson.toJson(curList);

        editor.putString("favourite_list", json);
        editor.apply();
    }

    public boolean isInFavourite(Movie movie){
        Set<Movie> set = getFavouriteList();
        return set.contains(movie);
    }

    public Set<Movie> getFavouriteList(){
        try {
            SharedPreferences sharedPreferences = activity.getSharedPreferences("my_shared_pref", MODE_PRIVATE);
            String json = sharedPreferences.getString("favourite_list", null);

            Gson gson = new Gson();
            Type type = new TypeToken<Set<Movie>>() {}.getType();
            Set<Movie> set = gson.fromJson(json, type);

            if (set == null) return new TreeSet<>();

            return set;
        }catch (Exception ignored){
            return new TreeSet<>();
        }
    }

}
