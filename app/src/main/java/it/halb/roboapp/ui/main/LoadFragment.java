package it.halb.roboapp.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;


import it.halb.roboapp.databinding.FragmentLoadBinding;
import it.halb.roboapp.util.Permissions;

public class LoadFragment extends Fragment {

    private FragmentLoadBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoadBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //viewmodel initialization
        LoadViewModel model = new ViewModelProvider(this).get(LoadViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setLoadViewModel(model);

        //permissions management
        Permissions.manageLocationPermissions(
                this,
                //permissions granted
                this::startFollow,
                //permission denied
                () -> {
                    binding.permissionsLayout.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                }
        );

        //view listeners
        binding.buttonBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).popBackStack();
        });
        binding.buttonEditPermissions.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", requireActivity().getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        });

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
