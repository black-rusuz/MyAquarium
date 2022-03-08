package ru.sfedu.myaquarium.ui.profile.edit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.sfedu.myaquarium.R;

public class EditFragment extends Fragment {
    private Uri image = null;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_edit, container, false);
        root.findViewById(R.id.edit_back).setOnClickListener(v -> getParentFragmentManager().popBackStack());

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor myEditor = myPreferences.edit();

        ImageView edit_userpic = root.findViewById(R.id.edit_userpic);
        TextView edit_userpic_key = root.findViewById(R.id.edit_userpic_key);
        EditText edit_name = root.findViewById(R.id.edit_name);
        Button edit_save = root.findViewById(R.id.edit_save);

        if (!myPreferences.getString(
                "USERPIC",
                "android.resource://ru.sfedu.myaquarium/drawable/userpic")
                .equals("android.resource://ru.sfedu.myaquarium/drawable/userpic")) {
            edit_userpic.setScaleType(ImageView.ScaleType.CENTER_CROP);
            edit_userpic.setTranslationY(0);
            edit_userpic.setImageURI(Uri.parse(myPreferences.getString(
                    "USERPIC",
                    "android.resource://ru.sfedu.myaquarium/drawable/userpic")));
        }
        edit_name.setText(myPreferences.getString("USERNAME", null));

        Intent imageIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        imageIntent.setType("image/*");

        // TODO: Добавить нормальный выбор авы c обрезкой
        edit_userpic.setOnClickListener(v ->  {
            startActivityForResult(imageIntent, 1);
        });
        edit_userpic_key.setOnClickListener(v ->  {
            startActivityForResult(imageIntent, 1);
        });

        edit_save.setOnClickListener(v -> {
            if (edit_name.getText().length() > 0)
                myEditor.putString("USERNAME", String.valueOf(edit_name.getText())).apply();

            if (image != null) {
                myEditor.putString("USERPIC", image.toString()).apply();
            }

            getParentFragmentManager().popBackStack();
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent returnedImageIntent) {
        super.onActivityResult(requestCode, resultCode, returnedImageIntent);

        if (returnedImageIntent != null) {
            image = returnedImageIntent.getData();
            getContext().getContentResolver().takePersistableUriPermission(
                    image,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ImageView edit_userpic = root.findViewById(R.id.edit_userpic);

        if (image != null) {
            edit_userpic.setImageURI(image);
            edit_userpic.setScaleType(ImageView.ScaleType.CENTER_CROP);
            edit_userpic.setTranslationY(0);
        }
    }
}