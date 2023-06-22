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

import java.util.List;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.RegattaRepository;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Roboa;
import it.halb.roboapp.databinding.FragmentManageRobuoyBinding;
import it.halb.roboapp.util.BuoyFactory;


public class ManageRobuoyFragment extends Fragment {

    private MapViewModel model;
    private FragmentManageRobuoyBinding binding;
    private RegattaRepository repository;
    private Roboa currentRoboa;
    private List<Buoy> buoys;


    public ManageRobuoyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentManageRobuoyBinding.inflate(inflater, container, false);
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

        //i get the list of buoys
        model.getBuoys().observe(getViewLifecycleOwner(), buoy -> {
            buoys = buoy;
        });

        //initialization of the textviews
        model.getCurrentRoboa().observe(getViewLifecycleOwner(), roboa -> {
            currentRoboa = roboa;

            binding.textView16.setText("Robuoy: " + currentRoboa.getName() + ", con id: " + currentRoboa.getId());
            binding.textView9.setText("- status: " + currentRoboa.getStatus());
            binding.textView10.setText("- estimated time arriving: " + currentRoboa.getEta());
            binding.textView11.setText(" - (Lat" + currentRoboa.getLatitude() + " Lon: " + currentRoboa.getLongitude() + ")");
            binding.textView12.setText("Buoy currently binded: " + currentRoboa.getBindedBuoy());

            if(currentRoboa.getStatus().equals("moving")){
                binding.textView14.setText("Stop the robuoy  ");
                binding.buttonGOSTOP.setText("STOP");
            }
            else if(currentRoboa.getStatus().equals("not moving")){
                binding.textView14.setText("Start the robuoy  ");
                binding.buttonGOSTOP.setText("GO");
            }
        });

        //this stops/starts a robuoy
        binding.buttonGOSTOP.setOnClickListener(v -> {
            if (binding.buttonGOSTOP.getText().equals("GO")){
                Log.d("ManageRobuoyFragment", "buttonGO pressed");
                currentRoboa.setStatus("moving");
            }
            else{
                Log.d("ManageRobuoyFragment", "buttonSTOP pressed");
                currentRoboa.setStatus("not moving");
            }
            repository.updateRoboa(currentRoboa);
            NavHostFragment.findNavController(this).navigate
                    (ManageRobuoyFragmentDirections.actionManageRobuoyFragmentToRoboaInfoFragment());
        });

        //this unbinds a robuoy from a buoy and viceversa
        binding.buttonUNBIND.setOnClickListener(v -> {
            Buoy tempBuoy = BuoyFactory.buoyFinder(buoys, currentRoboa.getBindedBuoy());
            tempBuoy.setBindedRobuoy(null);
            currentRoboa.setBindedBuoy(null);
            model.updateBindedBuoy(tempBuoy);
            repository.updateRoboa(currentRoboa);
            NavHostFragment.findNavController(this).navigate
                    (ManageRobuoyFragmentDirections.actionManageRobuoyFragmentToRoboaInfoFragment());
        });
    }
}