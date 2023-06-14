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
import it.halb.roboapp.dataLayer.localDataSource.Roboa;
import it.halb.roboapp.databinding.FragmentRobuoyInfoBinding;
import it.halb.roboapp.ui.main.adapters.RoboaListSimpleAdapter;


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

            //prima verifico che la roboa sia online
            if(roboa.getStatus().equals("online")) {
                model.setCurrentRoboa(roboa);
                //se una roboa non ha boe associate allora si può associare una boa
                if (roboa.getBindedBuoy() == null && roboa.isActive()) {
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


        //quello che succede è che quando si torna indietro dalla schermata di gestione della roboa
        //ri avviene la fake initialization e quindi non si vedono le cose testate
        //
        //
        //
        //fake initialization
        Roboa fakeRoboa = new Roboa(123);
        fakeRoboa.setName("Roboa1");
        fakeRoboa.setActive(true);
        fakeRoboa.setStatus("online");
        fakeRoboa.setBindedBuoy("MID LINE START");
        //MID LINE START non ha associata questa boa!
        Roboa fakeRoboa2 = new Roboa(456);
        fakeRoboa2.setName("Roboa2");
        fakeRoboa2.setActive(false);
        fakeRoboa2.setStatus("offline");
        Roboa fakeRoboa3 = new Roboa(789);
        fakeRoboa3.setName("Roboa3");
        fakeRoboa3.setActive(true);
        fakeRoboa3.setStatus("online");
        //delete line addding the fake roboa to the list
        //
        //
        //

        model.getRoboa().observe(getViewLifecycleOwner(), roboa -> {
            adapter.clear();
            adapter.addAll(roboa);
            adapter.add(fakeRoboa);
            adapter.add(fakeRoboa2);
            adapter.add(fakeRoboa3);
            adapter.notifyDataSetChanged();
        });
    }
}