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
            Log.d("RoboaInfoFragment", "onViewCreated: " + roboa.getId());
            //model.setTarget(roboa);
            //Navigation.findNavController(view1).navigate(R.id.mapFragment);
        });

        model.getRoboa().observe(getViewLifecycleOwner(), roboa -> {
            adapter.clear();
            adapter.addAll(roboa);
            adapter.notifyDataSetChanged();
        });

        //adapter.add(new Roboa(123));
        List<Roboa> fakeRoboaList = new ArrayList<>();
        Roboa fakeRoboa = new Roboa(123);
        fakeRoboa.setName("Roboa1");
        fakeRoboa.setActive(true);
        fakeRoboaList.add(fakeRoboa);
        adapter.addAll(fakeRoboaList);
    }
}