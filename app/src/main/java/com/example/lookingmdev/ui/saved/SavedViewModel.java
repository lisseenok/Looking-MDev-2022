package com.example.lookingmdev.ui.saved;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SavedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SavedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("this is fragment of the saved hotels");
    }

    public LiveData<String> getText() {
        return mText;
    }
}