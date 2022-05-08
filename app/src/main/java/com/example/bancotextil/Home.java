package com.example.bancotextil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements  DataRVAdapter.DataClickInterface {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ProgressDialog progressDialog;
    private RecyclerView dataRV;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<DataRVModal> dataRVModalsArrayList;

    private DataRVAdapter dataRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dataRV = findViewById(R.id.dataRV);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");
        dataRVModalsArrayList = new ArrayList<>();

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
        startActivity(new Intent(this, PostInfo.class));
    }
}