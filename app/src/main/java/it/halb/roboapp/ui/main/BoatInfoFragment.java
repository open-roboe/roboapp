package it.halb.roboapp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.halb.roboapp.databinding.FragmentBoatInfoBinding;


public class BoatInfoFragment extends Fragment {


    private FragmentBoatInfoBinding binding;

    public BoatInfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoatInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ViewModel initialization
        BoatInfoViewModel model = new ViewModelProvider(this).get(BoatInfoViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setBoatInfoViewModel(model);

        binding.boatsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));




    }
}