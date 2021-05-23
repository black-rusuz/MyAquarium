package com.example.myaquarium.ui.home.add.plant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myaquarium.R;

public class PlantFragment extends Fragment {

    private PlantViewModel plantViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        plantViewModel =
                new ViewModelProvider(this).get(PlantViewModel.class);
        View root = inflater.inflate(R.layout.fragment_plant, container, false);

        Button plant_back = root.findViewById(R.id.plant_back);
        plant_back.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });

        return root;
    }
}
