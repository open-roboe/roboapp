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
        Boat a = new Boat("a", "b", 0.000, 0.2222);
        Boat b = new Boat("a", "b", 0.000, 0.2222);
        Boat c = new Boat("a", "b", 0.000, 0.2222);
        a.setUsername("qaaaaaaaa");
        b.setUsername("bbbbbbbbb");
        c.setUsername("ccccccccc");
        ArrayList<Boat> list = new ArrayList<Boat>();
        list.add(a);
        list.add(b);
        list.add(c);
        /*
        Boat d = new Boat("a", "b", 0.000, 0.2222);
        Boat e = new Boat("a", "b", 0.000, 0.2222);
        Boat f = new Boat("a", "b", 0.000, 0.2222);
        d.setUsername("qaaaaaaaa");
        e.setUsername("bbbbbbbbb");
        f.setUsername("ccccccccc");

        list.add(d);
        list.add(e);
        list.add(f);
        Boat g = new Boat("a", "b", 0.000, 0.2222);
        Boat h = new Boat("a", "b", 0.000, 0.2222);
        Boat i = new Boat("a", "b", 0.000, 0.2222);
        g.setUsername("qaaaaaaaa");
        h.setUsername("bbbbbbbbb");
        i.setUsername("ccccccccc");

        list.add(g);
        list.add(h);
        list.add(i);
        Boat l = new Boat("a", "b", 0.000, 0.2222);
        Boat m = new Boat("a", "b", 0.000, 0.2222);
        Boat n = new Boat("a", "b", 0.000, 0.2222);
        l.setUsername("qaaaaaaaa");
        m.setUsername("bbbbbbbbb");
        n.setUsername("ccccccccc");

        list.add(l);
        list.add(m);
        list.add(n);
        Boat p = new Boat("a", "b", 0.000, 0.2222);
        Boat q = new Boat("a", "b", 0.000, 0.2222);
        Boat r = new Boat("a", "b", 0.000, 0.2222);
        p.setUsername("qaaaaaaaa");
        q.setUsername("BBBBBBB");
        r.setUsername("AAAAAAAAAA");

        list.add(p);
        list.add(q);
        list.add(r);


         */
        adapter.submitList(list);






    }
}