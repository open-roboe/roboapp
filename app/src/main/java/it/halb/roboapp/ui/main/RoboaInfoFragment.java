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
import it.halb.roboapp.dataLayer.localDataSource.Roboa;
import it.halb.roboapp.databinding.FragmentRobuoyInfoBinding;
import it.halb.roboapp.ui.main.adapters.RoboaListSimpleAdapter;
import it.halb.roboapp.util.RoboeInitialization;



public class RoboaInfoFragment extends Fragment {
    private FragmentRobuoyInfoBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRobuoyInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // ViewModel initialization.
        // It is scoped to the navigation graph, so that it will be shared between all
        // the runningRegatta fragments and it will be cleared when navigating back to the regatta lists fragment.
        NavBackStackEntry backStackEntry = NavHostFragment.findNavController(this)
                .getBackStackEntry(R.id.main_navigation);
        MapViewModel model = new ViewModelProvider(backStackEntry).get(MapViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setMapViewModel(model);

        //listview initialization
        RoboaListSimpleAdapter adapter = new RoboaListSimpleAdapter(requireContext(), new ArrayList<>());
        binding.robuoyListView.setAdapter(adapter);


        //set onClickListener for listview
        binding.robuoyListView.setOnItemClickListener((parent, view1, position, id) -> {
            Roboa roboa = adapter.getItemAt(position);

            //i need to check whether the roboa is online or not
            if(roboa.isActive()) {
                model.setCurrentRoboa(roboa);
                //if the robuoy is free, we navigate to the bind fragment
                if (roboa.getBindedBuoy() == null) {
                    NavHostFragment.findNavController(this).navigate(
                            RoboaInfoFragmentDirections.actionRoboaInfoFragmentToBindBoaAndRoboaFragment());
                }
                //otherwise we navigate to the manage fragment
                else {
                    NavHostFragment.findNavController(this).navigate(
                            RoboaInfoFragmentDirections.actionRoboaInfoFragmentToManageRobuoyFragment());
                }
            }
            //if the robuoy is offline i notify the user
            else{
                Toast.makeText(this.requireContext(), "This robuoy is offline", Toast.LENGTH_SHORT).show();
            }
        });

        //i get the list of roboas
        model.getRoboa().observe(getViewLifecycleOwner(), roboa -> {
            RoboeInitialization.roboeInitialization(roboa, model);
            adapter.clear();
            adapter.addAll(roboa);
            adapter.notifyDataSetChanged();
        });

    }
}