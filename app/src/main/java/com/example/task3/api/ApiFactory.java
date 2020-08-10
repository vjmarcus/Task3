package com.example.task3.api;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {
    //https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=92632cb5e9be43ef9dfc1545d021180a
    public static final String NEWS_API_KEY = "92632cb5e9be43ef9dfc1545d021180a";
    public static final String TOPIC = "q=software";
    public static final String PAGE_SIZE = "pageSize=20";
    public static  String dateFrom = "&from=";
    public static final String URL = "https://newsapi.org/v2/everything?" + TOPIC + dateFrom + PAGE_SIZE +
            "&apiKey=" + NEWS_API_KEY;
    public static final String BASE_URL = "https://newsapi.org/v2/";
    private static final String TAG = "MyApp";

    public static ApiFactory apiFactory;
    private static Retrofit retrofit;

    public static ApiFactory getInstance() {
        if (apiFactory == null)
            apiFactory = new ApiFactory();
        return apiFactory;
    }

    public static NewsApi getNewsApi() {
        return retrofit.create(NewsApi.class);
    }

    private ApiFactory() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }
    public static String getCurrentDate() {
        long date = System.currentTimeMillis();
        String pattern = "YYYY-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date(date));
    }
}
