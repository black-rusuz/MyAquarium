package com.example.myaquarium.ui.help;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myaquarium.MainActivity;
import com.example.myaquarium.R;

import java.net.URI;
import java.net.URL;

public class HelpFragment extends Fragment {

    private HelpViewModel helpViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        helpViewModel =
                new ViewModelProvider(this).get(HelpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_help, container, false);

        Button help_sv_1 = root.findViewById(R.id.help_sv_1);
        Button help_sv_2 = root.findViewById(R.id.help_sv_2);
        Button help_sv_3 = root.findViewById(R.id.help_sv_3);
        Button help_sv_4 = root.findViewById(R.id.help_sv_4);
        Button help_sv_5 = root.findViewById(R.id.help_sv_5);

        help_sv_1.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.aqvium.ru/ryby");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        help_sv_2.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.aqvium.ru/vidy-rastenij");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        help_sv_3.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.aqvium.ru/aquarium/voda/gidrokhimichesky-sostav-vody");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        help_sv_4.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.aqvium.ru/aquarium");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        help_sv_5.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.aqvium.ru/aquarium/zapusk-akvariuma");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        return root;
    }
}