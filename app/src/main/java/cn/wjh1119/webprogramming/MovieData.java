package cn.wjh1119.webprogramming;

/**
 * MovieData
 * Created by Mr.King on 2017/5/24 0024.
 */

public class MovieData {
    private String poster_path;

    private boolean adult;

    private String overview;

    private String release_date;

    private int id;

    private String original_title;

    private String original_language;

    private String title;

    private String backdrop_path;

    private double popularity;

    private int vote_count;

    private boolean video;

    private double vote_average;

    public void setPoster_path(String poster_path){
        this.poster_path = poster_path;
    }
    public String getPoster_path(){
        return this.poster_path;
    }
    public void setAdult(boolean adult){
        this.adult = adult;
    }
    public boolean getAdult(){
        return this.adult;
    }
    public void setOverview(String overview){
        this.overview = overview;
    }
    public String getOverview(){
        return this.overview;
    }
    public void setRelease_date(String release_date){
        this.release_date = release_date;
    }
    public String getRelease_date(){
        return this.release_date;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setOriginal_title(String original_title){
        this.original_title = original_title;
    }
    public String getOriginal_title(){
        return this.original_title;
    }
    public void setOriginal_language(String original_language){
        this.original_language = original_language;
    }
    public String getOriginal_language(){
        return this.original_language;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setBackdrop_path(String backdrop_path){
        this.backdrop_path = backdrop_path;
    }
    public String getBackdrop_path(){
        return this.backdrop_path;
    }
    public void setPopularity(double popularity){
        this.popularity = popularity;
    }
    public double getPopularity(){
        return this.popularity;
    }
    public void setVote_count(int vote_count){
        this.vote_count = vote_count;
    }
    public int getVote_count(){
        return this.vote_count;
    }
    public void setVideo(boolean video){
        this.video = video;
    }
    public boolean getVideo(){
        return this.video;
    }
    public void setVote_average(double vote_average){
        this.vote_average = vote_average;
    }
    public double getVote_average(){
        return this.vote_average;
    }

}