package com.example.bancotextil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    private TextInputLayout etEmailBase, etPassBase, etConfirmBase;
    private TextInputEditText etEmail, etPass, etConfirm;
    private MaterialButton btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail2);
        etEmailBase = findViewById(R.id.etEmail2Base);
        etPassBase = findViewById(R.id.etPass2Base);
        etConfirmBase = findViewById(R.id.etConfirmPassBase);
        etPass = findViewById(R.id.etPass2);
        etConfirm = findViewById(R.id.etConfirmPass);
        btnRegister = findViewById(R.id.btnReset);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
    }

    private void Register() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final String email = etEmail.getText().toString().trim();
        final String password = etPass.getText().toString().trim();
        final String cpassword = etConfirm.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(cpassword)) {
            progressDialog.dismiss();
            etEmailBase.setHelperText("Obligatorio");
            etPassBase.setHelperText("Obligatorio");
            etConfirmBase.setHelperText("Obligatorio");
            //Toast.makeText(SignUp.this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            progressDialog.dismiss();
            etEmailBase.setHelperText("Por favor ingresa un correo valido");
            etPassBase.setHelperText("");
            etConfirmBase.setHelperText("");
            //Toast.makeText(this, "Por favor ingresa un correo valido", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 7) {
            progressDialog.dismiss();
            etEmailBase.setHelperText("");
            etPassBase.setHelperText("Debe contener almenos 7 caracteres");
            etConfirmBase.setHelperText("");
        } else if (!password.equals(cpassword)) {
            progressDialog.dismiss();
            etEmailBase.setHelperText("");
            etPassBase.setHelperText("");
            etConfirmBase.setHelperText("Las contraseñas no coinciden");
            //Toast.makeText(SignUp.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        } else {
            etEmailBase.setHelperText("");
            etPassBase.setHelperText("");
            etConfirmBase.setHelperText("");
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Intent update = new Intent(SignUp.this, UpdateAccount.class);
                        startActivity(update);
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            progressDialog.dismiss();
                            etEmailBase.setHelperText("Este correo ya se encuentra registrado");
                            //Toast.makeText(SignUp.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this, "No se pudo registrar el usuario  \uD83D\uDE25", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    public void Back(View v) {
        Intent create = new Intent(SignUp.this, LogIn.class);
        startActivity(create);
    }
}