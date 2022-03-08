package ru.sfedu.myaquarium.ui.home.aquarium.fish;

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

public class FishFragment extends Fragment {
    private View root;
    private SharedPreferences myPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_fish, container, false);
        root.findViewById(R.id.fish_back).setOnClickListener(v -> getParentFragmentManager().popBackStack());

        myPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor myEditor = myPreferences.edit();

        LinearLayout fish_ll = root.findViewById(R.id.fish_ll);
        Button fish_add = root.findViewById(R.id.fish_add);

        fish_add.setOnClickListener(v -> {
            // TODO: Допилить это говно с добавлениями

            // Делаем ConstraintLayout и задаём стиль и отступы
            ConstraintLayout fish_cl = new ConstraintLayout(
                    getContext(),
                    null,
                    R.style.ConstraintLayout_LL,
                    R.style.ConstraintLayout_LL);
            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.margin_half));
            fish_cl.setLayoutParams(lp);

            // TODO: Надо делать не ЕТ, а спиннер с поиском
            EditText et_name = new EditText(
                    getContext(),
                    null,
                    R.style.EditText_Add_Text_Coded,
                    R.style.EditText_Add_Text_Coded);
            et_name.setFocusable(true);
            et_name.setFocusableInTouchMode(true);
            et_name.setHint("Рыбка...");

            // Задаём EditText ID со счётчиком
            int id_fish = myPreferences.getInt("ID_FISH", 0);
            String fish_id = "FISH_" + id_fish;
            id_fish++;
            et_name.setTag(fish_id);
            myEditor.putInt("ID_FISH", id_fish).apply();

            // Добавляем всё на слой
            fish_cl.addView(et_name);
            fish_ll.addView(fish_cl);
            et_name.setLayoutParams(new ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) (36 * getContext().getResources().getDisplayMetrics().density)));
            et_name.setGravity(Gravity.CENTER);

            // Даём фокус ласт полю
            et_name.requestFocus();
        });

        fish_add.setOnLongClickListener(v -> {
            int id_fish = myPreferences.getInt("ID_FISH", 0);
            for (int i = 0; i <= id_fish; i++) {
                String fish_id = "FISH_" + i;
                myEditor.remove(fish_id);
                myEditor.putInt("ID_FISH", 0);
                myEditor.commit();
            }

            Toast.makeText(
                    getContext(),
                    "Рыбки очищены",
                    Toast.LENGTH_SHORT).show();

            return true;
        });

        return root;
    }

    public void onStop() {
        super.onStop();

        SharedPreferences.Editor myEditor = myPreferences.edit();

        int id_fish = myPreferences.getInt("ID_FISH", 0);
        for (int i = 0; i <= id_fish; i++) {
            String fish_id = "FISH_" + i;
            EditText fish_et = root.findViewWithTag(fish_id);
            if(fish_et != null) {
                String fish_name = String.valueOf(fish_et.getText());
                if (fish_name.length() > 0)
                    myEditor.putString(fish_id, fish_name);
            }
        }

        myEditor.apply();
    }
}