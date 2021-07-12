package com.example.myaquarium.ui.help.page;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myaquarium.DatabaseHelper;
import com.example.myaquarium.DownloadImageTask;
import com.example.myaquarium.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PageFragment extends Fragment {

    private PageViewModel pageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pageViewModel =
                new ViewModelProvider(this).get(PageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_page, container, false);
        root.findViewById(R.id.page_back).setOnClickListener(v -> getParentFragmentManager().popBackStack());

        TextView page_header = root.findViewById(R.id.page_header);
        ImageView page_image = root.findViewById(R.id.page_image);
        TextView page_temperature = root.findViewById(R.id.page_temperature);
        TextView page_ph = root.findViewById(R.id.page_ph);
        TextView page_agressive = root.findViewById(R.id.page_agressive);
        TextView page_description = root.findViewById(R.id.page_description);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        databaseHelper.create_db();
        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase();

        Bundle bundle = this.getArguments();
        String category = bundle.getString("category", "plants");
        int id = bundle.getInt("id", 0);

        Cursor userCursor = readableDatabase.rawQuery("select * from " + category, null);
        userCursor.moveToPosition(id);

        page_header.setText(userCursor.getString(userCursor.getColumnIndex("name")));

//        page_image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//        if (category.equals("fishes")) {
//            page_image.setImageDrawable(getResources().getDrawable(R.drawable.bg_start_fish2));
//        }
//        if (category.equals("plants")) {
//            page_image.setImageDrawable(getResources().getDrawable(R.drawable.bg_start_plant));
//        }

        page_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        new DownloadImageTask(page_image)
                .execute(userCursor.getString(userCursor.getColumnIndex("img")));

        String temperature =
                userCursor.getInt(userCursor.getColumnIndex("tmin")) + " — "
                +
                userCursor.getInt(userCursor.getColumnIndex("tmax")) + "°C";
        page_temperature.setText(temperature);

        String ph =
                userCursor.getFloat(userCursor.getColumnIndex("phmin")) + " — "
                +
                userCursor.getFloat(userCursor.getColumnIndex("phmax"));
        page_ph.setText(ph);

        if (category.equals("fishes"))
            if (userCursor.getInt(userCursor.getColumnIndex("agressive")) == 0)
                page_agressive.setText("неагрессивны");
            else
                page_agressive.setText("агрессивны");
        else {
            root.findViewById(R.id.page_agressive_key).setVisibility(View.GONE);
            page_agressive.setVisibility(View.GONE);
        }

        page_description.setText(userCursor.getString(userCursor.getColumnIndex("description")));

        readableDatabase.close();
        userCursor.close();
        return root;
    }
}