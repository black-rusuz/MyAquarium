package com.example.myaquarium.ui.home.add.fish;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myaquarium.R;
import com.example.myaquarium.ui.home.HomeFragment;
import com.example.myaquarium.ui.home.add.AddFragment;

public class FishFragment extends Fragment {

    private FishViewModel fishViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fishViewModel =
                new ViewModelProvider(this).get(FishViewModel.class);
        View root = inflater.inflate(R.layout.fragment_fish, container, false);

        Button fish_back = root.findViewById(R.id.fish_back);
        fish_back.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });

        return root;
    }
}
