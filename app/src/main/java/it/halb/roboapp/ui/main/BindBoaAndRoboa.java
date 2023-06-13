package it.halb.roboapp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Roboa;
import it.halb.roboapp.databinding.FragmentBindBoaAndRoboaBinding;
import it.halb.roboapp.databinding.FragmentBoatInfoBinding;
import it.halb.roboapp.ui.main.MapViewModel;
import it.halb.roboapp.ui.main.adapters.BuoyListSimpleAdapter;

public class BindBoaAndRoboa extends Fragment {
    private FragmentBindBoaAndRoboaBinding binding;
    private MapViewModel model;
    private Roboa currentRoboa;
    private Buoy selectedBuoy;

    public BindBoaAndRoboa() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBindBoaAndRoboaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavBackStackEntry store = NavHostFragment.findNavController(this).getBackStackEntry(R.id.main_navigation);
        model = new ViewModelProvider(store).get(MapViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setMapViewModel(model);

        BuoyListSimpleAdapter adapter = new BuoyListSimpleAdapter(requireContext(), new ArrayList<>());
        binding.buoysListViewInBindFragment.setAdapter(adapter);

        model.getCurrentRoboa().observe(getViewLifecycleOwner(), roboa -> {
            currentRoboa = roboa;
            binding.textView5.setText("Roboa corrente: " + currentRoboa.getName() + ", con id: " + currentRoboa.getId());
            binding.textView6.setText("(Lat: " + currentRoboa.getLongitude() + " Lon: " + currentRoboa.getLongitude() + ")");
        });

        model.getBuoy().observe(getViewLifecycleOwner(), buoy -> {
            adapter.clear();
            adapter.addAll(buoy);
            adapter.notifyDataSetChanged();
        });

        binding.buoysListViewInBindFragment.setOnItemClickListener((parent, view1, position, id) -> {
            selectedBuoy = adapter.getItemAt(position);
            Log.d("BindBoaAndRoboa", "selectedBuoy: " + selectedBuoy.getId());
        });


    }
}