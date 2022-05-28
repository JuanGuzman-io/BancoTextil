package com.example.bancotextil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NewPost extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference PostData;
    ProgressDialog progressDialog;
    private AutoCompleteTextView select;
    private TextInputLayout etTituloBase, etNumeroBase, etCantidadBase, etDireccionBase, etDescBase, selectBase;
    private TextInputEditText etTitulo, etNumero, etCantidad, etDireccion, etDesc;

    ArrayAdapter<String> typeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        PostData = FirebaseDatabase.getInstance().getReference();

        selectBase = findViewById(R.id.selectBase);
        select = findViewById(R.id.select);

        etTituloBase = findViewById(R.id.etTituloBase);
        etTitulo = findViewById(R.id.etTitulo);
        etNumeroBase = findViewById(R.id.etNumeroBase);
        etNumero = findViewById(R.id.etNumero);
        etCantidadBase = findViewById(R.id.etCantidadBase);
        etCantidad = findViewById(R.id.etCantidad);
        etDireccionBase = findViewById(R.id.etDireccionBase);
        etDireccion = findViewById(R.id.etDireccion);
        etDescBase = findViewById(R.id.etDescBase);
        etDesc = findViewById(R.id.etDesc);

        MaterialButton btnPost = findViewById(R.id.btnPost);

        String[] types = getResources().getStringArray(R.array.type_array);

        if (user != null) {
            typeAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, types);
            select.setAdapter(typeAdapter);
            select.setThreshold(1);

            btnPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Post();
                }
            });

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
            bottomNavigationView.setSelectedItemId(R.id.add);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.home:
                            startActivity(new Intent(getApplicationContext(), Home.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.add:
                            return true;

                        case R.id.profile:
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.search:
                            startActivity(new Intent(getApplicationContext(), Search.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.menu:
                            startActivity(new Intent(getApplicationContext(), Menu.class));
                            overridePendingTransition(0, 0);
                            return true;
                    }
                    return false;
                }
            });
        } else {
            Toast.makeText(this, "Por favor vuelve a iniciar sesión", Toast.LENGTH_SHORT).show();
            Intent breakSession = new Intent(this, LogIn.class);
            startActivity(breakSession);
        }
    }

    private void Post() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        String titulo = etTitulo.getText().toString();
        String tipo = select.getText().toString();
        String numero = etNumero.getText().toString();
        String cantidad = etCantidad.getText().toString();
        String direccion = etDireccion.getText().toString();
        String desc = etDesc.getText().toString();
        String userId = user.getUid();
        String nombre = user.getDisplayName();
        LocalDateTime fecha = LocalDateTime.now();
        Boolean estado = true;

        if (TextUtils.isEmpty(titulo) || TextUtils.isEmpty(tipo) || TextUtils.isEmpty(numero) || TextUtils.isEmpty(cantidad) || TextUtils.isEmpty(direccion) || TextUtils.isEmpty(desc)) {
            progressDialog.dismiss();
            etTituloBase.setHelperText("Obligatorio");
            selectBase.setHelperText("Obligatorio");
            etNumeroBase.setHelperText("Obligatorio");
            etCantidadBase.setHelperText("Obligatorio");
            etDireccionBase.setHelperText("Obligatorio");
            etDescBase.setHelperText("Obligatorio");
        } else if (etDesc.length() > 100) {
            progressDialog.dismiss();
            etDescBase.setHelperText("Máximo 100 caracteres");
        } else {
            progressDialog.dismiss();
            String id = PostData.push().getKey();
            PostData donacion = new PostData(id, userId, titulo, tipo, numero, cantidad, direccion, desc, nombre, fecha, estado);
            PostData.child("Posts").child(id).setValue(donacion);
            Toast.makeText(this, "Se ha publicado tu donación \uD83D\uDC4F", Toast.LENGTH_SHORT).show();
            etTitulo.setText("");
            select.setText("");
            etNumero.setText("");
            etCantidad.setText("");
            etDireccion.setText("");
            etDesc.setText("");

            etTituloBase.setHelperText("");
            selectBase.setHelperText("");
            etNumeroBase.setHelperText("");
            etCantidadBase.setHelperText("");
            etDireccionBase.setHelperText("");
            etDescBase.setHelperText("");
        }
    }
}