package it.halb.roboapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import it.halb.roboapp.dataLayer.localDataSource.Database;

public class Permissions {

    @FunctionalInterface
    public interface GrantedCallback{
        void run();
    }
    @FunctionalInterface
    public interface DeniedCallback{
        void run();
    }

    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    /**
     * Use this method before running any logic that requires access to the user location.
     *
     * @param fragment a reference to the fragment that is calling this method
     * @param grantedCallback callback that will run if the user granted permissions
     * @param deniedCallback callback that will run if the user denied the permissions
     */
    public static void manageLocationPermissions(Fragment fragment, GrantedCallback grantedCallback, DeniedCallback deniedCallback){
        // Register the permissions callback, which handles the user's response to the system permissions dialog
        //https://stackoverflow.com/a/68347506/9169799
        ActivityResultLauncher<String[]> requestPermissionLauncher =
                fragment.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                    boolean areAllGranted = true;
                    for(Boolean b : result.values()) {
                        areAllGranted = areAllGranted && b;
                    }
                    if(areAllGranted)
                        grantedCallback.run();
                    else
                        deniedCallback.run();
                });

        //check if we have the permissions we need
        if(Permissions.hasLocationPermissions(fragment.requireActivity())){
            grantedCallback.run();
        }
        else{
            //ask permissions
            requestPermissionLauncher.launch(PERMISSIONS);
        }

    }

    public static boolean hasLocationPermissions(Context activity){
        return Permissions.hasPermission(activity, PERMISSIONS);
    }

    private static boolean hasPermission(@NonNull Context activity, @NonNull String... PERMISSIONS){
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
