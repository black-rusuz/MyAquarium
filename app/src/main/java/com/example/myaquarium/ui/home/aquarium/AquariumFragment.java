package com.example.myaquarium.ui.home.aquarium;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myaquarium.DatabaseHelper;
import com.example.myaquarium.R;
import com.example.myaquarium.ui.home.add.AddFragment;
import com.example.myaquarium.ui.home.aquarium.fish.FishFragment;
import com.example.myaquarium.ui.home.aquarium.plant.PlantFragment;

public class AquariumFragment extends Fragment {

    private AquariumViewModel aquariumViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aquariumViewModel =
                new ViewModelProvider(this).get(AquariumViewModel.class);
        View root = inflater.inflate(R.layout.fragment_aquarium, container, false);
        root.findViewById(R.id.aquarium_back).setOnClickListener(v -> getParentFragmentManager().popBackStack());

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor myEditor = myPreferences.edit();

        TextView aquarium_name = root.findViewById(R.id.aquarium_header);
        TextView aquarium_type = root.findViewById(R.id.aquarium_type);
        TextView aquarium_volume = root.findViewById(R.id.aquarium_volume);
        TextView aquarium_volume_unit = root.findViewById(R.id.aquarium_volume_unit);
        TextView aquarium_temperature = root.findViewById(R.id.aquarium_temperature);
        TextView aquarium_ph_circle = root.findViewById(R.id.aquarium_ph_circle);
        TextView aquarium_ph = root.findViewById(R.id.aquarium_ph);

        TextView aquarium_gh = root.findViewById(R.id.aquarium_gh);
        TextView aquarium_kh = root.findViewById(R.id.aquarium_kh);
        TextView aquarium_no2 = root.findViewById(R.id.aquarium_no2);
        TextView aquarium_no3 = root.findViewById(R.id.aquarium_no3);
        TextView aquarium_cl2 = root.findViewById(R.id.aquarium_cl2);
        TextView aquarium_nh4 = root.findViewById(R.id.aquarium_nh4);

        TextView aquarium_gh_circle = root.findViewById(R.id.aquarium_gh_circle);
        TextView aquarium_kh_circle = root.findViewById(R.id.aquarium_kh_circle);
        TextView aquarium_no2_circle = root.findViewById(R.id.aquarium_no2_circle);
        TextView aquarium_no3_circle = root.findViewById(R.id.aquarium_no3_circle);
        TextView aquarium_cl2_circle = root.findViewById(R.id.aquarium_cl2_circle);
        TextView aquarium_nh4_circle = root.findViewById(R.id.aquarium_nh4_circle);

        ConstraintLayout aquarium_content = root.findViewById(R.id.aquarium_content);

        TextView aquarium_fish_key = root.findViewById(R.id.aquarium_fish_key);
        TextView aquarium_fish = root.findViewById(R.id.aquarium_fish);
        TextView aquarium_plant_key = root.findViewById(R.id.aquarium_plant_key);
        TextView aquarium_plant = root.findViewById(R.id.aquarium_plant);

        TextView aquarium_feed = root.findViewById(R.id.aquarium_feed);
        TextView aquarium_refresh = root.findViewById(R.id.aquarium_refresh);

        Button aquarium_addFish = root.findViewById(R.id.aquarium_addFish);
        Button aquarium_addPlant = root.findViewById(R.id.aquarium_addPlant);

        Button aquarium_edit = root.findViewById(R.id.aquarium_edit);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        databaseHelper.create_db();
        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase();

        Cursor userCursor =  readableDatabase.rawQuery("select * from aquariums", null);

