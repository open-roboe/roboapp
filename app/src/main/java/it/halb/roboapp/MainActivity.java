package it.halb.roboapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainViewModel model = new ViewModelProvider(this).get(MainViewModel.class);
        model.getAccount().observe(this, account -> {
            //If there is no account, redirect to auth activity
            if(account == null){
                Intent intent = new Intent(this, AuthActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
            //If no auth is required, and this is the first time the code runs, launch main application
            else if(!model.isFirstLaunch()){
                model.setLaunched();
                setContentView(R.layout.activity_main);
            }
        });
    }
}