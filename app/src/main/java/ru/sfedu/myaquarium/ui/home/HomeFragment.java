package ru.sfedu.myaquarium.ui.home;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.sfedu.myaquarium.DatabaseHelper;
import ru.sfedu.myaquarium.R;
import ru.sfedu.myaquarium.ui.home.add.AddFragment;
import ru.sfedu.myaquarium.ui.home.aquarium.AquariumFragment;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        // TODO: Сделать переключение свайпами или Cardwipe
        TextView home_header = root.findViewById(R.id.home_header);
        View home_fragment = root.findViewById(R.id.home_fragment);
        TextView aqua_name = root.findViewById(R.id.aqua_header);
        ImageView aqua_icon = root.findViewById(R.id.aqua_icon);
        Button home_add = root.findViewById(R.id.home_add);

        // TODO: Доделать редактирование и отображение аквариума
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        databaseHelper.create_db();
        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase();

        Cursor userCursor =  readableDatabase.rawQuery("select * from aquariums", null);
        if ((userCursor == null) || (userCursor.getCount() <= 0)) {
            home_fragment.setVisibility(View.INVISIBLE);
        }
        if ((userCursor != null) && (userCursor.getCount() > 0)) {
            home_add.setText("Изменить");
            home_add.setCompoundDrawablesWithIntrinsicBounds(
                    ResourcesCompat.getDrawable(getResources(), R.drawable.ic_edit, null),
                    null,
                    null,
                    null);
            myPreferences.edit().putInt("COUNT_A", 1).apply();
        }
        userCursor.close();

        aqua_name.setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new AquariumFragment())
                        .addToBackStack("Home")
                        .commit());
        aqua_icon.setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new AquariumFragment())
                        .addToBackStack("Home")
                        .commit());

        home_add.setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new AddFragment())
                        .addToBackStack("Home")
                        .commit());

        home_add.setOnLongClickListener(v -> {
            myPreferences.edit().clear().commit();
            System.exit(0);
            return true;
        });

        return root;
    }
}