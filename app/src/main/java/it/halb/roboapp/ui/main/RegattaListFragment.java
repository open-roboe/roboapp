package it.halb.roboapp.ui.main;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.halb.roboapp.R;
import it.halb.roboapp.RunningRegattaService;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.databinding.FragmentRegattaListBinding;
import it.halb.roboapp.generated.callback.OnClickListener;
import it.halb.roboapp.ui.main.adapters.RegattaListAdapter;
import it.halb.roboapp.util.LinearLayoutManagerWrapper;
import it.halb.roboapp.util.RecyclerItemClickListener;
import it.halb.roboapp.util.SwipeController;

public class RegattaListFragment extends Fragment {
    private FragmentRegattaListBinding binding;
    private RegattaListViewModel model;
    private RegattaListAdapter adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegattaListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // stop the followService, since we are not following any regatta
        requireActivity().stopService(new Intent(getContext(), RunningRegattaService.class));
        // Clear the runningRegatta viewModel.
        // For some reason proper Navigation Graph scoping does not work, so we are manually clearing it here.
        NavHostFragment.findNavController(this)
                .getBackStackEntry(R.id.main_navigation)
                        .getViewModelStore()
                                .clear();

        //viewModel initialization
        model = new ViewModelProvider(this).get(RegattaListViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setRegattaListViewModel(model);




        if (!getArguments().getBoolean("isRaceOfficer")) {
            binding.floatingActionButton.setVisibility(View.GONE);
            binding.floatingActionButton.setEnabled(false);
        }

        //recyclerview initialization
        binding.recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(this.getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setHasFixedSize(true);

        adapter = new RegattaListAdapter(model, this, getArguments().getBoolean("isRaceOfficer"));

        binding.recyclerView.setAdapter(adapter);

        //viewModel update listeners
        model.getAllRegattas().observe(this.getViewLifecycleOwner(), regattas -> {
            //update the list adapter
            adapter.submitList(regattas);
            adapter.notifyDataSetChanged();

            //update placeholder visibility
            binding.noRegattasPlaceholder.setVisibility(
                    regattas.size() > 0 ? View.INVISIBLE : View.VISIBLE
            );
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });


        //refresh listener
        binding.refreshLayout.setOnRefreshListener(() -> {
            model.refresh(
                    v -> binding.refreshLayout.setRefreshing(false),
                    (code, details) -> {
                        binding.refreshLayout.setRefreshing(false);
                        Snackbar.make(view, "refresh error", Snackbar.LENGTH_SHORT)
                                .show();
                    }
            );
        });

        binding.floatingActionButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(
                    RegattaListFragmentDirections.actionCourseListToCreateRegattaFragment()
            );
        });

        /*binding.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                requireContext(), binding.recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Regatta regatta = adapter.getRegattaAt(position);
                handleRegattaClick(regatta.getName());
            }

            @Override
            public void onLongItemClick(View view, int position) {
                if(model.showSwipeHint){
                    model.showSwipeHint = false;
                    Snackbar.make(view, R.string.snackbar_info_swipe_to_delete_regatta, Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        }));*/

    }

    private void filterList(String query){
        List<Regatta> filteredList = new ArrayList<>();

        model.getAllRegattas().observe(this.getViewLifecycleOwner(), regattas -> {
            for (Regatta regatta : regattas) {
                if (regatta.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(regatta);
                }
            }
        });
        adapter.submitList(filteredList);
        adapter.notifyDataSetChanged();
    }

}