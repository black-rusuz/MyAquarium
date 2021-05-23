package com.example.myaquarium.ui.home.add;

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
import com.example.myaquarium.ui.home.add.fish.FishFragment;
import com.example.myaquarium.ui.home.add.plant.PlantFragment;

public class AddFragment extends Fragment {

    private AddViewModel addViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add, container, false);

        Button add_back = root.findViewById(R.id.add_back);
        add_back.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(root.getContext());
        SharedPreferences.Editor myEditor = myPreferences.edit();

        EditText add_name = root.findViewById(R.id.add_sv_name_et);
        Spinner add_type = root.findViewById(R.id.add_sv_type_sp);
        EditText add_vol = root.findViewById(R.id.add_sv_vol_et);
        EditText add_temp = root.findViewById(R.id.add_sv_temp_et);
        TextView add_header = root.findViewById(R.id.add_header);

        if (    !((myPreferences.getInt("VOL", 0)) == 0) &&
                !((myPreferences.getInt("TEMP", 0)) == 0)) {
            add_header.setText("Изменить");
            add_name.setText(myPreferences.getString("NAME", "Аквариум"));
            if (myPreferences.getString("TYPE", "Смешанный").equals("Смешанный")) {
                add_type.setSelection(0);
            }
            if (myPreferences.getString("TYPE", "Смешанный").equals("Рыбки")) {
                add_type.setSelection(1);
            }
            if (myPreferences.getString("TYPE", "Смешанный").equals("Растения")) {
                add_type.setSelection(2);
            }
            add_vol.setText(String.valueOf(myPreferences.getInt("VOL", 0)));
            add_temp.setText(String.valueOf(myPreferences.getInt("TEMP", 0)));
        }

        Button add_fish = root.findViewById(R.id.add_fish);
        add_fish.setOnClickListener(v -> {
            getFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new FishFragment())
                    .addToBackStack(null)
                    .commit();
        });

        Button add_plant = root.findViewById(R.id.add_plant);
        add_plant.setOnClickListener(v -> {
            getFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new PlantFragment())
                    .addToBackStack(null)
                    .commit();
        });

        Button save = root.findViewById(R.id.add_save);
        save.setOnClickListener(v -> {

            String name;
            String type;
            Integer vol;
            Integer temp;

            if (add_name.getText().length() > 0) {
                name = String.valueOf(add_name.getText());
            } else {
                name = "Аквариум";
                // + Integer.toString(1);
            }

            type = String.valueOf(add_type.getSelectedItem());

            if (add_vol.getText().length() > 0) {
                vol = Integer.parseInt(String.valueOf(add_vol.getText()));
            } else {
                vol = 0;
                Toast.makeText(getContext(),"Введите объём", Toast.LENGTH_SHORT).show();
                return;
            }

            if (add_temp.getText().length() > 0) {
                temp = Integer.parseInt(String.valueOf(add_temp.getText()));
            } else {
                temp = 0;
                Toast.makeText(getContext(),"Введите температуру", Toast.LENGTH_SHORT).show();
                return;
            }

            myEditor.putString("NAME", name);
            myEditor.putString("TYPE", type);
            myEditor.putInt("VOL", vol);
            myEditor.putInt("TEMP", temp);

            myEditor.commit();

            getFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment())
                    .commit();
        });

        return root;
    }
}
