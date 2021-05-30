package com.example.myaquarium.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myaquarium.R;
import com.example.myaquarium.ui.home.add.AddFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(root.getContext());

        View home_fragment = root.findViewById(R.id.home_fragment);
        Button home_add = root.findViewById(R.id.home_add);


        if (    ((myPreferences.getInt("VOL", 0)) == 0) &&
                ((myPreferences.getInt("TEMP", 0)) == 0)) {
            home_fragment.setVisibility(View.INVISIBLE);
            home_add.setText("Добавить");
            home_add.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_add_black_24dp),null,null,null);
        }

//        home_fragment.setOnClickListener(v -> {
//            getFragmentManager().beginTransaction()
//                    .replace(R.id.nav_host_fragment, new AddFragment())
//                    .addToBackStack(null)
//                    .commit();
//        });

        home_add.setOnClickListener(v -> {
            getFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new AddFragment())
                    .addToBackStack(null)
                    .commit();
        });
        home_add.setOnLongClickListener(v -> {
            myPreferences.edit().clear().commit();
            System.exit(0);
            return true;
        });

        return root;
    }
}