package ru.sfedu.myaquarium;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.register_back).setOnClickListener(v -> this.finish());
    }
}
