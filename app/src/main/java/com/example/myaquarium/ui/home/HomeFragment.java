package com.example.myaquarium.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myaquarium.R;
import com.example.myaquarium.ui.home.add.AddFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        // TODO: Сделать переключение свайпами или Cardwipe
        View home_fragment = root.findViewById(R.id.home_fragment);
        Button home_add = root.findViewById(R.id.home_add);

        // TODO: Доделать редактирование и отображение аквариума
        if (myPreferences.getInt("VOLUME", 0) == 0) {
            home_fragment.setVisibility(View.INVISIBLE);
        }

        if (myPreferences.getInt("VOLUME", 0) != 0) {
            home_add.setText("Изменить");
            home_add.setCompoundDrawablesWithIntrinsicBounds(
                    ResourcesCompat.getDrawable(getResources(), R.drawable.ic_edit, null),
                    null,
                    null,
                    null);
        }

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