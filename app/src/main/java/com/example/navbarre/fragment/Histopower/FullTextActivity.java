package com.example.navbarre.fragment.Histopower;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.navbarre.R;

public class FullTextActivity extends AppCompatActivity {

    private TextView textViewOriginalText;
    private TextView textViewTranslatedText;
    private TextView textViewDate;
    private TextView textViewTime;
    private int translationIndex;

    //@SuppressLint("MissingInflatedId")

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_text);

        textViewOriginalText = findViewById(R.id.full_original_text);
        textViewTranslatedText = findViewById(R.id.full_translated_text);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);

        ImageView deleteIcon = findViewById(R.id.delete_icon);

        ImageView closeButton = findViewById(R.id.close_button);

        String originalText = getIntent().getStringExtra("original_text");
        String translationText = getIntent().getStringExtra("translated_text");
        String date = getIntent().getStringExtra("translation_date");
        String time = getIntent().getStringExtra("translation_time");

        translationIndex = getIntent().getIntExtra("translation_index", -1);

        textViewOriginalText.setText(originalText);
        textViewTranslatedText.setText(translationText);
        textViewDate.setText(date);
        textViewTime.setText(time);

        closeButton.setOnClickListener(v -> finish());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.WHITE);
        }


    }


}
