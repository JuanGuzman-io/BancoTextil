package com.example.bancotextil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private TextView tvNombre1, tvNombre2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        tvNombre1 = findViewById(R.id.tvNombre1);
        tvNombre2 = findViewById(R.id.tvNombre2);

        if (user != null) {
            tvNombre1.setText(user.getDisplayName());
            tvNombre2.setText(user.getDisplayName());

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
            bottomNavigationView.setSelectedItemId(R.id.profile);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
}