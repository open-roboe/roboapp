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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Boat;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.databinding.FragmentBuoyInfoBinding;
import it.halb.roboapp.ui.main.adapters.BuoyListSimpleAdapter;


public class BuoyInfoFragment extends Fragment {
    private FragmentBuoyInfoBinding binding;
    private MapViewModel model;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBuoyInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ViewModel initialization
        model = new ViewModelProvider(requireActivity()).get(MapViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setMapViewModel(model);
        //TEST
        //TODO: remove
        Log.d("VIEWMODEL_SCOPING_TEST", " " + model.TEST);

        //listview initialization
        BuoyListSimpleAdapter adapter = new BuoyListSimpleAdapter(requireContext(), new ArrayList<>());
        binding.buoysListView.setAdapter(adapter);

        //Listen to clicks on the listview
        binding.buoysListView.setOnItemClickListener((parent, view1, position, id) -> {
            //set the current buoy as a target for the navigation
            Buoy buoy = adapter.getItemAt(position);
            model.setTarget(buoy);
            //simulate a click on the bottomNavigation map button
            BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottomNavigation);
            bottomNav.setSelectedItemId(R.id.mapFragment);
        });

        //update listview with livedata.
        //this list will always be short, and will update sporadically. No need for fancy recyclerViews
        model.getBuoy().observe(getViewLifecycleOwner(), buoy -> {
            adapter.clear();
            adapter.addAll(buoy);
            adapter.notifyDataSetChanged();
        });

    }
}