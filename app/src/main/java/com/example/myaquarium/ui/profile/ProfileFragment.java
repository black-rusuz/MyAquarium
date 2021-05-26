package com.example.myaquarium.ui.profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myaquarium.R;
import com.example.myaquarium.ui.home.HomeFragment;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(root.getContext());

        ConstraintLayout profile_aqua = root.findViewById(R.id.profile_aqua);
        ConstraintLayout profile_fish = root.findViewById(R.id.profile_fish);
        ConstraintLayout profile_plant = root.findViewById(R.id.profile_plant);

        TextView profile_aqua_num = root.findViewById(R.id.profile_aqua_num);
        TextView profile_aqua_text = root.findViewById(R.id.profile_aqua_text);

        if (    !((myPreferences.getInt("VOL", 0)) == 0) &&
                !((myPreferences.getInt("TEMP", 0)) == 0)) {
            profile_aqua_num.setText("1");
            profile_aqua_text.setText(this.getResources().getQuantityText(R.plurals.aqua, 1));
        }

        profile_aqua.setOnClickListener(v -> {
            getFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment())
                    .commit();
        });
        profile_fish.setOnClickListener(v -> {
            Toast.makeText(getContext(),"Это покажет рыбок и их количество (наверно)", Toast.LENGTH_SHORT).show();
        });
        profile_plant.setOnClickListener(v -> {
            Toast.makeText(getContext(),"Это покажет растения и их количество (наверно)", Toast.LENGTH_SHORT).show();
        });

        return root;
    }
}