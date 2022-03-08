package ru.sfedu.myaquarium.ui.home.aquarium.plant;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.sfedu.myaquarium.R;

public class PlantFragment extends Fragment {

    private View root;
    private SharedPreferences myPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_plant, container, false);
        root.findViewById(R.id.plant_back).setOnClickListener(v -> getParentFragmentManager().popBackStack());

        myPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor myEditor = myPreferences.edit();

        LinearLayout plant_ll = root.findViewById(R.id.plant_ll);
        Button plant_add = root.findViewById(R.id.plant_add);

        plant_add.setOnClickListener(v -> {
            // TODO: Допилить это говно с добавлениями

            // Делаем ConstraintLayout и задаём стиль и отступы
            ConstraintLayout plant_cl = new ConstraintLayout(
                    getContext(),
                    null,
                    R.style.ConstraintLayout_LL,
                    R.style.ConstraintLayout_LL);
            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.margin_half));
            plant_cl.setLayoutParams(lp);

            // TODO: Надо делать не ЕТ, а спиннер с поиском
            EditText et_name = new EditText(
                    getContext(),
                    null,
                    R.style.EditText_Add_Text_Coded,
                    R.style.EditText_Add_Text_Coded);
            et_name.setFocusable(true);
            et_name.setFocusableInTouchMode(true);
            et_name.setHint("Растение...");

            // Задаём EditText ID со счётчиком
            int id_plant = myPreferences.getInt("ID_PLANT", 0);
            String plant_id = "PLANT_" + id_plant;
            id_plant++;
            et_name.setTag(plant_id);
            myEditor.putInt("ID_PLANT", id_plant).apply();

            // Добавляем всё на слой
            plant_cl.addView(et_name);
            plant_ll.addView(plant_cl);
            et_name.setLayoutParams(new ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) (36 * getContext().getResources().getDisplayMetrics().density)));
            et_name.setGravity(Gravity.CENTER);

            // Даём фокус ласт полю
            et_name.requestFocus();
        });

        plant_add.setOnLongClickListener(v -> {
            int id_plant = myPreferences.getInt("ID_PLANT", 0);
            for (int i = 0; i <= id_plant; i++) {
                String plant_id = "PLANT_" + i;
                myEditor.remove(plant_id);
                myEditor.putInt("ID_PLANT", 0);
                myEditor.commit();
            }

            Toast.makeText(
                    getContext(),
                    "Растения очищены",
                    Toast.LENGTH_SHORT).show();

            return true;
        });

        return root;
    }

    public void onStop() {
        super.onStop();

        SharedPreferences.Editor myEditor = myPreferences.edit();

        int id_plant = myPreferences.getInt("ID_PLANT", 0);
        for (int i = 0; i <= id_plant; i++) {
            String plant_id = "PLANT_" + i;
            EditText plant_et = root.findViewWithTag(plant_id);
            if (plant_et != null) {
                String plant_name = String.valueOf(plant_et.getText());
                if (plant_name.length() > 0)
                    myEditor.putString(plant_id, plant_name);
            }
        }

        myEditor.apply();
    }
}