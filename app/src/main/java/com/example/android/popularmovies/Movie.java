package com.example.android.popularmovies;

/**
 * Created by tantryr on 25/10/15.
 */
public class Movie {
    private String title;
    private String releaseDate;
    private String poster;
    private double voteAverage;
    private String overview;

    public static final Movie[] movies={
            new Movie("Jurasic Park", "2015-06-12","jjBgi2r5cRt36xF6iNUEhzscEcb.jpg",6.9,"Twenty-two years after  by John Hammond."),
            new Movie("The Martian", "2015-06-12","AjbENYG3b8lhYSkdrWwlhVLRPKR.jpg",6.9,"Twenty-twoublar now d."),
            new Movie("Pixels", "2015-06-12","ktyVmIqfoaJ8w0gDSZyjhhOPpD6.jpg",6.9,"Twenty-twoublar now d."),
            new Movie("Tomorrowland", "2015-06-12","69Cz9VNQZy39fUE2g0Ggth6SBTM.jpg",6.9,"Twenty-twoublar now d."),
            new Movie("Terminator Genisys", "2015-06-12","5JU9ytZJyR3zmClGmVm9q4Geqbd.jpg",6.9,"Twenty-twoublar now d."),
            new Movie("Mad Max: Fury Road", "2015-06-12","kqjL17yufvn9OVLyXYpvtyrFfak.jpg",6.9,"Twenty-twoublar now d.")
    };

    public Movie(){

    }

    public Movie(String title, String releaseDate, String poster, double voteAverage, String overview) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.poster = poster;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
