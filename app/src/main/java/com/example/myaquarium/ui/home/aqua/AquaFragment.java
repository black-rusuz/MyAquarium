package com.example.myaquarium.ui.home.aqua;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor myEditor = myPreferences.edit();

        TextView aqua_name = root.findViewById(R.id.aqua_header);
        ImageView aqua_icon = root.findViewById(R.id.aqua_icon);
        TextView aqua_type = root.findViewById(R.id.aqua_type);
        TextView aqua_volume = root.findViewById(R.id.aqua_volume);
        TextView aqua_temperature = root.findViewById(R.id.aqua_temperature);
        TextView aqua_ph = root.findViewById(R.id.aqua_ph);

        ConstraintLayout aqua_content = root.findViewById(R.id.aqua_content);

        TextView aqua_fish_key = root.findViewById(R.id.aqua_fish_key);
        TextView aqua_fish = root.findViewById(R.id.aqua_fish);
        TextView aqua_plant_key = root.findViewById(R.id.aqua_plant_key);
        TextView aqua_plant = root.findViewById(R.id.aqua_plant);

        TextView aqua_feed = root.findViewById(R.id.aqua_feed);
        TextView aqua_refresh = root.findViewById(R.id.aqua_refresh);

        int id_fish = myPreferences.getInt("ID_FISH", 0);
        int id_plant = myPreferences.getInt("ID_PLANT", 0);
        int count_fish;
        int count_plant;

        String sp_aqua_type = myPreferences.getString("TYPE", "Смешанный");

        if (myPreferences.getInt("VOLUME", 0) != 0) {
            aqua_name.setText(myPreferences.getString("NAME", "Аквариум"));

            aqua_type.setText(sp_aqua_type);

            if (sp_aqua_type.equals("Смешанный")) {
                aqua_icon.setImageResource(R.drawable.pic_aquarium_mixed);
            }

            if (sp_aqua_type.equals("Рыбки")) {
                aqua_icon.setImageResource(R.drawable.pic_aquarium_fish);
                aqua_plant_key.setVisibility(View.GONE);
                aqua_plant.setVisibility(View.GONE);
            }

            if (sp_aqua_type.equals("Растения")) {
                aqua_icon.setImageResource(R.drawable.pic_aquarium_plant);
                aqua_fish_key.setVisibility(View.GONE);
                aqua_fish.setVisibility(View.GONE);
            }

            aqua_volume.setText(String.valueOf(myPreferences.getInt("VOLUME", 0)));
            TextView aqua_volume_unit = root.findViewById(R.id.aqua_volume_unit);
            aqua_volume_unit.setText(getResources().getQuantityText(R.plurals.volume_unit,
                    myPreferences.getInt("VOLUME", 0)));
            aqua_temperature.setText(String.valueOf(myPreferences.getInt("TEMPERATURE", 0)));
            aqua_ph.setText(String.valueOf(myPreferences.getFloat("PH", 0)));

            aqua_feed.setText("утром и вечером");
            aqua_refresh.setText("каждые 2 недели");

            count_fish = 0;
            String fishes = "Рыбки: ";
            for (int i = 0; i <= id_fish; i++) {
                String fish_id = "FISH_" + i;
                String fish_name = myPreferences.getString(fish_id, null);
                if (fish_name != null) {
                    count_fish++;
                    if (count_fish > 1)
                        fishes = fishes + ", ";
                    fishes = fishes + fish_name;
                }
            }
            aqua_fish.setText(fishes);

            count_plant = 0;
            String plants = "Растения: ";
            for (int i = 0; i <= id_plant; i++) {
                String plant_id = "PLANT_" + i;
                String plant_name = myPreferences.getString(plant_id, null);
                if (plant_name != null) {
                    count_plant++;
                    if (count_plant > 1)
                        plants = plants + ", ";
                    plants = plants + plant_name;
                }
            }
            aqua_plant.setText(plants);

            myEditor.putInt("COUNT_FISH", count_fish);
            myEditor.putInt("COUNT_PLANT", count_plant);
            myEditor.apply();

            if (count_fish <= 0 && count_plant <= 0)
                aqua_content.setVisibility(View.GONE);

            if (count_fish <= 0) {
                aqua_fish_key.setVisibility(View.GONE);
                aqua_fish.setVisibility(View.GONE);
            }

            if (count_plant <= 0) {
                aqua_plant_key.setVisibility(View.GONE);
                aqua_plant.setVisibility(View.GONE);
            }
        }

        return root;
    }
}