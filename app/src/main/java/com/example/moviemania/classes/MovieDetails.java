package com.example.moviemania.classes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetails {

    @SerializedName("poster_path")
    private String image;

    private String title;

    @SerializedName("tagline")
    private String tagLine;

    @SerializedName("overview")
    private String overview;

    @SerializedName("genres")
    private List<Genre> genreList;

    @SerializedName("vote_average")
    private String rating;

    public MovieDetails(String image, String title, String tagLine, String overview, List<Genre> genreList, String rating) {
        this.image = image;
        this.title = title;
        this.tagLine = tagLine;
        this.overview = overview;
        this.genreList = genreList;
        this.rating = rating;
    }

    public String getFormattedGenres(){
        final StringBuilder builder = new StringBuilder();
        for(Genre genre : genreList){
            builder.append(genre.getName()).append(" |");
        }

        if(builder.length() > 2){
            builder.deleteCharAt(builder.length()-1);
        }
        return builder.toString();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
