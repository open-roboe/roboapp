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

import java.util.ArrayList;
import java.util.List;

import it.halb.roboapp.dataLayer.localDataSource.Boat;
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

        //test recyclerView
        Boat a = new Boat();
        Boat b = new Boat();
        Boat c = new Boat();
        a.setUsername("qaaaaaaaa");
        b.setUsername("bbbbbbbbb");
        c.setUsername("ccccccccc");
        ArrayList<Boat> list = new ArrayList<Boat>();
        list.add(a);
        list.add(b);
        list.add(c);
        adapter.submitList(list);






    }
}