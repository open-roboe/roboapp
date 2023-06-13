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

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;

import it.halb.roboapp.R;
import it.halb.roboapp.RunningRegattaService;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.databinding.FragmentRegattaListBinding;
import it.halb.roboapp.ui.main.adapters.RegattaListAdapter;
import it.halb.roboapp.util.RecyclerItemClickListener;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

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

        //recyclerview initialization
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerView.setHasFixedSize(true);
        adapter = new RegattaListAdapter();
        binding.recyclerView.setAdapter(adapter);

        //viewModel update listeners
        model.getAllRegattas().observe(this.getViewLifecycleOwner(), regattas -> {
            //update the list adapter
            adapter.submitList(regattas);

            //update placeholder visibility
            binding.noRegattasPlaceholder.setVisibility(
                    regattas.size() > 0 ? View.INVISIBLE : View.VISIBLE
            );
        });

        //searching in the list using the latest added search View
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

        //recyclerview touch gestures
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT ) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                model.deleteRegatta(
                        adapter.getRegattaAt(viewHolder.getAdapterPosition())
                );
            }


            /**
             * Draw a red background under the item when an item is wiped to the side
             */
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    // Get RecyclerView item from the ViewHolder
                    View itemView = viewHolder.itemView;

                    Paint p = new Paint();
                    p.setColor(ContextCompat.getColor(requireContext(), R.color.custom_removed_red));
                    if (dX > 0) {
                        /* Set your color for positive displacement */

                        // Draw Rect with varying right side, equal to displacement dX
                        c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                                (float) itemView.getBottom(), p);
                    } else {
                        /* Set your color for negative displacement */

                        // Draw Rect with varying left side, equal to the item's right side plus negative displacement dX
                        c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                                (float) itemView.getRight(), (float) itemView.getBottom(), p);
                    }

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }
        }).attachToRecyclerView(binding.recyclerView);


        //touch listener
        binding.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
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
        }
        ));
    }

    //used to move to map fragment
    private void handleRegattaClick(String regattaName){
        binding.searchView.clearFocus();
        NavHostFragment.findNavController(this).navigate(
                RegattaListFragmentDirections.actionCourseListToLoadFragment(regattaName)
        );
    }

    //filter the list based on the query
    private void filterList(String query){
        List<Regatta> filteredList = new ArrayList<>();
        for (Regatta regatta : model.getAllRegattas().getValue()) {
            if (regatta.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(regatta);
            }
        }
        adapter.submitList(filteredList);
    }



}