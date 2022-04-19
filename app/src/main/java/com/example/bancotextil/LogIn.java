package com.example.bancotextil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LogIn extends AppCompatActivity {
    private TextInputLayout etEmailBase, etPassBase;
    private TextInputEditText etEmail, etPass;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        etEmailBase = findViewById(R.id.etEmailBase);
        etEmail = findViewById(R.id.etEmail);
        etPassBase = findViewById(R.id.etPassBase);
        etPass = findViewById(R.id.etPass);
    }

    public void LogIn(View v) {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final String email = etEmail.getText().toString().trim();
        final String password = etPass.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            progressDialog.dismiss();
            etEmailBase.setHelperText("Obligatorio");
            etPassBase.setHelperText("Obligatorio");
            //Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            progressDialog.dismiss();
            etEmailBase.setHelperText("Por favor ingresa un correo valido");
            etPassBase.setHelperText("");
            //Toast.makeText(this, "Por favor ingresa un correo valido", Toast.LENGTH_SHORT).show();
        } else {
            etEmailBase.setHelperText("");
            etPassBase.setHelperText("");
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(LogIn.this, "Bienvenido de vuelta  \uD83E\uDD1D", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(LogIn.this, Home.class);
                        startActivity(login);
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            progressDialog.dismiss();
                            Toast.makeText(LogIn.this, "El usuario no existe  \uD83D\uDE25", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LogIn.this, "No se pudo iniciar sesión  \uD83D\uDE25", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    public void Create(View v) {
        Intent create = new Intent(LogIn.this, SignUp.class);
        startActivity(create);
    }

    public void Reset(View v) {
        Intent create = new Intent(LogIn.this, ResetEmail.class);
        startActivity(create);
    }
}