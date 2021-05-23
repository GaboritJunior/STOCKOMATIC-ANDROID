package com.example.stockomatic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //affichage de l'activité
        setContentView(R.layout.splashscreenactivity);
        //redirection apres 1 secondes
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // recuperation de la page a afficher
                Intent intent = new Intent(getApplicationContext(), VueProduit.class);
                // affichage de la page
                startActivity(intent);
                // fermeture de la page en cours
                finish();
            }
        };
        // handler post delayed
        new Handler().postDelayed(runnable,1000);
    }
}