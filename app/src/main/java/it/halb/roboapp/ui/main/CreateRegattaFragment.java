package it.halb.roboapp.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButtonToggleGroup;

import it.halb.roboapp.databinding.FragmentCreateRegattaBinding;

public class CreateRegattaFragment extends Fragment {
    private FragmentCreateRegattaBinding binding;

    private MaterialButtonToggleGroup regattaTypeSegmentedButton;

    private Button createRegattaButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateRegattaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CreateRegattaViewModel model = new ViewModelProvider(this).get(CreateRegattaViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setCreateRegattaViewModel(model);

        regattaTypeSegmentedButton = binding.toggleButton;
        createRegattaButton = binding.buttonCreateRegatta;

        model.getFormFieldsErrors().getValue().get("regattaNameError").observe(getViewLifecycleOwner(), error -> {
            binding.textInputRegattaName.setError(error);
        });

        model.getFormFieldsErrors().getValue().get("courseAxisError").observe(getViewLifecycleOwner(), error -> {
            binding.textInputRegattaCourseAxis.setError(error);
        });

        model.getFormFieldsErrors().getValue().get("courseLengthError").observe(getViewLifecycleOwner(), error -> {
            binding.textInputRegattaCourseLength.setError(error);
        });

        model.getFormFieldsErrors().getValue().get("startLineLengthError").observe(getViewLifecycleOwner(), error -> {
            binding.textInputRegattaStartLineLength.setError(error);
        });

        model.getFormFieldsErrors().getValue().get("stacchettoDistanceError").observe(getViewLifecycleOwner(), error -> {
            binding.textInputRegattaStacchettoDistance.setError(error);
        });

        model.getFormFieldsErrors().getValue().get("bolinaDistanceError").observe(getViewLifecycleOwner(), error -> {
            binding.textInputRegattaBolinaDistance.setError(error);
        });

        model.getFormFieldsErrors().getValue().get("buoySternError").observe(getViewLifecycleOwner(), error -> {
            binding.textInputRegattaBuoyStern.setError(error);
        });

        model.getEnableStacchettoDistance().observe(getViewLifecycleOwner(), enable -> {
            binding.textInputRegattaStacchettoDistance.getEditText().setText("");
        });

        model.getEnableBolinaDistance().observe(getViewLifecycleOwner(), enable -> {
            binding.textInputRegattaBolinaDistance.getEditText().setText("");
        });

        regattaTypeSegmentedButton.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                Log.d("CreateRegattaFragment", "onViewCreated: " + checkedId);
                model.onRegattaTypeChanged(group.indexOfChild(view.findViewById(checkedId)));
            }
        });

        createRegattaButton.setOnClickListener(v -> {
            model.createRegatta(
                    //creation success
                    regattaName -> {
                        NavHostFragment.findNavController(this)
                                .navigate(CreateRegattaFragmentDirections.actionCreateRegattaFragmentToRunRegattaFragment(regattaName));
                    },
                    //creation error
                    (code, details) -> {
                        //TODO: display error
                    }
            );
        });

        binding.buttonCancel.setOnClickListener(v -> {
            //go back to the previous page. This is practically like pressing the back button
            NavHostFragment.findNavController(this).popBackStack();
        });


    }
}

