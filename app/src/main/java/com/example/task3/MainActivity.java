package com.example.task3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.task3.adapter.StoryAdapter;
import com.example.task3.api.ApiFactory;
import com.example.task3.api.NewsApi;
import com.example.task3.model.Source;
import com.example.task3.model.Story;
import com.example.task3.model.StoryList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyApp";

    private TextView authorTextView;
    private TextView titleTextView;
    private TextView publishedAdTextView;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private List<Story> storyList;
    private StoryAdapter storyAdapter;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        init();

        ApiFactory apiFactory = ApiFactory.getInstance();
        NewsApi newsApi = ApiFactory.getNewsApi();

//
//        call.enqueue(new Callback<StoryList>() {
//            @Override
//            public void onResponse(Call<StoryList> call, Response<StoryList> response) {
//                if (response.isSuccessful()) {
//                    StoryList articlesList = response.body();
//                    assert articlesList != null;
//                    storyList = artiCall<StoryList> call = newsApi.getPosts();clesList.getArticles();
//                    initRecycler();
//                    Log.d(TAG, "onResponse: " + storyList.size());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<StoryList> call, Throwable t) {
//                Log.d(TAG, "onFailure: " + t.getMessage());
//            }
//        });
        Call<StoryList> call = newsApi.getPostsByDate(ApiFactory.getCurrentDate());
        call.enqueue(new Callback<StoryList>() {
            @Override
            public void onResponse(Call<StoryList> call, Response<StoryList> response) {
                StoryList articlesList = response.body();
                storyList = articlesList.getArticles();
                initRecycler();
                    Log.d(TAG, "onResponse: " + storyList.size());
            }

            @Override
            public void onFailure(Call<StoryList> call, Throwable t) {

            }
        });
    }


    private void init() {
        authorTextView = findViewById(R.id.authorTextView);
        titleTextView = findViewById(R.id.titleTextView);
        publishedAdTextView = findViewById(R.id.publishedAtTextView);
    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.story_recycler);
        storyAdapter = new StoryAdapter(storyList);
        recyclerView.setAdapter(storyAdapter);
    }

    private void addTestStories() {
        storyList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            storyList.add(new Story(new Source("sourceName"), "author" + i, "title" + i,
                    "description" + i, "urlToImage" + i, "publishedAt" + i));
        }
    }
}