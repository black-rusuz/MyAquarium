package ru.sfedu.myaquarium.ui.help.menu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.sfedu.myaquarium.DatabaseHelper;
import ru.sfedu.myaquarium.R;
import ru.sfedu.myaquarium.ui.help.page.PageFragment;

public class MenuFragment extends Fragment {

    private String category;
    private ListView menu_lv;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor userCursor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        root.findViewById(R.id.menu_back).setOnClickListener(v -> getParentFragmentManager().popBackStack());

        TextView menu_header = root.findViewById(R.id.menu_header);
        menu_lv = root.findViewById(R.id.menu_lv);

        Bundle bundle = this.getArguments();
        category = bundle.getString("category", "Категория");
        if (category.equals("fishes"))
            menu_header.setText("Рыбки");
        if (category.equals("plants"))
            menu_header.setText("Растения");

        menu_lv.setOnItemClickListener((parent, view, position, id) -> {
            Fragment fragment = new PageFragment();
            bundle.putInt("id", position);
            fragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .addToBackStack("Help")
                    .commit();
        });

        databaseHelper = new DatabaseHelper(getContext());
        databaseHelper.create_db();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        db = databaseHelper.open();
        userCursor = db.rawQuery("select * from " + category, null);
        SimpleCursorAdapter userAdapter = new SimpleCursorAdapter(
                getContext(),
                R.layout.listview,
                userCursor,
                new String[]{"name"},
                new int[]{android.R.id.text1}, 0);
        menu_lv.setAdapter(userAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
        userCursor.close();
    }
}