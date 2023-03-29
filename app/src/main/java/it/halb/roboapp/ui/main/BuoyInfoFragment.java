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

import it.halb.roboapp.dataLayer.localDataSource.Buoy;
import it.halb.roboapp.databinding.FragmentBuoyInfoBinding;
import it.halb.roboapp.ui.main.adapters.BuoyListSimpleAdapter;


public class BuoyInfoFragment extends Fragment {

    private FragmentBuoyInfoBinding binding;
    private MapViewModel model;

    public BuoyInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBuoyInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ViewModel initialization
        model = new ViewModelProvider(this).get(MapViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setMapViewModel(model);

        BuoyListSimpleAdapter adapter = new BuoyListSimpleAdapter(requireContext(), new ArrayList<>());
        binding.buoysListView.setAdapter(adapter);
        binding.buoysListView.setOnItemClickListener((parent, view1, position, id) -> {
            Buoy buoy = adapter.getItemAt(position);
            Log.d("CLICK", "clicked buoy: " + buoy.getId());
            model.setTarget(buoy);
            //Navigation.findNavController(view1).navigate(R.id.mapFragment);
        });

        //TODO: mettere la giusta immagine e le giuste info per ogni item della lista
        //TODO: implementari le funzioni di click su ogni item della lista (quali sono?)


        model.getBuoy().observe(getViewLifecycleOwner(), buoy -> {
            adapter.clear();
            adapter.addAll(buoy);
            adapter.notifyDataSetChanged();
        });













    }
}