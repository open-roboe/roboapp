package it.halb.roboapp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.RunningRegattaRepository;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Roboa;
import it.halb.roboapp.databinding.FragmentBindBoaAndRoboaBinding;
import it.halb.roboapp.databinding.FragmentBoatInfoBinding;
import it.halb.roboapp.ui.main.MapViewModel;
import it.halb.roboapp.ui.main.adapters.BuoyListSimpleAdapter;

public class BindBoaAndRoboa extends Fragment {
    private FragmentBindBoaAndRoboaBinding binding;
    private RunningRegattaRepository repository;
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

        //da questa lista si seleziona la boa da bindare
        binding.buoysListViewInBindFragment.setOnItemClickListener((parent, view1, position, id) -> {
            selectedBuoy = adapter.getItemAt(position);

            //se la boa è libera procediamo a bindarla
            if(selectedBuoy.getBindedRobuoy() == null){
                selectedBuoy.setBindedRobuoy(currentRoboa.getId() + "");
                currentRoboa.setBindedBuoy(selectedBuoy.getId());
                model.updateBindedBuoy(selectedBuoy);
                NavHostFragment.findNavController(this).navigate
                        (BindBoaAndRoboaDirections.actionBindBoaAndRoboaFragmentToRoboaInfoFragment());
            }
            //altrimenti mandiamo un messaggio di errore
            else{
                Toast.makeText(this.getContext(), "This buoy is already binded to a robouy!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}