package ru.sfedu.myaquarium.ui.home.aquarium.insider;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import ru.sfedu.myaquarium.DatabaseHelper;
import ru.sfedu.myaquarium.R;

public class InsiderFragment extends Fragment {

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_insider, container, false);
        root.findViewById(R.id.insider_back).setOnClickListener(v -> getParentFragmentManager().popBackStack());

        TextView insider_header = root.findViewById(R.id.insider_header);
        LinearLayout insider_ll = root.findViewById(R.id.insider_ll);
        SearchableSpinner insider_ss = root.findViewById(R.id.insider_ss);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        databaseHelper.create_db();
        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase();

        Bundle bundle = this.getArguments();
        String insider = bundle.getString("insider", "fishes");

        if (insider.equals("fishes")) {
            insider_ss.setTitle("Рыбки");
            insider_header.append("рыбок");
        }
        if (insider.equals("plants")) {
            insider_ss.setTitle("Растения");
            insider_header.append("растения");
        }
        insider_ss.setPositiveButton("Ок");

        Cursor userCursor = readableDatabase.rawQuery("select * from aquariums", null);
        userCursor.moveToFirst();
        int temperature = userCursor.getInt(userCursor.getColumnIndex("temperature"));

        userCursor = readableDatabase.rawQuery(
                "select * from " + insider + " where tmin <= " + temperature + " and tmax >= " + temperature,
                null);
        userCursor.moveToFirst();
        String[] fishes = new String[userCursor.getCount()];
        for (int i = 0; i < userCursor.getCount(); i++) {
            fishes[i] = userCursor.getString(userCursor.getColumnIndex("name"));
            userCursor.moveToNext();
        }
        SpinnerAdapter adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                fishes
        );
        insider_ss.setAdapter(adapter);

        insider_ss.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SearchableSpinner insider_ss_new = new SearchableSpinner(
                        getContext(),
                        null,
                        R.style.SearchableSpinner);
                insider_ss_new.setFocusable(true);
                insider_ss_new.setFocusableInTouchMode(true);

                if (insider.equals("fishes")) {
                    insider_ss_new.setTitle("Рыбки");
                }
                if (insider.equals("plants")) {
                    insider_ss_new.setTitle("Растения");
                }
                insider_ss_new.setPositiveButton("Ок");
                insider_ss_new.setAdapter(adapter);

                insider_ll.addView(insider_ss_new);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        readableDatabase.close();
        userCursor.close();
        return root;
    }

    public void onStop() {
        super.onStop();
    }
}