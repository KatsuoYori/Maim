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

public class RegistroActivity extends AppCompatActivity{

    private EditText emailEditText;
    private EditText contraseñaEditText;
    private EditText usuarioEditText;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registroactivity);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        emailEditText = findViewById(R.id.Email);
        usuarioEditText = findViewById(R.id.NuevoUsuario);
        contraseñaEditText = findViewById(R.id.NuevaContraseña);

        Button registrarButton = findViewById(R.id.Confirmar);
        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        final String email = emailEditText.getText().toString();
        final String contraseña = contraseñaEditText.getText().toString();
        final String usuario = usuarioEditText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, contraseña)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registro exitoso en Firebase Authentication
                            Log.d("RegistroActivity", "createUserWithEmail:success");

                            // Obtener el UID del usuario registrado
                            String uid = mAuth.getCurrentUser().getUid();

                            // Guardar los datos en Firestore
                            guardarDatosEnFirestore(uid, usuario, contraseña);

                            // Mostrar mensaje de éxito y redirigir al usuario
                            Toast.makeText(RegistroActivity.this, "Usuario registrado correctamente.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Si el registro falla, mostrar un mensaje al usuario
                            Log.w("RegistroActivity", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistroActivity.this, "Error al registrar usuario. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public class Usuario {
        private String nombreUsuario;
        private String contraseña;

        public Usuario(String nombreUsuario, String contraseña) {
            this.nombreUsuario = nombreUsuario;
            this.contraseña = contraseña;
        }

        // Asegúrate de tener los getters y setters necesarios para los atributos

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public void setNombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
        }

        public String getContraseña() {
            return contraseña;
        }

        public void setContraseña(String contraseña) {
            this.contraseña = contraseña;
        }
    }

    private void guardarDatosEnFirestore(String uid, String usuario, String contraseña) {
        // Crear un nuevo documento en Firestore para el usuario
        db.collection("users").document(uid)
                .set(new Usuario(usuario, contraseña))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("RegistroActivity", "DocumentSnapshot successfully written!");
                        } else {
                            Log.w("RegistroActivity", "Error writing document", task.getException());
                        }
                    }
                });
    }
}
