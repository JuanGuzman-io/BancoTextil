package com.example.bancotextil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Menu extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private MaterialButton btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnLogout = findViewById(R.id.btnSignOut);

        if (user != null) {
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
            bottomNavigationView.setSelectedItemId(R.id.menu);
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
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.search:
                            startActivity(new Intent(getApplicationContext(), Search.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.menu:
                            return true;
                    }
                    return false;
                }
            });

            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogOut();
                }
            });
        } else {
            Toast.makeText(this, "Por favor vuelve a iniciar sesión", Toast.LENGTH_SHORT).show();
            Intent breakSession = new Intent(this, LogIn.class);
            startActivity(breakSession);
        }
    }

    private void LogOut() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Hasta pronto \uD83D\uDC4B", Toast.LENGTH_SHORT).show();
            Intent logout = new Intent(this, LogIn.class);
            startActivity(logout);
        } else {
            Intent logout = new Intent(this, LogIn.class);
            startActivity(logout);
        }
    }
}