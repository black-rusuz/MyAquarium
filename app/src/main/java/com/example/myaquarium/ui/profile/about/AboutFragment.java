package com.example.myaquarium.ui.profile.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myaquarium.R;

public class AboutFragment extends Fragment {

    private AboutViewModel aboutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutViewModel =
                new ViewModelProvider(this).get(AboutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        root.findViewById(R.id.about_back).setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        ImageView about_gsusha_img = root.findViewById(R.id.about_gsusha_img);
        ImageView about_rusuz_img = root.findViewById(R.id.about_rusuz_img);

        TextView about_gsusha = root.findViewById(R.id.about_gsusha);
        TextView about_rusuz = root.findViewById(R.id.about_rusuz);

        Uri gsusha_uri = Uri.parse("https://github.com/gsusha");
        Uri rusuz_uri = Uri.parse("https://github.com/black-rusuz");

        Intent gsusha_intent = new Intent(Intent.ACTION_VIEW, gsusha_uri);
        Intent rusuz_intent = new Intent(Intent.ACTION_VIEW, rusuz_uri);

        about_gsusha.setOnClickListener(v -> startActivity(gsusha_intent));
        about_gsusha_img.setOnClickListener(v -> startActivity(gsusha_intent));
        about_rusuz.setOnClickListener(v -> startActivity(rusuz_intent));
        about_rusuz_img.setOnClickListener(v -> startActivity(rusuz_intent));

        return root;
    }
}