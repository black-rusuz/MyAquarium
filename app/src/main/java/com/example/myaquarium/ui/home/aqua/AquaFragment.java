package com.example.myaquarium.ui.home.aqua;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.myaquarium.DatabaseHelper;
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
        TextView aqua_volume_unit = root.findViewById(R.id.aqua_volume_unit);
        TextView aqua_temperature = root.findViewById(R.id.aqua_temperature);
        TextView aqua_ph_circle = root.findViewById(R.id.aqua_ph_circle);
        TextView aqua_ph = root.findViewById(R.id.aqua_ph);

        ConstraintLayout aqua_content = root.findViewById(R.id.aqua_content);

        TextView aqua_fish_key = root.findViewById(R.id.aqua_fish_key);
        TextView aqua_fish = root.findViewById(R.id.aqua_fish);
        TextView aqua_plant_key = root.findViewById(R.id.aqua_plant_key);
        TextView aqua_plant = root.findViewById(R.id.aqua_plant);

        TextView aqua_feed = root.findViewById(R.id.aqua_feed);
        TextView aqua_refresh = root.findViewById(R.id.aqua_refresh);
        TextView aqua_condition = root.findViewById(R.id.aqua_condition);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        databaseHelper.create_db();
        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase();

        Cursor userCursor =  readableDatabase.rawQuery("select * from aquariums", null);

        if ((userCursor != null) && (userCursor.getCount() > 0)) {
            userCursor.moveToFirst();
            aqua_name.setText(userCursor.getString(userCursor.getColumnIndex("name")));
            String type = userCursor.getString(userCursor.getColumnIndex("type"));
            aqua_type.setText(type);

            if (type.equals("Смешанный")) {
                aqua_icon.setImageResource(R.drawable.pic_aquarium_mixed);
            }

            if (type.equals("Рыбки")) {
                aqua_icon.setImageResource(R.drawable.pic_aquarium_fish);
                aqua_plant_key.setVisibility(View.GONE);
                aqua_plant.setVisibility(View.GONE);
            }

            if (type.equals("Растения")) {
                aqua_icon.setImageResource(R.drawable.pic_aquarium_plant);
                aqua_fish_key.setVisibility(View.GONE);
                aqua_fish.setVisibility(View.GONE);
            }

            int volume = userCursor.getInt(userCursor.getColumnIndex("volume"));
            aqua_volume.setText(String.valueOf(volume));
            aqua_volume_unit.setText(getResources().getQuantityText(R.plurals.volume_unit, volume));

            aqua_temperature.setText(String.valueOf(userCursor.getInt(userCursor.getColumnIndex("temperature"))));

            float ph = userCursor.getFloat(userCursor.getColumnIndex("ph"));
            aqua_ph.setText(String.valueOf(ph));
            if ((ph > 0 && ph < 5) || (ph > 9.5 && ph <= 14)) {
                aqua_ph_circle.setTextColor(getResources().getColor(R.color.bad));
            } else {
                if ((ph >= 5 && ph < 6.5) || (ph > 8.5 && ph <= 9.5)) {
                    aqua_ph_circle.setTextColor(getResources().getColor(R.color.average));
                } else {
                    if (ph >= 6.5 && ph <= 8.5){
                        aqua_ph_circle.setTextColor(getResources().getColor(R.color.good));
                    }
                }
            }

            userCursor.close();

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

        aqua_feed.setText("утром и вечером");
        aqua_refresh.setText("каждые 2 недели");

        int rating = myPreferences.getInt("RATING", 0);
        if (rating < -1) {
            aqua_condition.setText(getResources().getString(R.string.terrible));
            aqua_condition.setTextColor(getResources().getColor(R.color.terrible));
        }
        if (rating == -1) {
            aqua_condition.setText(getResources().getString(R.string.bad));
            aqua_condition.setTextColor(getResources().getColor(R.color.bad));
        }
        if (rating == 0) {
            aqua_condition.setText(getResources().getString(R.string.average));
            aqua_condition.setTextColor(getResources().getColor(R.color.average));
        }
        if (rating == 1) {
            aqua_condition.setText(getResources().getString(R.string.good));
            aqua_condition.setTextColor(getResources().getColor(R.color.good));
        }
        if (rating > 1) {
            aqua_condition.setText(getResources().getString(R.string.excellent));
            aqua_condition.setTextColor(getResources().getColor(R.color.excellent));
        }

        return root;
    }
}