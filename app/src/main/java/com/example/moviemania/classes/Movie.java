package com.example.moviemania.classes;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class Movie implements Comparable<Movie>, Serializable {

    @SerializedName("id")
    public long id;
    @SerializedName("title")
    public String title;
    @SerializedName("poster_path")
    public String image;
    @SerializedName("release_date")
    public String releaseDate;
    @SerializedName("vote_average")
    public String rating;

    public Movie(long id, String title, String image, String releaseDate, String rating) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public boolean fullyEquals(Movie item){
        return Objects.equals(title, item.title) &&
                Objects.equals(image, item.image) &&
                Objects.equals(releaseDate, item.releaseDate) &&
                Objects.equals(rating, item.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public int compareTo(Movie item) {
        return Long.compare(id, item.id);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if( !(obj instanceof Movie) ) return false;

        return id == ((Movie)obj).id;
    }
}
