package it.halb.roboapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import it.halb.roboapp.dataLayer.AuthRepository;

public class LoginViewModel extends AndroidViewModel {
    private final MutableLiveData<String> username = new MutableLiveData<>("");
    private final MutableLiveData<String> password = new MutableLiveData<>("");

    private final MutableLiveData<String> usernameError = new MutableLiveData<>("");
    private final MutableLiveData<String> passwordError = new MutableLiveData<>("");

    private boolean loading = false;

    private final AuthRepository authRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
    }

    public LiveData<String> getUsernameError(){
        return usernameError;
    }
    public LiveData<String> getPasswordError(){
        return passwordError;
    }

    public LiveData<String> getUsername(){
        return username;
    }
    public LiveData<String> getPassword(){
        return password;
    }

    public void usernameTextChanged(CharSequence s, int start, int before, int count){
        username.setValue(s.toString());
        resetErrors();
    }
    public void passwordTextChanged(CharSequence s, int start, int before, int count){
        password.setValue(s.toString());
        resetErrors();
    }

    public boolean isLoading(){
        return loading;
    }

    public void login(){
        if(loading) return;
        resetErrors();
        if(validateForm(username.getValue(), password.getValue())){
            //set loading state
            //authRepository.login(username.getValue(), password.getValue());
            //callback: remove loading state, and eventually set success
        }
    }

    private boolean validateForm(@Nullable String username, @Nullable String password){
        boolean isValid = false;
        //handle null data
        if(username == null) username = "";
        if(password == null) password = "";
        //validate the data
        if(username.length() < 1){
            usernameError.setValue(getApplication().getString(R.string.login_form_validation_username_required));
        }
        else if(password.length() < 1){
            passwordError.setValue(getApplication().getString(R.string.login_form_validation_password_required));
        }
        else if(password.length() < 8){
            passwordError.setValue(getApplication().getString(R.string.login_form_validation_password_too_short));
        }else{
            isValid = true;
        }
        return isValid;
    }

    private void resetErrors(){
        usernameError.setValue(null);
        passwordError.setValue(null);
    }


}
