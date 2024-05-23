package com.example.navbarre.custombottomnav;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.example.navbarre.R;

public class PageChargement extends AppCompatActivity {

    private static final int LOADING_DURATION = 2500; // Durée de chargement en millisecondes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_chargement);

        // Utiliser un Handler pour retarder le démarrage de l'activité principale
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Aller à l'activité principale
                startActivity(new Intent(PageChargement.this, MainActivity.class));
                finish(); // Fermer cette activité
            }
        }, LOADING_DURATION);
    }
}
