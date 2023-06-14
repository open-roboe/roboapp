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

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Roboa;
import it.halb.roboapp.databinding.FragmentManageRobuoyBinding;
import it.halb.roboapp.util.BuoyFactory;


public class ManageRobuoyFragment extends Fragment {

    private MapViewModel model;
    private FragmentManageRobuoyBinding binding;

    private Roboa currentRoboa;


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
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setMapViewModel(model);


        model.getCurrentRoboa().observe(getViewLifecycleOwner(), roboa -> {
            currentRoboa = roboa;

            //initialization of the textviews
            binding.textView16.setText("Robuoy: " + currentRoboa.getName() + ", con id: " + currentRoboa.getId());
            binding.textView9.setText("- status: " + currentRoboa.getStatus());
            binding.textView10.setText("- estimated time arriving: " + currentRoboa.getEta());
            binding.textView11.setText(" - (Lat" + currentRoboa.getLatitude() + " Lon: " + currentRoboa.getLongitude() + ")");
            binding.textView12.setText("Buoy currently binded: " + currentRoboa.getBindedBuoy());

            if(currentRoboa.isActive()){
                binding.textView14.setText("Stop the robuoy  ");
                binding.buttonGOSTOP.setText("STOP");
            }
            else {
                binding.textView14.setText("Start the robuoy  ");
                binding.buttonGOSTOP.setText("GO");
            }
        });

        //facciamo partire/fermare la robuoy
        binding.buttonGOSTOP.setOnClickListener(v -> {
            if (binding.buttonGOSTOP.getText().equals("GO"))
                Log.d("ManageRobuoyFragment", "buttonGO pressed");
            else
                Log.d("ManageRobuoyFragment", "buttonSTOP pressed");
        });

        //scolleghiamo una boa da una roboa
        binding.buttonUNBIND.setOnClickListener(v -> {
            Log.d("ManageRobuoyFragment", "buttonUnbind pressed");
            Buoy tempBuoy = BuoyFactory.buoyFinder(model.getBuoy().getValue(), currentRoboa.getBindedBuoy());
            tempBuoy.setBindedRobuoy(null);
            currentRoboa.setBindedBuoy(null);
            Log.d("UNBIND", "now the buoy and the robuoy are unbinded");
        });



    }
}