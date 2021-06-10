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
        TextView aqua_vol = root.findViewById(R.id.aqua_vol);
        TextView aqua_temp = root.findViewById(R.id.aqua_temp);

        TextView aqua_fish_key = root.findViewById(R.id.aqua_fish_key);
        TextView aqua_fish = root.findViewById(R.id.aqua_fish);
        TextView aqua_plant_key = root.findViewById(R.id.aqua_plant_key);
        TextView aqua_plant = root.findViewById(R.id.aqua_plant);

        TextView aqua_feed = root.findViewById(R.id.aqua_feed);
        TextView aqua_refresh = root.findViewById(R.id.aqua_refresh);

        int count_fish = myPreferences.getInt("COUNT_FISH", 0);
        int count_plant = myPreferences.getInt("COUNT_PLANT", 0);
        int real_count_fish;
        int real_count_plant;

        String sp_aqua_type = myPreferences.getString("TYPE", "Смешанный");

        if (    !((myPreferences.getInt("VOL", 0)) == 0) &&
                !((myPreferences.getInt("TEMP", 0)) == 0)) {

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

            aqua_vol.setText(String.valueOf(myPreferences.getInt("VOL", 0)));
            TextView aqua_vol_unit = root.findViewById(R.id.aqua_vol_unit);
            aqua_vol_unit.setText(getResources().getQuantityText(R.plurals.vol_unit,
                    myPreferences.getInt("VOL", 0)));
            aqua_temp.setText(String.valueOf(myPreferences.getInt("TEMP", 0)));

            aqua_feed.setText("утром и вечером");
            aqua_refresh.setText("каждые 2 недели");

            real_count_fish = 0;
            String fishes = "Рыбки: ";
            for (int i = 0; i <= count_fish; i++) {
                String fish_id = "FISH_" + i;
                String fish_name = myPreferences.getString(fish_id, null);
                if (fish_name != null) {
                    real_count_fish++;
                    if (real_count_fish > 1)
                        fishes = fishes + ", ";
                    fishes = fishes + fish_name;
                }
            }
            aqua_fish.setText(fishes);

            real_count_plant = 0;
            String plants = "Растения: ";
            for (int i = 0; i <= count_plant; i++) {
                String plant_id = "PLANT_" + i;
                String plant_name = myPreferences.getString(plant_id, null);
                if (plant_name != null) {
                    real_count_plant++;
                    if (real_count_plant > 1)
                        plants = plants + ", ";
                    plants = plants + plant_name;
                }
            }
            aqua_plant.setText(plants);

            myEditor.putInt("REAL_COUNT_FISH", real_count_fish);
            myEditor.putInt("REAL_COUNT_PLANT", real_count_plant);
            myEditor.apply();

            if (real_count_fish <= 0) {
                aqua_fish_key.setVisibility(View.GONE);
                aqua_fish.setVisibility(View.GONE);
            }

            if (real_count_plant <= 0) {
                aqua_plant_key.setVisibility(View.GONE);
                aqua_plant.setVisibility(View.GONE);
            }
        }

        return root;
    }
}