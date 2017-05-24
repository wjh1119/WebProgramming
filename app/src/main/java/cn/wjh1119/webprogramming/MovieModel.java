package cn.wjh1119.webprogramming;

import java.util.List;

/**
 * MovieModel
 * Created by Mr.King on 2017/5/24 0024.
 */

public class MovieModel {
    private int page;

    private List<MovieData> results ;

    private int total_results;

    private int total_pages;

    public void setPage(int page){
        this.page = page;
    }
    public int getPage(){
        return this.page;
    }
    public void setResults(List<MovieData> results){
        this.results = results;
    }
    public List<MovieData> getResults(){
        return this.results;
    }
    public void setTotal_results(int total_results){
        this.total_results = total_results;
    }
    public int getTotal_results(){
        return this.total_results;
    }
    public void setTotal_pages(int total_pages){
        this.total_pages = total_pages;
    }
    public int getTotal_pages(){
        return this.total_pages;
    }

}
