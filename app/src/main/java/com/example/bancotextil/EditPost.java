package com.example.bancotextil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditPost extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference;
    private DataRVModal dataRVModal;
    ProgressDialog progressDialog;
    private AutoCompleteTextView select;
    private TextInputLayout etTituloBase, etNumeroBase, etCantidadBase, etDireccionBase, etDescBase, selectBase;
    private TextInputEditText etTitulo, etNumero, etCantidad, etDireccion, etDesc;
    private MaterialButton btnUpdatePost, btnDeletePost;
    private String pubId;

    ArrayAdapter<String> typeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        selectBase = findViewById(R.id.selectBase2);
        select = findViewById(R.id.select2);
        etTituloBase = findViewById(R.id.etTituloBase2);
        etTitulo = findViewById(R.id.etTitulo2);
        etNumeroBase = findViewById(R.id.etNumeroBase2);
        etNumero = findViewById(R.id.etNumero2);
        etCantidadBase = findViewById(R.id.etCantidadBase2);
        etCantidad = findViewById(R.id.etCantidad2);
        etDireccionBase = findViewById(R.id.etDireccionBase2);
        etDireccion = findViewById(R.id.etDireccion2);
        etDescBase = findViewById(R.id.etDescBase2);
        etDesc = findViewById(R.id.etDesc2);

        btnUpdatePost = findViewById(R.id.btnUpdatePost);
        btnDeletePost = findViewById(R.id.btnDeletePost);

        dataRVModal = getIntent().getParcelableExtra("data");

        if (dataRVModal != null) {
            select.setText(dataRVModal.getTipo());
            etTitulo.setText(dataRVModal.getTitulo());
            etNumero.setText(dataRVModal.getNumero());
            etCantidad.setText(dataRVModal.getCantidad());
            etDireccion.setText(dataRVModal.getDireccion());
            etDesc.setText(dataRVModal.getDesc());
            pubId = dataRVModal.getPubid();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Post").child(pubId);

        btnUpdatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdatePost();
            }
        });

        btnDeletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeletePost("Desea eliminar la publicación?");
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
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
                        startActivity(new Intent(getApplicationContext(), NewPost.class));
                        overridePendingTransition(0, 0);
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
    }

    private void UpdatePost() {
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

        Map<String, Object> map = new HashMap<>();
        map.put("titulo", titulo);
        map.put("tipo", tipo);
        map.put("numero", numero);
        map.put("cantidad", cantidad);
        map.put("direccion", direccion);
        map.put("desc", desc);

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

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressDialog.dismiss();
                    databaseReference.updateChildren(map);
                    Toast.makeText(EditPost.this, "La publicación se ha actualizado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditPost.this, Home.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();
                    Toast.makeText(EditPost.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                }
            });

            etTituloBase.setHelperText("");
            selectBase.setHelperText("");
            etNumeroBase.setHelperText("");
            etCantidadBase.setHelperText("");
            etDireccionBase.setHelperText("");
            etDescBase.setHelperText("");
        }
    }

    private void DeletePost(String cadena) {
        progressDialog.dismiss();
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setMessage(cadena);
        alertbox.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressDialog.dismiss();
                databaseReference.removeValue();
                Toast.makeText(EditPost.this, "Se ha eliminado la publicación", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditPost.this, Home.class));
            }
        });

        alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressDialog.dismiss();
                Toast.makeText(EditPost.this, "No se eliminó", Toast.LENGTH_SHORT).show();
            }
        });
    }
}