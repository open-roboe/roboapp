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

import java.util.ArrayList;

import it.halb.roboapp.dataLayer.localDataSource.Roboa;
import it.halb.roboapp.databinding.FragmentRobuoyInfoBinding;
import it.halb.roboapp.ui.main.adapters.RoboaListSimpleAdapter;


public class RoboaInfoFragment extends Fragment {


    private FragmentRobuoyInfoBinding binding;

    public RoboaInfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRobuoyInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ViewModel initialization
        MapViewModel model = new ViewModelProvider(requireActivity()).get(MapViewModel.class);
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






    }
}