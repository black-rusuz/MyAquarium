package com.example.myaquarium.ui.home.aquarium.fish;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FishViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FishViewModel() {
    }

    public LiveData<String> getText() {
        return mText;
    }
}