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

import com.google.android.material.button.MaterialButtonToggleGroup;

import it.halb.roboapp.databinding.FragmentCreateRegattaBinding;

public class CreateRegattaFragment extends Fragment {

    private String name;

    private String courseAxis;

    private String courseLength;

    private String startLineLength;
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

        name = binding.textInputRegattaName.getEditText().getText().toString();
        courseAxis = binding.textInputRegattaCourseAxis.getEditText().getText().toString();
        courseLength = binding.textInputRegattaCourseLength.getEditText().getText().toString();
        startLineLength = binding.textInputRegattaStartLineLength.getEditText().getText().toString();
        regattaTypeSegmentedButton = binding.toggleButton;
        createRegattaButton = binding.buttonCreateRegatta;

        regattaTypeSegmentedButton.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                Log.d("CreateRegattaFragment", "onViewCreated: " + checkedId);
                model.onRegattaTypeChanged(group.indexOfChild(view.findViewById(checkedId)));
            }
        });

        createRegattaButton.setOnClickListener(v -> {
            model.createRegatta(name, Integer.parseInt(courseAxis), Double.parseDouble(courseLength), Double.parseDouble(startLineLength));
        });


    }
}

