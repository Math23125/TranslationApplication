package com.example.navbarre.fragment.Histopower;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE);
        boolean isDarkModeEnabled = sharedPreferences.getBoolean("isDarkModeEnabled", false);

        if (isDarkModeEnabled) {
            setTheme(R.style.Theme_NavBarre_Dark);
        } else {
            setTheme(R.style.Theme_NavBarre_Light);
        }

        setContentView(R.layout.activity_full_text);

        // Initialisation des TextViews avec les bons identifiants
        textViewOriginalText = findViewById(R.id.full_original_text);
        textViewTranslatedText = findViewById(R.id.full_translated_text);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);

        ImageView deleteIcon = findViewById(R.id.delete_icon);
        ImageView closeButton = findViewById(R.id.close_button);

        // Getting the translation text, date and time from intent
        String originalText = getIntent().getStringExtra("original_text");
        String translationText = getIntent().getStringExtra("translated_text");
        String date = getIntent().getStringExtra("translation_date");
        String time = getIntent().getStringExtra("translation_time");

        translationIndex = getIntent().getIntExtra("translation_index", -1);

        // Setting the translation text, date and time to TextViews
        textViewOriginalText.setText(originalText);
        textViewTranslatedText.setText(translationText);
        textViewDate.setText(date);
        textViewTime.setText(time);

        closeButton.setOnClickListener(v -> finish());

        // Change status bar color to white
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.WHITE);
        }

        deleteIcon.setOnClickListener(v -> {
            if (translationIndex != -1) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("translation_index", translationIndex);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
