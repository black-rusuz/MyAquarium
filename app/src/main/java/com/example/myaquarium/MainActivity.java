package com.example.myaquarium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor myEditor = myPreferences.edit();
        boolean previouslyStarted = myPreferences.getBoolean("PREVIOUSLY_STARTED", false);


        if(!previouslyStarted) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_start);

            Button login = findViewById(R.id.login);
            Button register = findViewById(R.id.register);
            Button help = findViewById(R.id.help);

            login.setOnClickListener(v -> {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            });

            register.setOnClickListener(v -> {
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
            });

            help.setOnClickListener(v -> {
                myEditor.putBoolean("PREVIOUSLY_STARTED", true).commit();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            });
        } else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            BottomNavigationView navView = findViewById(R.id.nav_view);
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupWithNavController(navView, navController);
        }
    }

    public void onBackPressed() {
        if (findViewById(R.id.add_back) != null)
            getSupportFragmentManager().popBackStack();
        else
            finish();
    }

}