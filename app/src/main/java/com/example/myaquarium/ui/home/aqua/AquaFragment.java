package com.example.myaquarium.ui.home.aqua;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myaquarium.R;

public class AquaFragment extends Fragment {

    private AquaViewModel aquaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aquaViewModel =
                new ViewModelProvider(this).get(AquaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_aqua, container, false);

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(root.getContext());

        TextView aqua_name = root.findViewById(R.id.aqua_header);
        TextView aqua_type = root.findViewById(R.id.aqua_type);
        TextView aqua_vol = root.findViewById(R.id.aqua_vol);
        TextView aqua_temp = root.findViewById(R.id.aqua_temp);


        if (    !((myPreferences.getInt("VOL", 0)) == 0) &&
                !((myPreferences.getInt("TEMP", 0)) == 0)) {
            aqua_name.setText(myPreferences.getString("NAME", "Аквариум"));
            aqua_type.setText(myPreferences.getString("TYPE", "Смешанный"));
            aqua_vol.setText(String.valueOf(myPreferences.getInt("VOL", 0)));
            aqua_temp.setText(String.valueOf(myPreferences.getInt("TEMP", 0)));

            TextView aqua_vol_unit = root.findViewById(R.id.aqua_vol_unit);
            aqua_vol_unit.setText(getResources().getQuantityText(R.plurals.vol_unit,
                    myPreferences.getInt("VOL", 0)));
        }

        return root;
    }
}
