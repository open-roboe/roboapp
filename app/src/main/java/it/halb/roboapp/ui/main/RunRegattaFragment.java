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


import it.halb.roboapp.RunningRegattaService;
import it.halb.roboapp.databinding.FragmentRunRegattaBinding;
import it.halb.roboapp.util.Permissions;

/**
 * This is the entry point for a running regatta
 * Before navigating to this fragment, make sure that you called declareRegattaToRun
 *
 */
public class RunRegattaFragment extends Fragment {

    private FragmentRunRegattaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRunRegattaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //require regattaName parameter, that is passed with every navigation
        String regattaName = RunRegattaFragmentArgs.fromBundle(getArguments()).getRunRegattaName();

        //viewModel initialization
        RunRegattaViewModel model = new ViewModelProvider(this).get(RunRegattaViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setLoadViewModel(model);

        //permissions management
        Permissions.manageLocationPermissions(
                this,
                //permissions granted
                () ->{
                    model.declareRegattaToRun(regattaName,
                            v -> startRunningRegattaService()
                            );
                },
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


    public void startRunningRegattaService(){
        requireActivity().startService(
                new Intent(requireActivity(), RunningRegattaService.class)
        );

        NavHostFragment.findNavController(this).navigate(
                RunRegattaFragmentDirections.actionLoadFragmentToMainMapFragment()
        );
    }
}
