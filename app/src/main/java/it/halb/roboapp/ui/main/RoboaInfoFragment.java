package it.halb.roboapp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.halb.roboapp.databinding.FragmentRobuoyInfoBinding;


public class RoboaInfoFragment extends Fragment {


    private FragmentRobuoyInfoBinding binding;

    public RoboaInfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRobuoyInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ViewModel initialization
        MapViewModel model = new ViewModelProvider(requireActivity()).get(MapViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setMapViewModel(model);
    }
}