package com.example.myaquarium.ui.profile;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myaquarium.R;
import com.example.myaquarium.ResizeAnimation;
import com.example.myaquarium.ui.home.HomeFragment;
import com.example.myaquarium.ui.profile.about.AboutFragment;
import com.example.myaquarium.ui.profile.edit.EditFragment;
import com.example.myaquarium.ui.profile.settings.SettingsFragment;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        TextView profile_username = root.findViewById(R.id.profile_username);
        ImageView profile_userpic = root.findViewById(R.id.page_image);

        //TODO: Сделать кликабельным и выводить инфу о левелах
        ConstraintLayout profile_exp_bar_progress = root.findViewById(R.id.profile_exp_bar_progress);

        ConstraintLayout profile_aqua = root.findViewById(R.id.profile_aqua);
        ConstraintLayout profile_fish = root.findViewById(R.id.profile_fish);
        ConstraintLayout profile_plant = root.findViewById(R.id.profile_plant);

        TextView profile_aqua_num = root.findViewById(R.id.profile_aqua_num);
        TextView profile_aqua_text = root.findViewById(R.id.profile_aqua_text);

        TextView profile_fish_num = root.findViewById(R.id.profile_fish_num);
        TextView profile_fish_text = root.findViewById(R.id.profile_fish_text);

        TextView profile_plant_num = root.findViewById(R.id.profile_plant_num);
        TextView profile_plant_text = root.findViewById(R.id.profile_plant_text);

        Button profile_sv_1 = root.findViewById(R.id.profile_sv_1);
        Button profile_sv_2 = root.findViewById(R.id.profile_sv_2);
        Button profile_sv_3 = root.findViewById(R.id.profile_sv_3);

        int count_a = myPreferences.getInt("COUNT_A", 0);
        int count_fish = myPreferences.getInt("COUNT_FISH", 0);
        int count_plant = myPreferences.getInt("COUNT_PLANT", 0);

        if (!myPreferences.getString(
                "USERPIC",
                "android.resource://com.example.myaquarium/drawable/userpic")
                .equals("android.resource://com.example.myaquarium/drawable/userpic")) {
            profile_userpic.setScaleType(ImageView.ScaleType.CENTER_CROP);
            profile_userpic.setTranslationY(0);
            profile_userpic.setImageURI(Uri.parse(myPreferences.getString(
                    "USERPIC",
                    "android.resource://com.example.myaquarium/drawable/userpic")));
        }
        profile_username.setText(myPreferences.getString("USERNAME", "Новый пользователь"));

        ResizeAnimation resizeAnimation = new ResizeAnimation(profile_exp_bar_progress, 250);
        resizeAnimation.setDuration(1000);
        profile_exp_bar_progress.startAnimation(resizeAnimation);

        profile_aqua_num.setText(String.valueOf(count_a));
        profile_aqua_text.setText(this.getResources().getQuantityText(R.plurals.aqua, count_a));

        profile_fish_num.setText(String.valueOf(count_fish));
        profile_fish_text.setText(this.getResources().getQuantityText(R.plurals.fish, count_fish));

        profile_plant_num.setText(String.valueOf(count_plant));
        profile_plant_text.setText(this.getResources().getQuantityText(R.plurals.plant, count_plant));

        profile_aqua.setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new HomeFragment())
                        .commit());

        profile_fish.setOnClickListener(v -> {
            Toast.makeText(
                    getContext(),
                    "Это покажет рыбок и их количество (наверно)",
                    Toast.LENGTH_SHORT).show();

            ViewGroup.LayoutParams lp = profile_exp_bar_progress.getLayoutParams();
            int w = lp.width;
            w -= 50;
            ResizeAnimation resize = new ResizeAnimation(profile_exp_bar_progress, w);
            resize.setDuration(250);
            profile_exp_bar_progress.startAnimation(resize);
        });

        profile_plant.setOnClickListener(v -> {
            Toast.makeText(
                    getContext(),
                    "Это покажет растения и их количество (наверно)",
                    Toast.LENGTH_SHORT).show();

            ViewGroup.LayoutParams lp = profile_exp_bar_progress.getLayoutParams();
            int w = lp.width;
            w += 50;
            ResizeAnimation resize = new ResizeAnimation(profile_exp_bar_progress, w);
            resize.setDuration(250);
            profile_exp_bar_progress.startAnimation(resize);
        });

        profile_sv_1.setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new EditFragment())
                        .addToBackStack("Profile")
                        .commit());

        profile_sv_2.setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new SettingsFragment())
                        .addToBackStack("Profile")
                        .commit());

        profile_sv_3.setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new AboutFragment())
                        .addToBackStack("Profile")
                        .commit());

        return root;
    }
}