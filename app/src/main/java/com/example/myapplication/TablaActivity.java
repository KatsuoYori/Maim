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
public class TablaActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablan);

        Button verVideoButton = findViewById(R.id.Tabla);
        Button Anterior = findViewById(R.id.Anterior);
        verVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirVideoEnYouTube();
            }
        });

        Anterior.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                Anterior();
            }
        });
    }



    private void abrirVideoEnYouTube() {

        // Construye la URL de la pagina
        String videoUrl = "https://zonafit.co/shop/proton-gainer-%C2%B7-smart-muscle/";

        // Crea un Intent para abrir la aplicación de YouTube o un navegador web externo
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        startActivity(intent);

    }
    private void Anterior(){
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
        finish();
    }
}


