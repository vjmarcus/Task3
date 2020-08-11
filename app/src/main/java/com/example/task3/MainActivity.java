package com.example.task3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "MyApp";

    private Spinner spinner;
    private List<Story> storyList;
    private RecyclerViewClickListener recyclerViewClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        setTitle("Software");
        initSpinner();
    }

    private void getCallToNewsApi(String key) {
        ApiFactory apiFactory = ApiFactory.getInstance();
        NewsApi newsApi = ApiFactory.getNewsApi();
        Call<StoryList> call = newsApi.getPostsByDate(key, ApiFactory.getCurrentDate(),
                ApiFactory.getCurrentDate(), 20, "en", ApiFactory.API_KEY);
        call.enqueue(new Callback<StoryList>() {
            @Override
            public void onResponse(Call<StoryList> call, Response<StoryList> response) {
                Log.d(TAG, "onResponse: " + response);
                StoryList articlesList = response.body();
                assert articlesList != null;
                storyList = articlesList.getArticles();
                initRecyclerViewClickListener();
                initRecycler();
                Log.d(TAG, "onResponse: " + storyList.size());
            }

            @Override
            public void onFailure(Call<StoryList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR = " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.story_recycler);
        StoryAdapter storyAdapter = new StoryAdapter(storyList, recyclerViewClickListener);
        recyclerView.setAdapter(storyAdapter);
    }

    private void initRecyclerViewClickListener() {
        recyclerViewClickListener = new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int position) {
                Toast.makeText(MainActivity.this, "CLICK " + position, Toast.LENGTH_SHORT).show();
                startIntentToSecondActivity(position);
            }
        };
    }

    private void startIntentToSecondActivity(int position) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("obj", storyList.get(position));
        startActivity(intent);
    }

    private void addTestStories() {
        storyList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            storyList.add(new Story(new Source("sourceName"), "author" + i, "title" + i,
                    "description" + i, "urlToImage" + i, "publishedAt" + i));
        }
    }

    private void initSpinner() {
        spinner = findViewById(R.id.spinner);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.topics,
                android.R.layout.simple_list_item_1);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getSelectedItem().toString()) {
            case "bitcoin":
                getCallToNewsApi("bitcoin");
                setTitle("Bitcoin");
                break;
            case "android":
                getCallToNewsApi("android");
                setTitle("Android");
                break;
            case "science":
                getCallToNewsApi("science");
                setTitle("Science");
                break;
            case "technology":
                getCallToNewsApi("technology");
                setTitle("Technology");
                break;
            default:
                getCallToNewsApi("software");
                setTitle("Software");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //DO NOTHING
    }
}