package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
//Juan Manuel Prado Restrepo
//Cc 1006325930
// Curso 7303a
// Ingenieria en sistemas
public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);

        Button verVideoButton = findViewById(R.id.Reproducir);
        Button Siguiente = findViewById(R.id.Siguiente);
        verVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirVideoEnYouTube();
            }
        });

        Siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {Siguiente();
            }
        });
    }

    private void abrirVideoEnYouTube() {
        // Reemplaza con el ID real del video de YouTube que quiere reproducir
        String videoId = "u0NC0vGz7PA";

        // Construye la URL del video de YouTube
        String videoUrl = "https://www.youtube.com/watch?v=" + videoId;

        // Crea un Intent para abrir la aplicación de YouTube o un navegador web externo
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        startActivity(intent);


    }
    private void Siguiente() {
        Intent intent2 = new Intent(this, TablaActivity.class);
        startActivity(intent2);
        finish();
    }
}


