package ru.sfedu.myaquarium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor myEditor = myPreferences.edit();

        boolean previouslyStarted = myPreferences.getBoolean("PREVIOUSLY_STARTED", false);

        if(previouslyStarted) {
            setContentView(R.layout.activity_main);
            BottomNavigationView navView = findViewById(R.id.nav_view);
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupWithNavController(navView, navController);
        } else {
            setContentView(R.layout.activity_start);

            Button start_login = findViewById(R.id.start_login);
            Button start_register = findViewById(R.id.start_register);
            Button start_help = findViewById(R.id.start_help);

            start_login.setOnClickListener(v -> {
                Toast.makeText(
                        this,
                        "Авторизация пока недоступна",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            });

            start_register.setOnClickListener(v -> {
                Toast.makeText(
                        this,
                        "Регистрация пока недоступна",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
            });

            start_help.setOnClickListener(v -> {
                myEditor.putBoolean("PREVIOUSLY_STARTED", true).apply();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        }

//        Calendar calendar_feed = Calendar.getInstance();
//        calendar_feed.set(Calendar.DAY_OF_WEEK, myPreferences.getInt("DAY", 0));
//        calendar_feed.set(Calendar.HOUR_OF_DAY, myPreferences.getInt("HOUR", 0));
//        calendar_feed.set(Calendar.MINUTE, myPreferences.getInt("MINUTE", 0));
//        calendar_feed.set(Calendar.SECOND, 0);
    }

    @Override
    public void onBackPressed() {
        // TODO: Переделать условие под наличие элементов в бэкстеке, а то хуйня какая-то
        if (    findViewById(R.id.add_back) != null         ||
                findViewById(R.id.edit_back) != null        ||
                findViewById(R.id.settings_back) != null    ||
                findViewById(R.id.about_back) != null       ||
                findViewById(R.id.aquarium_back) != null    ||
                findViewById(R.id.fish_back) != null        ||
                findViewById(R.id.plant_back) != null       ||
                findViewById(R.id.menu_back) != null        ||
                findViewById(R.id.page_back) != null        ) {
            getSupportFragmentManager().popBackStack();
        }
        else
            finish();
    }

}