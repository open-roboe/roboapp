package it.halb.roboapp.ui.main;

import static it.halb.roboapp.util.Constants.LOCATION_PRIORITY;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.snackbar.Snackbar;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.SuccessCallback;
import it.halb.roboapp.databinding.FragmentCreateRegattaBinding;
import it.halb.roboapp.databinding.FragmentEditRegattaBinding;
import it.halb.roboapp.util.Constants;
import it.halb.roboapp.util.Permissions;

public class EditRegattaFragment extends Fragment {
    private FragmentEditRegattaBinding binding;

    private MaterialButtonToggleGroup regattaTypeSegmentedButton;

    private Button editRegattaButton;

    private EditRegattaViewModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditRegattaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize viewmodel
        model = new ViewModelProvider(this).get(EditRegattaViewModel.class);



        model.setBundle(getArguments());

        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setEditRegattaViewModel(model);

        //binding.dropdownMenu.setText("Single", false);

        //ask for location permissions. we notify the viewModel the result so that a
        // manual coordinates input can be created in case of denied permissions
        Permissions.manageLocationPermissions(
                this,
                //location permissions granted
                () -> {
                    setCurrentLocation(v->{});
                    model.setLocationPermissions(true);
                },
                //location permissions denied
                () -> model.setLocationPermissions(false)
        );

        //model.getFormFields().observe(getViewLifecycleOwner(), error -> {});

        regattaTypeSegmentedButton = binding.toggleButton;
        editRegattaButton = binding.buttonEditRegatta;

        if(getArguments().get(getString(R.string.type)).equals(Constants.triangleRegatta)) {
            regattaTypeSegmentedButton.check(R.id.buttonTriangle);
        }
        else {
            regattaTypeSegmentedButton.check(R.id.buttonStick);
        }

        model.getFormFields().getValue().get(getString(R.string.regatta_buoy_stern)).observe(getViewLifecycleOwner(), value -> {
            if (value != null) {
                binding.dropdownMenu.setText(value, false);
            }
        });

        model.getFormFieldsErrors().getValue().get(getString(R.string.regatta_name_error)).observe(getViewLifecycleOwner(), error -> {
            binding.textInputRegattaName.setError(error);
        });

        model.getFormFieldsErrors().getValue().get(getString(R.string.regatta_course_axis_error)).observe(getViewLifecycleOwner(), error -> {
            binding.textInputRegattaCourseAxis.setError(error);
        });

        model.getFormFieldsErrors().getValue().get(getString(R.string.regatta_course_length_error)).observe(getViewLifecycleOwner(), error -> {
            binding.textInputRegattaCourseLength.setError(error);
        });

        model.getFormFieldsErrors().getValue().get(getString(R.string.regatta_start_line_length_error)).observe(getViewLifecycleOwner(), error -> {
            binding.textInputRegattaStartLineLength.setError(error);
        });

        model.getFormFieldsErrors().getValue().get(getString(R.string.regatta_stacchetto_distance_error)).observe(getViewLifecycleOwner(), error -> {
            binding.textInputRegattaStacchettoDistance.setError(error);
        });

        model.getFormFieldsErrors().getValue().get(getString(R.string.regatta_bolina_distance_error)).observe(getViewLifecycleOwner(), error -> {
            binding.textInputRegattaBolinaDistance.setError(error);
        });

        model.getFormFieldsErrors().getValue().get(getString(R.string.regatta_buoy_stern_error)).observe(getViewLifecycleOwner(), error -> {
            binding.textInputRegattaBuoyStern.setError(error);
        });


        model.getEnableStacchettoDistance().observe(getViewLifecycleOwner(), enable -> {
            if (!binding.materialSwitchStacchetto.isChecked())
                binding.textInputRegattaStacchettoDistance.getEditText().setText("");
        });

        model.getEnableBolinaDistance().observe(getViewLifecycleOwner(), enable -> {
            if (!binding.materialSwitchBolina.isChecked())
                binding.textInputRegattaBolinaDistance.getEditText().setText("");
        });

        regattaTypeSegmentedButton.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                model.onRegattaTypeChanged(group.indexOfChild(view.findViewById(checkedId)));
            }
        });

        editRegattaButton.setOnClickListener(v -> {
            //set the current location, then create the regatta
            setCurrentLocation(
                    vv -> editRegatta()
            );

        });

        binding.buttonCancel.setOnClickListener(v -> {
            //go back to the previous page. This is practically like pressing the back button
            NavHostFragment.findNavController(this).popBackStack();
        });
        binding.topAppBar.setNavigationOnClickListener(v -> {
            NavHostFragment.findNavController(this).popBackStack();
        });



    }

    @SuppressLint("MissingPermission") //this lint rule is kinda broken, we are checking for permission
    public void setCurrentLocation(@NonNull SuccessCallback<Void> callback){
        if(Permissions.hasLocationPermissions(requireActivity())){
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
            fusedLocationClient.getCurrentLocation(LOCATION_PRIORITY, null)
                    .addOnSuccessListener(location -> {
                        if (location != null){
                            model.setCurrentLocation(location.getLatitude(), location.getLongitude());
                            callback.success(null);
                        }
                    })
                    .addOnFailureListener(v -> callback.success(null));
        }
        else{
            callback.success(null);
        }
    }

    public void editRegatta(){
        model.editRegatta(
                //creation success
                regattaName -> {
                    NavHostFragment.findNavController(this)
                            .navigate(EditRegattaFragmentDirections.actionEditRegattaFragmentToRunRegattaFragment(regattaName));
                },
                //creation error
                (code, details) -> {
                    String error = getString(R.string.create_regatta_creation_generic_error);
                    error += code +  " " + details;
                    Snackbar.make(this.getView(), error, Snackbar.LENGTH_SHORT)
                            .show();
                }
        );
    }

}


