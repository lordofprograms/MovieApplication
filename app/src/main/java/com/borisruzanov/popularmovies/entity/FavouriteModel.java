package com.borisruzanov.popularmovies.entity;

public class FavouriteModel {

    private String poster_path;
    private String id;
    private String title;
    private String release_date;
    private String vote_average;
    private String overview;

    public FavouriteModel(String id,String poster_path,  String title, String release_date, String vote_average, String overview) {
        this.id = id;
        this.poster_path = poster_path;
        this.title = title;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
