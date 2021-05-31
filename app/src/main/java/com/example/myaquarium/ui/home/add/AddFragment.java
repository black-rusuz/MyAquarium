package com.example.myaquarium.ui.home.add;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myaquarium.R;
import com.example.myaquarium.ui.home.HomeFragment;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

        TextView add_header = root.findViewById(R.id.add_header);
        EditText add_name = root.findViewById(R.id.add_sv_name_et);
        Spinner add_type = root.findViewById(R.id.add_sv_type_sp);
        EditText add_vol = root.findViewById(R.id.add_sv_vol_et);
        EditText add_temp = root.findViewById(R.id.add_sv_temp_et);
        Button add_fish = root.findViewById(R.id.add_fish);
        Button add_plant = root.findViewById(R.id.add_plant);
        Button add_save = root.findViewById(R.id.add_save);
        ScrollView add_sv = root.findViewById(R.id.add_sv);

        if (    ((myPreferences.getInt("VOL", 0)) != 0) &&
                ((myPreferences.getInt("TEMP", 0)) != 0)) {
            add_header.setText("Изменить");
            add_name.setText(myPreferences.getString("NAME", "Аквариум"));
            if (myPreferences.getString("TYPE", "Смешанный").equals("Смешанный")) {
                add_type.setSelection(0);
            }
            if (myPreferences.getString("TYPE", "Смешанный").equals("Рыбки")) {
                add_type.setSelection(1);
                add_plant.setVisibility(View.GONE);
            }
            if (myPreferences.getString("TYPE", "Смешанный").equals("Растения")) {
                add_type.setSelection(2);
                add_fish.setVisibility(View.GONE);
            }
            add_vol.setText(String.valueOf(myPreferences.getInt("VOL", 0)));
            add_temp.setText(String.valueOf(myPreferences.getInt("TEMP", 0)));
        }

        add_fish.setOnClickListener(v -> {
            // TODO: допилить это говно с добавлениями

            LinearLayout add_sv_ll = root.findViewById(R.id.add_sv_ll);
            ConstraintLayout add_sv_cl = new ConstraintLayout(getContext(), null, R.style.ConstraintLayout_LL, R.style.ConstraintLayout_LL);

            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.margin_default_0_5x));
            add_sv_cl.setLayoutParams(lp);

            EditText et_name = new EditText(getContext(), null, R.style.EditText_Add_Text_Coded, R.style.EditText_Add_Text_Coded);
            et_name.setFocusable(true);
            et_name.setFocusableInTouchMode(true);
            et_name.setHint("Рыбка...");

            int count_fish = myPreferences.getInt("COUNT_FISH", 0);
            String fish_id = "FISH_" + count_fish;
            count_fish++;
            et_name.setTag(fish_id);
            myEditor.putInt("COUNT_FISH", count_fish).commit();

            add_sv_cl.addView(et_name);
            add_sv_ll.addView(add_sv_cl);

            add_sv.fullScroll(View.FOCUS_DOWN);
        });

        add_fish.setOnLongClickListener(v -> {
            int count_fish = myPreferences.getInt("COUNT_FISH", 0);
            for (int i = 0; i <= count_fish; i++) {
                String fish_id = "FISH_" + i;
                myEditor.remove(fish_id);
                myEditor.putInt("COUNT_FISH", 0);
                myEditor.commit();
            }

            Toast.makeText(getContext(), "Рыбки очищены", Toast.LENGTH_SHORT).show();

            return true;
        });

        add_plant.setOnClickListener(v -> {
            LinearLayout add_sv_ll = root.findViewById(R.id.add_sv_ll);
            ConstraintLayout add_sv_cl = new ConstraintLayout(getContext(), null, R.style.ConstraintLayout_LL, R.style.ConstraintLayout_LL);

            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.margin_default_0_5x));
            add_sv_cl.setLayoutParams(lp);

            EditText et_name = new EditText(getContext(), null, R.style.EditText_Add_Text_Coded, R.style.EditText_Add_Text_Coded);
            et_name.setFocusable(true);
            et_name.setFocusableInTouchMode(true);
            et_name.setHint("Растение...");

            int count_plant = myPreferences.getInt("COUNT_PLANT", 0);
            String plant_id = "PLANT_" + count_plant;
            count_plant++;
            et_name.setTag(plant_id);
            myEditor.putInt("COUNT_PLANT", count_plant).commit();

            add_sv_cl.addView(et_name);
            add_sv_ll.addView(add_sv_cl);

            add_sv.fullScroll(View.FOCUS_DOWN);
        });

        add_plant.setOnLongClickListener(v -> {
            int count_plant = myPreferences.getInt("COUNT_PLANT", 0);
            for (int i = 0; i <= count_plant; i++) {
                String plant_id = "PLANT_" + i;
                myEditor.remove(plant_id);
                myEditor.putInt("COUNT_PLANT", 0);
                myEditor.commit();
            }

            Toast.makeText(getContext(), "Растения очищены", Toast.LENGTH_SHORT).show();

            return true;
        });

        add_save.setOnClickListener(v -> {

            String name;
            String type;
            Integer vol;
            Integer temp;

            if (add_name.getText().length() > 0)
                name = String.valueOf(add_name.getText());
            else {
                name = "Аквариум";
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

            int count_fish = myPreferences.getInt("COUNT_FISH", 0);
            for (int i = 0; i <= count_fish; i++) {
                String fish_id = "FISH_" + i;
                EditText fish_et = root.findViewWithTag(fish_id);
                if(fish_et != null) {
                    String fish_name = String.valueOf(fish_et.getText());
                    if (fish_name.length() > 0)
                        myEditor.putString(fish_id, fish_name);
                }
            }

            int count_plant = myPreferences.getInt("COUNT_PLANT", 0);
            for (int i = 0; i <= count_plant; i++) {
                String plant_id = "PLANT_" + i;
                EditText plant_et = root.findViewWithTag(plant_id);
                if(plant_et != null) {
                    String plant_name = String.valueOf(plant_et.getText());
                    if (plant_name.length() > 0)
                        myEditor.putString(plant_id, plant_name);
                }
            }

            myEditor.commit();

            getFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment())
                    .commit();
        });

        return root;
    }
}