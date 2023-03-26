package it.halb.roboapp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Boat;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.databinding.FragmentBoatInfoBinding;
import it.halb.roboapp.ui.main.adapters.BoatsListAdapter;
import it.halb.roboapp.util.RecyclerItemClickListener;


public class BoatInfoFragment extends Fragment {
    private FragmentBoatInfoBinding binding;
    private MapViewModel model;

    public BoatInfoFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoatInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ViewModel initialization
        model = new ViewModelProvider(this).get(MapViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setMapViewModel(model);

        binding.boatsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.boatsRecyclerView.setHasFixedSize(true);
        BoatsListAdapter adapter = new BoatsListAdapter();
        binding.boatsRecyclerView.setAdapter(adapter);

        model.getBoats().observe(this.getViewLifecycleOwner(), boats -> {
            adapter.submitList(boats);
        });


        //touch listener
        binding.boatsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                requireContext(), binding.boatsRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Boat b = adapter.getBoatAt(position);
                handleBoatClick(b);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }
        ));


    }

    public void handleBoatClick(Boat b){
        model.setTarget(b);
    }
}