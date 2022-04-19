package com.example.bancotextil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ResetEmail extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private TextInputLayout etEmailBase;
    private TextInputEditText etEmail;
    private MaterialButton btnReset;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_email);

        etEmailBase = findViewById(R.id.etEmail3Base);
        etEmail = findViewById(R.id.etEmail3);
        btnReset = findViewById(R.id.btnReset);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reset();
            }
        });
    }

    public void Reset() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final String email = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            progressDialog.dismiss();
            etEmailBase.setHelperText("Por favor ingresa tu correo");
            //Toast.makeText(this, "Por favor ingresa tu correo", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            progressDialog.dismiss();
            etEmailBase.setHelperText("Por favor ingresa un correo valido");
            //Toast.makeText(this, "Por favor ingresa un correo valido", Toast.LENGTH_SHORT).show();
        } else {
            etEmailBase.setHelperText("");
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ResetEmail.this);
                        dialog.setMessage("Por favor revisa tu correo, se ha enviado el enlace para restablecer tu contraseña.").setCancelable(true).setTitle("¡Se ha enviado!").create().show();

                        etEmail.setText("");
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(ResetEmail.this, "El correo no esta registrado \uD83D\uDE25", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(ResetEmail.this, "Ocurrio un error \uD83D\uDE25", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void Back(View v) {
        Intent create = new Intent(ResetEmail.this, LogIn.class);
        startActivity(create);
    }
}