        if ((userCursor != null) && (userCursor.getCount() > 0)) {
            userCursor.moveToFirst();
            int rating = 0;

            aquarium_name.setText(userCursor.getString(userCursor.getColumnIndex("name")));
            String type = userCursor.getString(userCursor.getColumnIndex("type"));
            aquarium_type.setText(type);

            int volume = userCursor.getInt(userCursor.getColumnIndex("volume"));
            aquarium_volume.setText(String.valueOf(volume));
            aquarium_volume_unit.setText(getResources().getQuantityText(R.plurals.volume_unit, volume));

            aquarium_temperature.setText(String.valueOf(userCursor.getInt(userCursor.getColumnIndex("temperature"))));

            float ph = userCursor.getFloat(userCursor.getColumnIndex("ph"));
            aquarium_ph.setText(String.valueOf(ph));
            if ((ph > 0 && ph < 5) || (ph > 9.5 && ph <= 14)) {
                aquarium_ph_circle.setTextColor(getResources().getColor(R.color.bad));
                rating--;
            } else {
                if ((ph >= 5 && ph < 6.5) || (ph > 8.5 && ph <= 9.5)) {
                    aquarium_ph_circle.setTextColor(getResources().getColor(R.color.average));
                } else {
                    if (ph >= 6.5 && ph <= 8.5) {
                        aquarium_ph_circle.setTextColor(getResources().getColor(R.color.good));
                        rating++;
                    }
                }
            }

            int id_fish = myPreferences.getInt("ID_FISH", 0);
            int id_plant = myPreferences.getInt("ID_PLANT", 0);
            int count_fish;
            int count_plant;

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
            aquarium_fish.setText(fishes);

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
            aquarium_plant.setText(plants);

            myEditor.putInt("COUNT_FISH", count_fish);
            myEditor.putInt("COUNT_PLANT", count_plant);
            myEditor.apply();

            if (count_fish <= 0 && count_plant <= 0)
                aquarium_content.setVisibility(View.GONE);

            if (count_fish <= 0) {
                aquarium_fish_key.setVisibility(View.GONE);
                aquarium_fish.setVisibility(View.GONE);
            }

            if (count_plant <= 0) {
                aquarium_plant_key.setVisibility(View.GONE);
                aquarium_plant.setVisibility(View.GONE);
            }

            int gh = userCursor.getInt(userCursor.getColumnIndex("gh"));
            int kh = userCursor.getInt(userCursor.getColumnIndex("kh"));
            int no2 = userCursor.getInt(userCursor.getColumnIndex("no2"));
            int no3 = userCursor.getInt(userCursor.getColumnIndex("no3"));
            float cl2 = userCursor.getFloat(userCursor.getColumnIndex("cl2"));
            float nh4 = userCursor.getFloat(userCursor.getColumnIndex("nh4"));

            aquarium_gh.setText(String.valueOf(gh));
            aquarium_kh.setText(String.valueOf(kh));
            aquarium_no2.setText(String.valueOf(no2));
            aquarium_no3.setText(String.valueOf(no3));
            aquarium_cl2.setText(String.valueOf(cl2));
            aquarium_nh4.setText(String.valueOf(nh4));

            if ((gh >= 0 && gh < 5) || (gh > 20)) {
                aquarium_gh_circle.setTextColor(getResources().getColor(R.color.average));
            } else {
                if (gh >= 5 && gh <= 20) {
                    aquarium_gh_circle.setTextColor(getResources().getColor(R.color.good));
                    rating++;
                }
            }

            if ((kh >= 0 && kh < 4) || (kh > 16)) {
                aquarium_kh_circle.setTextColor(getResources().getColor(R.color.average));
            } else {
                if (kh >= 4 && kh <= 16) {
                    aquarium_kh_circle.setTextColor(getResources().getColor(R.color.good));
                    rating++;
                }
            }

            if (no2 > 0.5) {
                aquarium_no2_circle.setTextColor(getResources().getColor(R.color.bad));
                rating--;
            } else {
                if (no2 > 0 && no2 <= 0.5) {
                    aquarium_no2_circle.setTextColor(getResources().getColor(R.color.average));
                } else {
                    if (no2 == 0) {
                        aquarium_no2_circle.setTextColor(getResources().getColor(R.color.good));
                        rating++;
                    }
                }
            }

            if (no3 > 50) {
                aquarium_no3_circle.setTextColor(getResources().getColor(R.color.bad));
                rating--;
            } else {
                if (no3 > 25 && no3 <= 50) {
                    aquarium_no3_circle.setTextColor(getResources().getColor(R.color.average));
                } else {
                    if (no3 >= 0 && no3 <= 25) {
                        aquarium_no3_circle.setTextColor(getResources().getColor(R.color.good));
                        rating++;
                    }
                }
            }

            if (cl2 > 0.7) {
                aquarium_cl2_circle.setTextColor(getResources().getColor(R.color.bad));
                rating--;
            } else {
                if (cl2 >= 0 && cl2 <= 0.7) {
                    aquarium_cl2_circle.setTextColor(getResources().getColor(R.color.good));
                    rating++;
                }
            }

            if (nh4 > 0.01) {
                aquarium_nh4_circle.setTextColor(getResources().getColor(R.color.bad));
                rating--;
            } else {
                if (nh4 >= 0 && nh4 <= 0.01) {
                    aquarium_nh4_circle.setTextColor(getResources().getColor(R.color.good));
                    rating++;
                }
            }

            myEditor.putInt("RATING", rating).apply();

            aquarium_feed.setText("утром и вечером");
            aquarium_refresh.setText("каждые 2 недели");

            aquarium_edit.setOnClickListener(v ->
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment, new AddFragment())
                            .addToBackStack("Aquarium")
                            .commit());

            aquarium_addFish.setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new FishFragment())
                        .addToBackStack("Aquarium")
                        .commit());

            aquarium_addPlant.setOnClickListener(v ->
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment, new PlantFragment())
                            .addToBackStack("Aquarium")
                            .commit());
        }
        
        readableDatabase.close();
        userCursor.close();
        return root;
    }
}