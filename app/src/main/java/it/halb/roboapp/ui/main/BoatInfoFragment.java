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

import it.halb.roboapp.databinding.FragmentBoatInfoBinding;
import it.halb.roboapp.ui.main.adapters.BoatsListAdapter;


public class BoatInfoFragment extends Fragment {


    private FragmentBoatInfoBinding binding;

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
        BoatInfoViewModel model = new ViewModelProvider(this).get(BoatInfoViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setBoatInfoViewModel(model);

        binding.boatsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.boatsRecyclerView.setHasFixedSize(true);
        BoatsListAdapter adapter = new BoatsListAdapter();
        binding.boatsRecyclerView.setAdapter(adapter);

        model.getBoats().observe(this.getViewLifecycleOwner(), boats -> {
            adapter.submitList(boats);
        });

        binding.boatsRecyclerView.setOnClickListener(v -> {
            Toast.makeText(this.getContext(), "CLICKED" + v.toString(), Toast.LENGTH_LONG).show();
            //TODO: implementare il giusto listener
        });





    }
}