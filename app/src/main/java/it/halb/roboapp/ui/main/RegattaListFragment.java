package it.halb.roboapp.ui.main;

import static it.halb.roboapp.R.string.snackbar_regatta_deleted_text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import it.halb.roboapp.R;
import it.halb.roboapp.databinding.FragmentRegattaListBinding;

public class RegattaListFragment extends Fragment {
    private FragmentRegattaListBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegattaListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //viewmodel initialization
        RegattaListViewModel model = new ViewModelProvider(this).get(RegattaListViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setRegattaListViewModel(model);

        //recyclerview initialization
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerView.setHasFixedSize(true);
        RegattaListAdapter adapter = new RegattaListAdapter();
        binding.recyclerView.setAdapter(adapter);

        //viewmodel update listeners
        model.getAllRegattas().observe(this.getViewLifecycleOwner(), regattas -> {
            //Toast.makeText(this.getContext(), "CHANGED", Toast.LENGTH_LONG).show();
            adapter.submitList(regattas);
            Log.d("REGATTAS_OBSERVE", "changes!");
        });

        //temporary test
        binding.fakeSearchBar.setOnClickListener(v -> {
            Snackbar.make(v, snackbar_regatta_deleted_text, Snackbar.LENGTH_LONG)
                    .setDuration(10 * 1000)
                    .setAction(R.string.snackbar_regatta_deleted_undo, v1 -> {
                        model.testLogout();
                    })
                    .show();
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
                        adapter.getRegattaAt( viewHolder.getAdapterPosition())
                );
                //adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
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



    }
}