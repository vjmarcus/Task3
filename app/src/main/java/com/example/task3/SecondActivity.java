package com.example.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.task3.model.Story;

public class SecondActivity extends AppCompatActivity {

    private TextView titleSecondTextView;
    private TextView sourceNameTextView;
    private TextView descriptionTextView;
    private Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        init();
        getIntentFromMainActivity();
        setTextToTextView();

    }

    private void setTextToTextView() {
        titleSecondTextView.setText(story.getTitle());
        sourceNameTextView.setText(story.getSource().getName());
        descriptionTextView.setText(story.getDescription());
    }

    private void init() {
        titleSecondTextView = findViewById(R.id.titleSecondTextView);
        sourceNameTextView = findViewById(R.id.sourceNameSecondTextView);
        descriptionTextView = findViewById(R.id.descriptionSecondTextView);
    }

    private void getIntentFromMainActivity() {
        Intent intent = getIntent();
        story = (Story) intent.getSerializableExtra("obj");
    }
}