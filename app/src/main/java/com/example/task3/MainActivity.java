package com.example.task3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String NEWS_API_KEY = "92632cb5e9be43ef9dfc1545d021180a";
    public static final String LANGUAGE = "&language=en";
    public static final String TOPIC = "sources?q=software&";
    public static final String GET = "https://newsapi.org/v2/" + TOPIC + LANGUAGE +
            "&apiKey=" + NEWS_API_KEY;
    private static final String TAG = "MyApp";

    private TextView authorTextView;
    private TextView titleTextView;
    private TextView publishedAdTextView;
    private Spinner spinner;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate " + GET);
    }
}