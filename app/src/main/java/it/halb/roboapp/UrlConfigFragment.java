package it.halb.roboapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import it.halb.roboapp.databinding.FragmentUrlConfigBinding;

public class UrlConfigFragment extends BottomSheetDialogFragment {
    private FragmentUrlConfigBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate the layout for this fragment, and save binding
        binding = FragmentUrlConfigBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ViewModel initialization
        UrlConfigViewModel model = new ViewModelProvider(this).get(UrlConfigViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setUrlConfigViewModel(model);

        //viewModel listeners
        model.getUrlError().observe(getViewLifecycleOwner(), error ->{
            binding.textInputUrl.setError(error);
        });

        //view listeners
        binding.buttonApplyChanges.setOnClickListener(v -> {
            if(model.save())
                this.dismiss();
        });
        binding.buttonCancel.setOnClickListener(v -> {
            this.dismiss();
        });
    }
}
