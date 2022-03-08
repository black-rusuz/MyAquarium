package ru.sfedu.myaquarium.ui.help;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.sfedu.myaquarium.R;
import ru.sfedu.myaquarium.ui.help.menu.MenuFragment;

public class HelpFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_help, container, false);

        Button help_sv_1 = root.findViewById(R.id.help_sv_1);
        Button help_sv_2 = root.findViewById(R.id.help_sv_2);
        Button help_sv_3 = root.findViewById(R.id.help_sv_3);
        Button help_sv_4 = root.findViewById(R.id.help_sv_4);
        Button help_sv_5 = root.findViewById(R.id.help_sv_5);

        //TODO: Тут переделать под внутреннее отображение
        help_sv_1.setOnClickListener(v -> {
            Fragment fragment = new MenuFragment();
            Bundle bundle = new Bundle();
            bundle.putString("category", "fishes");
            fragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .addToBackStack("Help")
                    .commit();
        });

        help_sv_2.setOnClickListener(v -> {
            Fragment fragment = new MenuFragment();
            Bundle bundle = new Bundle();
            bundle.putString("category", "plants");
            fragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .addToBackStack("Help")
                    .commit();
        });

        help_sv_3.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.aqvium.ru/aquarium/voda/gidrokhimichesky-sostav-vody");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        help_sv_4.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.aqvium.ru/aquarium");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        help_sv_5.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.aqvium.ru/aquarium/zapusk-akvariuma");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        return root;
    }
}