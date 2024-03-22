package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    private EditText usuarioEditText, contrasenaEditText;
    private Button iniciarSesionButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        usuarioEditText = findViewById(R.id.editTextText_Usuario);
        contrasenaEditText = findViewById(R.id.editTextText_Contraseña);
        iniciarSesionButton = findViewById(R.id.Confirm);

        iniciarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });
    }

    private void iniciarSesion() {
        final String usuario = usuarioEditText.getText().toString();
        final String contrasena = contrasenaEditText.getText().toString();

        // Consultar Firestore para verificar las credenciales
        db.collection("users")
                .whereEqualTo("nombreUsuario", usuario)
                .whereEqualTo("contraseña", contrasena)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                // Las credenciales son válidas
                                Log.d("MainActivity", "Inicio de sesión exitoso");
                                Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                                // Redirigir a la siguiente actividad
                                Intent intent = new Intent(MainActivity.this, VideoActivity.class); // Reemplaza TuActividadDestino con el nombre de tu actividad destino
                                startActivity(intent);
                                finish();
                            } else {
                                // Las credenciales no son válidas
                                Log.w("MainActivity", "Inicio de sesión fallido. Credenciales incorrectas.");
                                Toast.makeText(MainActivity.this, "Inicio de sesión fallido. Credenciales incorrectas.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Error al consultar Firestore
                            Log.e("MainActivity", "Error al consultar Firestore para verificar las credenciales", task.getException());
                            Toast.makeText(MainActivity.this, "Error al verificar las credenciales.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
