package it.halb.roboapp.ui.main;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.halb.roboapp.R;

public class MainMapFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavHostFragment navHostFragment = (NavHostFragment) this.getActivity().getSupportFragmentManager().
                findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = this.getActivity().findViewById(R.id.bottom_navigation);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.mapFragment, R.id.buoyInfo,
                R.id.robuoyInfo, R.id.boatInfo).build();


        // For the BottomNavigationView
        NavigationUI.setupWithNavController(bottomNav, navController);
    }
}