package com.example.task3.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoryList {
    @SerializedName("articles")
    @Expose
    public List<Story> articles = null;

    public StoryList(List<Story> articles) {
        this.articles = articles;
    }

    public List<Story> getArticles() {
        return articles;
    }

    public void setArticles(List<Story> articles) {
        this.articles = articles;
    }
}
