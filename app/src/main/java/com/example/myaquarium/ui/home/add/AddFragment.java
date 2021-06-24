package com.example.myaquarium.ui.home.add;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import com.example.myaquarium.DatabaseHelper;
import com.example.myaquarium.R;

public class AddFragment extends Fragment {

    private AddViewModel addViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add, container, false);
        root.findViewById(R.id.add_back).setOnClickListener(v -> getParentFragmentManager().popBackStack());

        TextView add_header = root.findViewById(R.id.add_header);
        EditText add_name = root.findViewById(R.id.add_name);
        Spinner add_type = root.findViewById(R.id.add_type);
        EditText add_volume = root.findViewById(R.id.add_volume);
        EditText add_temperature = root.findViewById(R.id.add_temperature);
        EditText add_ph = root.findViewById(R.id.add_ph);

        EditText add_gh = root.findViewById(R.id.add_gh);
        EditText add_kh = root.findViewById(R.id.add_kh);
        EditText add_no2 = root.findViewById(R.id.add_no2);
        EditText add_no3 = root.findViewById(R.id.add_no3);
        EditText add_cl2 = root.findViewById(R.id.add_cl2);
        EditText add_nh4 = root.findViewById(R.id.add_nh4);

        Button add_save = root.findViewById(R.id.add_save);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        databaseHelper.create_db();
        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase();
        SQLiteDatabase writableDatabase = databaseHelper.getWritableDatabase();

        Cursor userCursor =  readableDatabase.rawQuery("select * from aquariums", null);
        if ((userCursor != null) && (userCursor.getCount() > 0)) {
            userCursor.moveToFirst();
            add_header.setText("Изменить аквариум");
            add_name.setText(userCursor.getString(userCursor.getColumnIndex("name")));

            if (userCursor.getString(userCursor.getColumnIndex("type")).equals("Смешанный")) {
                add_type.setSelection(0);
            }
            if (userCursor.getString(userCursor.getColumnIndex("type")).equals("Рыбки")) {
                add_type.setSelection(1);
            }
            if (userCursor.getString(userCursor.getColumnIndex("type")).equals("Растения")) {
                add_type.setSelection(2);
            }

            add_volume.setText(String.valueOf(userCursor.getInt(userCursor.getColumnIndex("volume"))));
            add_temperature.setText(String.valueOf(userCursor.getInt(userCursor.getColumnIndex("temperature"))));
            add_ph.setText(String.valueOf(userCursor.getFloat(userCursor.getColumnIndex("ph"))));

            add_gh.setText(String.valueOf(userCursor.getInt(userCursor.getColumnIndex("gh"))));
            add_kh.setText(String.valueOf(userCursor.getInt(userCursor.getColumnIndex("kh"))));
            add_no2.setText(String.valueOf(userCursor.getInt(userCursor.getColumnIndex("no2"))));
            add_no3.setText(String.valueOf(userCursor.getInt(userCursor.getColumnIndex("no3"))));
            add_cl2.setText(String.valueOf(userCursor.getFloat(userCursor.getColumnIndex("cl2"))));
            add_nh4.setText(String.valueOf(userCursor.getFloat(userCursor.getColumnIndex("nh4"))));
            userCursor.close();
        }

        add_save.setOnClickListener(v -> {

            ContentValues cv = new ContentValues();
            String name;
            String type;
            int volume;
            int temperature;
            float ph = 0.0f;
            int gh = 0;
            int kh = 0;
            int no2 = 0;
            int no3 = 0;
            float cl2 = 0.0f;
            float nh4 = 0.0f;

            if (add_name.getText().length() > 0)
                name = String.valueOf(add_name.getText());
            else {
                // TODO: Допилить много аквариумов
                name = "Аквариум";
            }

            type = String.valueOf(add_type.getSelectedItem());

            if (add_volume.getText().length() > 0) {
                volume = Integer.parseInt(String.valueOf(add_volume.getText()));
                if (volume == 0) {
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

            if (add_temperature.getText().length() > 0)
                temperature = Integer.parseInt(String.valueOf(add_temperature.getText()));
            else {
                Toast.makeText(
                        getContext(),
                        "Введите температуру",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (add_ph.getText().length() > 0)
                ph = Float.parseFloat(String.valueOf(add_ph.getText()));

            if (add_gh.getText().length() > 0)
                gh = Integer.parseInt(String.valueOf(add_gh.getText()));
            if (add_kh.getText().length() > 0)
                kh = Integer.parseInt(String.valueOf(add_kh.getText()));
            if (add_no2.getText().length() > 0)
                no2 = Integer.parseInt(String.valueOf(add_no2.getText()));
            if (add_no3.getText().length() > 0)
                no3 = Integer.parseInt(String.valueOf(add_no3.getText()));
            if (add_cl2.getText().length() > 0)
                cl2 = Float.parseFloat(String.valueOf(add_cl2.getText()));
            if (add_nh4.getText().length() > 0)
                nh4 = Float.parseFloat(String.valueOf(add_nh4.getText()));

            cv.put("name", name);
            cv.put("type", type);
            cv.put("volume", volume);
            cv.put("temperature", temperature);
            cv.put("ph", ph);

            cv.put("gh", gh);
            cv.put("kh", kh);
            cv.put("no2", no2);
            cv.put("no3", no3);
            cv.put("cl2", cl2);
            cv.put("nh4", nh4);

            if ((userCursor != null) && (userCursor.getCount() > 0))
                writableDatabase.update("aquariums", cv, null, null);
            else
                writableDatabase.insert("aquariums", null, cv);
            writableDatabase.close();

            getParentFragmentManager().popBackStack();
        });

        return root;
    }
}