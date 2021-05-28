package com.example.myaquarium.ui.home.add;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
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

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(root.getContext());
        SharedPreferences.Editor myEditor = myPreferences.edit();

        root.findViewById(R.id.add_back).setOnClickListener(v -> getFragmentManager().popBackStack());

        EditText add_name = root.findViewById(R.id.add_sv_name_et);
        Spinner add_type = root.findViewById(R.id.add_sv_type_sp);
        EditText add_vol = root.findViewById(R.id.add_sv_vol_et);
        EditText add_temp = root.findViewById(R.id.add_sv_temp_et);
        TextView add_header = root.findViewById(R.id.add_header);

        if (    ((myPreferences.getInt("VOL", 0)) != 0) &&
                ((myPreferences.getInt("TEMP", 0)) != 0)) {
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
//            getFragmentManager().beginTransaction()
//                    .replace(R.id.nav_host_fragment, new FishFragment())
//                    .addToBackStack(null)
//                    .commit();
            // TODO: допилить это говно с добавлениями

            LinearLayout add_sv_ll = root.findViewById(R.id.add_sv_ll);
            ConstraintLayout add_sv_cl = new ConstraintLayout(getContext(), null, R.style.ConstraintLayout_LL, R.style.ConstraintLayout_LL);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.margin_default_0_5x));
            add_sv_cl.setLayoutParams(lp);

            EditText et = new EditText(getContext(), null, R.style.EditText_Add_Text_Coded, R.style.EditText_Add_Text_Coded);
            add_sv_cl.addView(et);
            add_sv_ll.addView(add_sv_cl);

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

            if (add_name.getText().length() > 0)
                name = String.valueOf(add_name.getText());
            else {
                name = "Аквариум";
                // + " " + Integer.toString(i);
                // TODO: допилить много аквариумов
            }

            type = String.valueOf(add_type.getSelectedItem());

            if (add_vol.getText().length() > 0) {
                vol = Integer.parseInt(String.valueOf(add_vol.getText()));
                if (vol == 0) {
                    Toast.makeText(getContext(), "Объём не может быть равен нулю", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(getContext(),"Введите объём", Toast.LENGTH_SHORT).show();
                return;
            }

            if (add_temp.getText().length() > 0)
                temp = Integer.parseInt(String.valueOf(add_temp.getText()));
            else {
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
