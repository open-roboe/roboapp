package it.halb.roboapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> username = new MutableLiveData<>("");
    public MutableLiveData<String> password = new MutableLiveData<>("");
}
