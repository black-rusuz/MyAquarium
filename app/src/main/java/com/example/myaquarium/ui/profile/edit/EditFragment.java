package com.example.myaquarium.ui.profile.edit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myaquarium.R;
import com.example.myaquarium.ui.profile.ProfileFragment;

public class EditFragment extends Fragment {

    private EditViewModel editViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editViewModel =
                new ViewModelProvider(this).get(EditViewModel.class);
        View root = inflater.inflate(R.layout.fragment_edit, container, false);

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor myEditor = myPreferences.edit();

        root.findViewById(R.id.edit_back).setOnClickListener(v -> getFragmentManager().popBackStack());

        EditText edit_name = root.findViewById(R.id.edit_name);
        Button edit_userpic = root.findViewById(R.id.edit_userpic);
        Button edit_save = root.findViewById(R.id.edit_save);

        edit_name.setText(myPreferences.getString("USERNAME", "Джон Сноу"));

        //TODO: даже, блядь, не знаю что сказать...
        edit_userpic.setOnClickListener(v ->  {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 1);
        });

        edit_save.setOnClickListener(v -> {
            if (edit_name.getText().length() > 0)
                myEditor.putString("USERNAME", String.valueOf(edit_name.getText())).apply();

            getFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new ProfileFragment())
                    .commit();
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor myEditor = myPreferences.edit();

        if (imageReturnedIntent != null) {
            Uri selectedImage = imageReturnedIntent.getData();
            myEditor.putString("USERPIC", selectedImage.toString()).apply();
            Log.d("uri", myPreferences.getString("USERPIC", null));
            Log.d("uri", Uri.parse(myPreferences.getString("USERPIC", null)).toString());
        }
    }
}