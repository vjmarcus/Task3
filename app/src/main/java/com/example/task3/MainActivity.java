package com.example.task3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import retrofit2.internal.EverythingIsNonNull;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String SOFTWARE = "software";
    private static final String ANDROID = "android";
    private static final String SCIENCE = "science";
    private static final String TECHNOLOGY = "technology";
    private static final String BITCOIN = "bitcoin";
    private static final String TAG = "MyApp";
    private Spinner spinner;
    private List<Story> storyList;
    private RecyclerViewClickListener recyclerViewClickListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ResponseListener responseListener;

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //DO NOTHING
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getSelectedItem().toString()) {
            case BITCOIN:
                getCallToNewsApi(BITCOIN);
                setTitle(getString(R.string.bitcoin));
                break;
            case ANDROID:
                getCallToNewsApi(ANDROID);
                setTitle(getString(R.string.android));
                break;
            case SCIENCE:
                getCallToNewsApi(SCIENCE);
                setTitle(getString(R.string.science));
                break;
            case TECHNOLOGY:
                getCallToNewsApi(TECHNOLOGY);
                setTitle(getString(R.string.technology));
                break;
            default:
                getCallToNewsApi(SOFTWARE);
                setTitle(getString(R.string.software));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        initResponseListener();
        initSpinner();
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout(){
        swipeRefreshLayout = findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCallToNewsApi(spinner.getSelectedItem().toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initResponseListener() {
        responseListener = new ResponseListener() {
            @Override
            public void responseReceived(Boolean isFinished) {
                if (isFinished) {
                    Toast.makeText(MainActivity.this, "LOAD OK!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "LOAD FAILURE", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void getCallToNewsApi(String key) {
        ApiFactory apiFactory = ApiFactory.getInstance();
        NewsApi newsApi = ApiFactory.getNewsApi();
        Call<StoryList> call = newsApi.getPostsByDate(key, ApiFactory.getCurrentDate(),
                ApiFactory.getCurrentDate(), 20, "en", ApiFactory.API_KEY);
        call.enqueue(new Callback<StoryList>() {
            @Override
            public void onResponse(@NonNull  Call<StoryList> call, @NonNull Response<StoryList> response) {
                Log.d(TAG, "onResponse: " + response);
                StoryList articlesList = response.body();
                if (articlesList != null) {
                    storyList = articlesList.getArticles();
                    initRecyclerViewClickListener();
                    initRecycler();
                    Log.d(TAG, "onResponse: " + storyList.size());
                    responseListener.responseReceived(true);
                }
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<StoryList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR = " + t.getMessage(), Toast.LENGTH_SHORT).show();
                responseListener.responseReceived(false);
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

    private void initSpinner() {
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<?> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.topics,
                android.R.layout.simple_list_item_1);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
    }
}