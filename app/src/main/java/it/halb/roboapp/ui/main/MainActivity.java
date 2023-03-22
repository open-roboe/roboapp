package it.halb.roboapp.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.halb.roboapp.R;
import it.halb.roboapp.ui.auth.AuthActivity;

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
                finish();
            }
            //If no auth is required, launch main application by inflating the view
            else{
                //inflate view
                setContentView(R.layout.activity_main);
                //setup bottom bar
                NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                        findFragmentById(R.id.fragmentContainerView);
                if(navHostFragment == null) return;
                BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
                NavController navController = navHostFragment.getNavController();
                NavigationUI.setupWithNavController(bottomNav, navController);
                //handle bottom bar visibility: only some fragments need it
                //https://stackoverflow.com/questions/56215403/how-to-hide-bottom-nav-bar-in-fragment
                navController.addOnDestinationChangedListener(
                        (navController1, navDestination, bundle) -> {
                            //put here the fragments you want to have a bottomNavbar. The id
                            //is the id of the fragment defined in the navigation_main.xml file
                            int id = navDestination.getId();
                            if( id == R.id.mapFragment ||
                                    id == R.id.buoyInfoFragment ||
                                    id == R.id.boatInfoFragment ||
                                    id == R.id.roboaInfoFragment ){
                                bottomNav.setVisibility(View.VISIBLE);
                            }
                            else{
                                bottomNav.setVisibility(View.INVISIBLE);
                            }
                        }
                );

            }
        });
    }
}