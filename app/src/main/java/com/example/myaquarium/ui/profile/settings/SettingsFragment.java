package com.example.myaquarium.ui.profile.settings;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myaquarium.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private View root;

    private SharedPreferences myPreferences;

    private SwitchMaterial settings_refresh;
    private Spinner settings_day;
    private SwitchMaterial settings_feed;
    //private Spinner settings_time;
    private TimePicker settings_timepicker;
    //private Button settings_change_password;
    //private Button settings_logout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        root = inflater.inflate(R.layout.fragment_settings, container, false);
        root.findViewById(R.id.settings_back).setOnClickListener(v -> getParentFragmentManager().popBackStack());

        myPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        settings_refresh = root.findViewById(R.id.settings_refresh);
        settings_day = root.findViewById(R.id.settings_day);
        settings_feed = root.findViewById(R.id.settings_feed);
        //settings_time = root.findViewById(R.id.settings_time);
        settings_timepicker = root.findViewById(R.id.settings_timepicker);
        //settings_change_password = root.findViewById(R.id.settings_change_password);
        //settings_logout = root.findViewById(R.id.settings_logout);

        settings_refresh.setChecked(myPreferences.getBoolean("REFRESH", true));
        settings_day.setSelection(myPreferences.getInt("DAY", 0));
        settings_feed.setChecked(myPreferences.getBoolean("FEED", true));
        //settings_time.setSelection(myPreferences.getInt("TIME", 0));
        settings_timepicker.setIs24HourView(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            settings_timepicker.setHour(myPreferences.getInt("HOUR", 0));
            settings_timepicker.setMinute(myPreferences.getInt("MINUTE", 0));
        } else {
            settings_timepicker.setCurrentHour(myPreferences.getInt("HOUR", 0));
            settings_timepicker.setCurrentMinute(myPreferences.getInt("MINUTE", 0));
        }

        return root;
    }

    public void onStop() {
        super.onStop();

        SharedPreferences.Editor myEditor = myPreferences.edit();

        myEditor.putBoolean("REFRESH", settings_refresh.isChecked());
        myEditor.putInt("DAY", settings_day.getSelectedItemPosition());
        myEditor.putBoolean("FEED", settings_feed.isChecked());
        //myEditor.putInt("TIME", settings_time.getSelectedItemPosition());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            myEditor.putInt("HOUR", settings_timepicker.getHour());
            myEditor.putInt("MINUTE", settings_timepicker.getMinute());
        } else {
            myEditor.putInt("HOUR", settings_timepicker.getCurrentHour());
            myEditor.putInt("MINUTE", settings_timepicker.getCurrentMinute());
        }

        myEditor.apply();
    }
}