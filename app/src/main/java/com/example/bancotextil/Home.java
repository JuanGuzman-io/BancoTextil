package com.example.bancotextil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class Home extends AppCompatActivity implements DataRVAdapter.DataClickInterface {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ProgressDialog progressDialog;
    private RecyclerView dataRV;
    private CardView dataView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<DataRVModal> dataRVModalsArrayList;
    private RelativeLayout dialogId;

    private DataRVAdapter dataRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dataRV = findViewById(R.id.dataRV);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");
        dataRVModalsArrayList = new ArrayList<>();
        dialogId = findViewById(R.id.dialogId);

        dataRVAdapter = new DataRVAdapter(dataRVModalsArrayList, this, this);
        dataRV.setLayoutManager(new LinearLayoutManager(this));
        dataRV.setAdapter(dataRVAdapter);


        if (user != null) {
            getAllPosts();

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
            bottomNavigationView.setSelectedItemId(R.id.home);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.add:
                            startActivity(new Intent(getApplicationContext(), NewPost.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.home:
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

    private void getAllPosts() {
        dataRVModalsArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                //progressDialog.dismiss();
                dataRVModalsArrayList.add(snapshot.getValue(DataRVModal.class));
                dataRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
                //progressDialog.dismiss();
                dataRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //progressDialog.dismiss();
                dataRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {
                //progressDialog.dismiss();
                dataRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //progressDialog.dismiss();
                dataRVAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDataClick(int position) {
        navigate(dataRVModalsArrayList.get(position));
    }

    public void navigate(DataRVModal dataRVModal) {
        final BottomSheetDialog bottomDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_dialog, dialogId);
        bottomDialog.setContentView(layout);
        bottomDialog.setCancelable(false);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();

        TextView bdTitulo = layout.findViewById(R.id.bdTitulo);
        TextView bdDesc = layout.findViewById(R.id.bdDesc);
        TextView bdTela = layout.findViewById(R.id.bdTipo);
        TextView bdCantidad = layout.findViewById(R.id.bdCantidad);
        TextView bdUser = layout.findViewById(R.id.bdUser);
        TextView bdNumero = layout.findViewById(R.id.bdNumero);
        TextView bdDireccion = layout.findViewById(R.id.bdDireccion);
        TextView bdEstado = layout.findViewById(R.id.bdEstado);

        Button btnEdit = layout.findViewById(R.id.btnEdit);

        bdTitulo.setText(dataRVModal.getTitulo());
        bdDesc.setText(dataRVModal.getDesc());
        bdTela.setText(dataRVModal.getTipo());
        bdCantidad.setText(dataRVModal.getCantidad()+ " kg");
        bdUser.setText(dataRVModal.getNombre());
        bdNumero.setText("+57 " + dataRVModal.getNumero());
        bdDireccion.setText(dataRVModal.getDireccion());
        String pubId = dataRVModal.getPubid();

        if (!dataRVModal.getEstado()) {
            bdEstado.setText("Cerrado");
        } else {
            bdEstado.setText("Disponible");
        }

        if (Objects.equals(user.getDisplayName(), dataRVModal.getNombre())) {
            btnEdit.setVisibility(View.VISIBLE);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Home.this, EditPost.class);
                    i.putExtra("post", dataRVModal);
                    startActivity(i);
                   /* databaseReference = firebaseDatabase.getReference("Posts").child(pubId);
                    progressDialog.dismiss();
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(Home.this);
                    alertbox.setMessage("Desea eliminar la publicación?");
                    alertbox.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            progressDialog.dismiss();
                            databaseReference.removeValue();
                            Toast.makeText(Home.this, "Se ha eliminado la publicación", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Home.this, Home.class));
                        }
                    });

                    alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            progressDialog.dismiss();
                            Toast.makeText(Home.this, "No se eliminó", Toast.LENGTH_SHORT).show();
                        }
                    });*/
                }
            });
        } else {
            btnEdit.setVisibility(View.INVISIBLE);
        }
    }

}