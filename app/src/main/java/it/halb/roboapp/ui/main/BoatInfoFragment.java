package it.halb.roboapp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.halb.roboapp.dataLayer.localDataSource.Boat;
import it.halb.roboapp.databinding.FragmentBoatInfoBinding;
import it.halb.roboapp.ui.main.adapters.BoatsListSimpleAdapter;


public class BoatInfoFragment extends Fragment {
    private FragmentBoatInfoBinding binding;
    private MapViewModel model;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoatInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ViewModel initialization
        model = new ViewModelProvider(this).get(MapViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setMapViewModel(model);

        //listview initialization
        BoatsListSimpleAdapter adapter = new BoatsListSimpleAdapter(requireContext(), new ArrayList<>());
        binding.boatsListView.setAdapter(adapter);
        binding.boatsListView.setOnItemClickListener((parent, view1, position, id) -> {
            Boat b = adapter.getItemAt(position);
            Log.d("CLICK", "clicked " + b.getUsername());
        });

        //update listview with livedata.
        //this list will always be short, and will update sporadically. No need for fancy recyclerViews
        model.getBoats().observe(getViewLifecycleOwner(), boats -> {
            adapter.clear();
            adapter.addAll(boats);
            adapter.notifyDataSetChanged();
        });

    }

    public void handleBoatClick(Boat b){
        model.setTarget(b);
    }
}