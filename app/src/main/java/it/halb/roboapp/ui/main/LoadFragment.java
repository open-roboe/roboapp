package it.halb.roboapp.ui.main;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import it.halb.roboapp.R;
import it.halb.roboapp.util.Permissions;

public class LoadFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_load, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //the permissions we need
        String[] PERMISSIONS = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        };

        // Register the permissions callback, which handles the user's response to the system permissions dialog
        //https://stackoverflow.com/a/68347506/9169799
        ActivityResultLauncher<String[]> requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                    boolean areAllGranted = true;
                    for(Boolean b : result.values()) {
                        areAllGranted = areAllGranted && b;
                    }

                    if(areAllGranted) {
                        startFollow();
                    }
                });

        //check if we have the permissions we need
        if(Permissions.hasPermission(requireActivity(), PERMISSIONS)){
            //all good, launch service
            startFollow();
        }
        else{
            //ask permissions
            requestPermissionLauncher.launch(PERMISSIONS);
        }

    }


    /**
     * start the regatta follow service, navigate to maps activity.
     *
     * Careful: Call this method only when the app has access to all the permissions required
     */
    public void startFollow(){
        //launch service

        //navigate no map activity, without navhistory
            //https://stackoverflow.com/questions/50514758/how-to-clear-navigation-stack-after-navigating-to-another-fragment-in-android
            //NavHostFragment.findNavController(this).navigate();

    }
}
