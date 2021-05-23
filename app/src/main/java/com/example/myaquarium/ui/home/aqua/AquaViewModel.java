package com.example.myaquarium.ui.home.aqua;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AquaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AquaViewModel() {
    }

    public LiveData<String> getText() {
        return mText;
    }
}