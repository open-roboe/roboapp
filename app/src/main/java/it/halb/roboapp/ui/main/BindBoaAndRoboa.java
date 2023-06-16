package it.halb.roboapp.ui.main;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;
import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.RegattaRepository;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Roboa;
import it.halb.roboapp.databinding.FragmentBindBoaAndRoboaBinding;
import it.halb.roboapp.ui.main.adapters.BuoyListSimpleAdapter;

public class BindBoaAndRoboa extends Fragment {
    private FragmentBindBoaAndRoboaBinding binding;
    private RegattaRepository repository;
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
        repository = new RegattaRepository(requireActivity().getApplication());
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setMapViewModel(model);

        //set the adapter
        BuoyListSimpleAdapter adapter = new BuoyListSimpleAdapter(requireContext(), new ArrayList<>());
        binding.buoysListViewInBindFragment.setAdapter(adapter);

        //i get the chosen roboa and set the textViews
        model.getCurrentRoboa().observe(getViewLifecycleOwner(), roboa -> {
            currentRoboa = roboa;
            binding.textView5.setText("picked robuoy: " + currentRoboa.getName() + ", with id: " + currentRoboa.getId());
            binding.textView6.setText("(Lat: " + currentRoboa.getLongitude() + " Lon: " + currentRoboa.getLongitude() + ")");
        });

        //i get the list of buoys and set the adapter
        model.getBuoy().observe(getViewLifecycleOwner(), buoy -> {
            adapter.clear();
            adapter.addAll(buoy);
            adapter.notifyDataSetChanged();
        });

        //this is the list the chosen buoy is selected from
        binding.buoysListViewInBindFragment.setOnItemClickListener((parent, view1, position, id) -> {
            selectedBuoy = adapter.getItemAt(position);

            //if the buoy is free we bind it to the roboa and notify the server
            if(selectedBuoy.getBindedRobuoy() == null){
                selectedBuoy.setBindedRobuoy(currentRoboa.getId() + "");
                currentRoboa.setBindedBuoy(selectedBuoy.getId());
                model.updateBindedBuoy(selectedBuoy);
                repository.updateRoboa(currentRoboa);
                NavHostFragment.findNavController(this).navigate
                        (BindBoaAndRoboaDirections.actionBindBoaAndRoboaFragmentToRoboaInfoFragment());
            }
            //otherwise we notify the user
            else{
                Toast.makeText(this.getContext(), "This buoy is already binded to a robouy!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}