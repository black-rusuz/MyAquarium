package com.example.myaquarium.ui.help.page;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PageViewModel() {
    }

    public LiveData<String> getText() {
        return mText;
    }
}