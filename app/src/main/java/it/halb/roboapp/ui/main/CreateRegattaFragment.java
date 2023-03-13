package it.halb.roboapp.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import it.halb.roboapp.databinding.FragmentCreateRegattaBinding;

public class CreateRegattaFragment extends Fragment {
    private FragmentCreateRegattaBinding binding;

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
    }
}

