package com.example.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.task3.model.Story;
import com.squareup.picasso.Picasso;

public class SecondActivity extends AppCompatActivity {

    private TextView titleSecondTextView;
    private TextView sourceNameTextView;
    private TextView descriptionTextView;
    private ImageView picassoImageView;
    private Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        init();
        getIntentFromMainActivity();
        setTextToTextView();
        initImageViews();
        loadImageToImageViews();
    }

    private void loadImageToImageViews() {
        Picasso.get().load(story.getUrlToImage())
                .into(picassoImageView);
    }

    private void initImageViews() {
        picassoImageView = findViewById(R.id.picassoImageView);
    }

    private void setTextToTextView() {
        titleSecondTextView.setText(story.getTitle());
        sourceNameTextView.setText(story.getSource().getName());
        descriptionTextView.setText(story.getDescription());
    }

    private void init() {
        titleSecondTextView = findViewById(R.id.titleTextView);
        sourceNameTextView = findViewById(R.id.sourceNameSecondTextView);
        descriptionTextView = findViewById(R.id.descriptionSecondTextView);
    }

    private void getIntentFromMainActivity() {
        Intent intent = getIntent();
        story = (Story) intent.getSerializableExtra("obj");
    }
}