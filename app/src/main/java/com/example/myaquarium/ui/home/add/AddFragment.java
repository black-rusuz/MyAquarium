package com.example.myaquarium.ui.home.add;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myaquarium.R;
import com.example.myaquarium.ui.home.HomeFragment;

public class AddFragment extends Fragment {

    private AddViewModel addViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add, container, false);
        root.findViewById(R.id.add_back).setOnClickListener(v -> getFragmentManager().popBackStack());

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor myEditor = myPreferences.edit();

        TextView add_header = root.findViewById(R.id.add_header);
        EditText add_name = root.findViewById(R.id.add_sv_name);
        Spinner add_type = root.findViewById(R.id.add_sv_type);
        EditText add_vol = root.findViewById(R.id.add_sv_vol);
        EditText add_temp = root.findViewById(R.id.add_sv_temp);

        LinearLayout add_sv_ll = root.findViewById(R.id.add_sv_ll);
        Button add_fish = root.findViewById(R.id.add_fish);
        Button add_plant = root.findViewById(R.id.add_plant);
        Button add_save = root.findViewById(R.id.add_save);

        if (myPreferences.getInt("VOL", 0) != 0) {

            add_header.setText("Изменить аквариум");
            add_name.setText(myPreferences.getString("NAME", null));

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

            // TODO: Допилить вывод полей с обитателями
        }

        add_fish.setOnClickListener(v -> {
            // TODO: Допилить это говно с добавлениями

            // Делаем ConstraintLayout и задаём стиль и отступы
            ConstraintLayout add_sv_cl = new ConstraintLayout(
                    getContext(),
                    null,
                    R.style.ConstraintLayout_LL,
                    R.style.ConstraintLayout_LL);
            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.margin_half));
            add_sv_cl.setLayoutParams(lp);

            // Делаем EditText
            // TODO: Надо делать не ЕТ, а спиннер с поиском
            // https://github.com/miteshpithadiya/SearchableSpinner
            EditText et_name = new EditText(
                    getContext(),
                    null,
                    R.style.EditText_Add_Text_Coded,
                    R.style.EditText_Add_Text_Coded);
            et_name.setFocusable(true);
            et_name.setFocusableInTouchMode(true);
            et_name.setHint("Рыбка...");

            // Задаём EditText ID со счётчиком
            int count_fish = myPreferences.getInt("COUNT_FISH", 0);
            String fish_id = "FISH_" + count_fish;
            count_fish++;
            et_name.setTag(fish_id);
            myEditor.putInt("COUNT_FISH", count_fish).apply();

            // Добавляем всё на слой
            add_sv_cl.addView(et_name);
            add_sv_ll.addView(add_sv_cl);

            // Даём фокус ласт полю
            et_name.requestFocus();
        });

        add_fish.setOnLongClickListener(v -> {
            int count_fish = myPreferences.getInt("COUNT_FISH", 0);
            for (int i = 0; i <= count_fish; i++) {
                String fish_id = "FISH_" + i;
                myEditor.remove(fish_id);
                myEditor.putInt("COUNT_FISH", 0);
                myEditor.commit();
            }

            Toast.makeText(
                    getContext(),
                    "Рыбки очищены",
                    Toast.LENGTH_SHORT).show();

            return true;
        });

        add_plant.setOnClickListener(v -> {

            // Делаем ConstraintLayout и задаём стиль и отступы
            ConstraintLayout add_sv_cl = new ConstraintLayout(
                    getContext(),
                    null,
                    R.style.ConstraintLayout_LL,
                    R.style.ConstraintLayout_LL);
            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.margin_half));
            add_sv_cl.setLayoutParams(lp);

            // Делаем EditText
            EditText et_name = new EditText(
                    getContext(),
                    null,
                    R.style.EditText_Add_Text_Coded,
                    R.style.EditText_Add_Text_Coded);
            et_name.setFocusable(true);
            et_name.setFocusableInTouchMode(true);
            et_name.setHint("Растение...");

            // Задаём EditText ID со счётчиком
            int count_plant = myPreferences.getInt("COUNT_PLANT", 0);
            String plant_id = "PLANT_" + count_plant;
            count_plant++;
            et_name.setTag(plant_id);
            myEditor.putInt("COUNT_PLANT", count_plant).apply();

            // Добавляем всё на слой
            add_sv_cl.addView(et_name);
            add_sv_ll.addView(add_sv_cl);

            // Даём фокус ласт полю
            et_name.requestFocus();
        });

        add_plant.setOnLongClickListener(v -> {
            int count_plant = myPreferences.getInt("COUNT_PLANT", 0);
            for (int i = 0; i <= count_plant; i++) {
                String plant_id = "PLANT_" + i;
                myEditor.remove(plant_id);
                myEditor.putInt("COUNT_PLANT", 0);
                myEditor.commit();
            }

            Toast.makeText(
                    getContext(),
                    "Растения очищены",
                    Toast.LENGTH_SHORT).show();

            return true;
        });

        add_save.setOnClickListener(v -> {

            String name;
            String type;
            int vol;
            int temp;

            if (add_name.getText().length() > 0)
                name = String.valueOf(add_name.getText());
            else {
                // TODO: Допилить много аквариумов
                name = "Аквариум";
            }

            type = String.valueOf(add_type.getSelectedItem());

            if (add_vol.getText().length() > 0) {
                vol = Integer.parseInt(String.valueOf(add_vol.getText()));
                if (vol == 0) {
                    Toast.makeText(
                            getContext(),
                            "Объём не может быть равен нулю",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(
                        getContext(),
                        "Введите объём",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (add_temp.getText().length() > 0)
                temp = Integer.parseInt(String.valueOf(add_temp.getText()));
            else {
                Toast.makeText(
                        getContext(),
                        "Введите температуру",
                        Toast.LENGTH_SHORT).show();
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

            myEditor.apply();

            getFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new HomeFragment())
                    .commit();
        });

        return root;
    }
}