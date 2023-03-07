package it.halb.roboapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import it.halb.roboapp.dataLayer.AuthRepository;
import it.halb.roboapp.dataLayer.localDataSource.Account;

public class LoginViewModel extends AndroidViewModel {
    private final MutableLiveData<String> username = new MutableLiveData<>("");
    private final MutableLiveData<String> password = new MutableLiveData<>("");

    private final MutableLiveData<String> usernameError = new MutableLiveData<>("");
    private final MutableLiveData<String> passwordError = new MutableLiveData<>("");
    private final MutableLiveData<String> genericError = new MutableLiveData<>("");

    private final LiveData<Account> account;

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    private final AuthRepository authRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        account = authRepository.getAccount();
    }

    public LiveData<Account> getAccount(){
        return account;
    }
    public LiveData<String> getUsernameError(){
        return usernameError;
    }
    public LiveData<String> getPasswordError(){
        return passwordError;
    }
    public LiveData<String> getGenericError() {
        return genericError;
    }
    public LiveData<String> getUsername(){
        return username;
    }
    public LiveData<String> getPassword(){
        return password;
    }

    public void usernameTextChanged(CharSequence s, int start, int before, int count){
        username.setValue(s.toString());
        resetDisplayedErrors();
    }
    public void passwordTextChanged(CharSequence s, int start, int before, int count){
        password.setValue(s.toString());
        resetDisplayedErrors();
    }

    public LiveData<Boolean> getCanClick(){
        return Transformations.map(loading, loading -> !loading);
    }


    public void login(){
        //ignore login button clicks when a login request is already in progress
        if(Boolean.TRUE.equals(loading.getValue())) return;
        //handle null data
        String usernameString = username.getValue();
        String passwordString = password.getValue();
        if(usernameString == null) usernameString = "";
        if(passwordString == null) passwordString = "";
        //start the login process
        resetDisplayedErrors();
        if(validateForm(usernameString, passwordString)){
            loading.setValue(true);
            authRepository.login(
                    usernameString,
                    passwordString,
                    // Handle login success
                    account -> {
                        loading.setValue(false);
                    },
                    // Handle login error
                    (code, details) -> {
                        loading.setValue(false);
                        handleLoginErrors(code, details);
                    }
            );

        }
    }

    private void handleLoginErrors(int code, String details){
        if(code == 400){
            usernameError.setValue(getApplication().getString(R.string.login_form_validation_invalid_credentials));
            passwordError.setValue(getApplication().getString(R.string.login_form_validation_invalid_credentials));
        }else{
            //We are not applying i18n to this string, this is not a best practice anyway
            String text = "Unexpected error: HTTP ";
            genericError.setValue(text + String.valueOf(code) + " " + details);
        }
    }

    private boolean validateForm(@NonNull String username, @NonNull String password){
        boolean isValid = false;
        //validate the data
        if(username.length() < 1){
            usernameError.setValue(getApplication().getString(R.string.login_form_validation_username_required));
        }
        else if(password.length() < 1){
            passwordError.setValue(getApplication().getString(R.string.login_form_validation_password_required));
        }
        else if(password.length() < 4){
            passwordError.setValue(getApplication().getString(R.string.login_form_validation_password_too_short));
        }else{
            isValid = true;
        }
        return isValid;
    }

    private void resetDisplayedErrors(){
        usernameError.setValue(null);
        passwordError.setValue(null);
        genericError.setValue(null);
    }


}
