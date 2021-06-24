package com.example.myaquarium.ui.home.aquarium;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AquariumViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AquariumViewModel() {
    }

    public LiveData<String> getText() {
        return mText;
    }
}