package com.example.bancotextil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UpdateAccount extends AppCompatActivity {
    private TextInputLayout etNombreBase;
    private TextInputEditText etNombre;
    private MaterialButton btnFinalizar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        etNombre = findViewById(R.id.etNombre);
        etNombreBase = findViewById(R.id.etNombreBase);
        btnFinalizar = findViewById(R.id.btnFinalizar);

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update();
            }
        });
    }

    private void Update() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final String name = etNombre.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        if (TextUtils.isEmpty(name) || etNombre.length() < 7) {
            progressDialog.dismiss();
            etNombreBase.setHelperText("Por favor ingresa tu nombre completo");
            //Toast.makeText(this, "Por favor ingresa tu nombre completo", Toast.LENGTH_SHORT).show();
        } else {
            etNombreBase.setHelperText("");
            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateAccount.this, "Se ha creado tu cuenta! \uD83C\uDF89", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(UpdateAccount.this, LogIn.class);
                        startActivity(login);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(UpdateAccount.this, "Ocurrio un error  \uD83D\uDE25", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}