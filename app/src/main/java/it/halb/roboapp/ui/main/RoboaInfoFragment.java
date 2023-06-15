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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.dataLayer.localDataSource.Roboa;
import it.halb.roboapp.databinding.FragmentRobuoyInfoBinding;
import it.halb.roboapp.ui.main.adapters.RoboaListSimpleAdapter;
import it.halb.roboapp.util.FakeInitialization;


public class RoboaInfoFragment extends Fragment {
    private FragmentRobuoyInfoBinding binding;
    private List<Roboa> fakeRoboaList = new ArrayList<>();
    private List<Buoy> buoys;


    @Override
    public void onStart() {
        super.onStart();

    }



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

        //i get the list of buoys
        model.getBuoy().observe(getViewLifecycleOwner(), buoy -> {
            Log.d("RoboaInfoFragment", "onViewCreated: " + buoy.size());
            buoys = buoy;
            //go fake-initialize the list
            if(FakeInitialization.flag == false) {
                fakeRoboaList = FakeInitialization.fakeInitialization(buoys);
            }
            else
                fakeRoboaList = FakeInitialization.getFakeRoboaList();
        });


        //set onClickListener for listview
        binding.robuoyListView.setOnItemClickListener((parent, view1, position, id) -> {
            Roboa roboa = adapter.getItemAt(position);

            //prima verifico che la roboa sia online
            if(roboa.isActive()) {
                model.setCurrentRoboa(roboa);
                //se una roboa non ha boe associate allora si può associare una boa
                if (roboa.getBindedBuoy() == null) {
                    NavHostFragment.findNavController(this).navigate(
                            RoboaInfoFragmentDirections.actionRoboaInfoFragmentToBindBoaAndRoboaFragment());
                }
                //altrimenti andiamo alla schermata di gestione della roboa
                else {
                    NavHostFragment.findNavController(this).navigate(
                            RoboaInfoFragmentDirections.actionRoboaInfoFragmentToManageRobuoyFragment());
                }
            }
            //se non lo è mando un messaggio di errore
            else{
                Toast.makeText(this.requireContext(), "This robuoy is offline", Toast.LENGTH_SHORT).show();
            }
        });

        model.getRoboa().observe(getViewLifecycleOwner(), roboa -> {
            adapter.clear();
            adapter.addAll(roboa);
            adapter.addAll(fakeRoboaList);
            adapter.notifyDataSetChanged();
        });





    }
}