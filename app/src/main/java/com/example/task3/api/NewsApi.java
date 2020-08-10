package com.example.task3.api;

import com.example.task3.model.Story;
import com.example.task3.model.StoryList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("everything?q=software&apiKey=92632cb5e9be43ef9dfc1545d021180a")
    Call <StoryList>  getPosts();

    @GET("everything?q=software&apiKey=92632cb5e9be43ef9dfc1545d021180a")
    Call <StoryList>  getPostsByDate(@Query("from=") String date);


}
