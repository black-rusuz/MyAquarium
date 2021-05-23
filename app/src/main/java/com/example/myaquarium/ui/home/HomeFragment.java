package com.example.myaquarium.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

        TextView aqua_name = root.findViewById(R.id.aqua_header);
        TextView aqua_type = root.findViewById(R.id.aqua_type);
        TextView aqua_vol = root.findViewById(R.id.aqua_vol);
        TextView aqua_temp = root.findViewById(R.id.aqua_temp);
        View home_fragment = root.findViewById(R.id.home_fragment);

        Button home_add = root.findViewById(R.id.home_add);

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(root.getContext());

        if (    !((myPreferences.getInt("VOL", 0)) == 0) &&
                !((myPreferences.getInt("TEMP", 0)) == 0)) {
            aqua_name.setText(myPreferences.getString("NAME", "Аквариум"));
            aqua_type.setText(myPreferences.getString("TYPE", "Смешанный"));
            aqua_vol.setText(String.valueOf(myPreferences.getInt("VOL", 0)));
            aqua_temp.setText(String.valueOf(myPreferences.getInt("TEMP", 0)));
        } else {
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