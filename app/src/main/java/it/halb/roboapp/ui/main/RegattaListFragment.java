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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;

import it.halb.roboapp.R;
import it.halb.roboapp.RunningRegattaService;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.databinding.FragmentRegattaListBinding;
import it.halb.roboapp.generated.callback.OnClickListener;
import it.halb.roboapp.ui.main.adapters.RegattaListAdapter;
import it.halb.roboapp.util.RecyclerItemClickListener;
import it.halb.roboapp.util.SwipeController;

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

        // stop the followService, since we are not following any regatta
        requireActivity().stopService(new Intent(getContext(), RunningRegattaService.class));

        //viewModel initialization
        RegattaListViewModel model = new ViewModelProvider(this).get(RegattaListViewModel.class);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setRegattaListViewModel(model);

        //recyclerview initialization
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setHasFixedSize(true);

        RegattaListAdapter adapter = new RegattaListAdapter(model, this);



        binding.recyclerView.setAdapter(adapter);

        //viewModel update listeners
        model.getAllRegattas().observe(this.getViewLifecycleOwner(), regattas -> {
            //update the list adapter
            adapter.submitList(regattas);

            //update placeholder visibility
            binding.noRegattasPlaceholder.setVisibility(
                    regattas.size() > 0 ? View.INVISIBLE : View.VISIBLE
            );
            //update searchbar scroll
            AppBarLayout.LayoutParams p = (AppBarLayout.LayoutParams) binding.fakeSearchBar.getLayoutParams();
            p.setScrollFlags(
                    regattas.size() > 5 ?
                            AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL :
                            AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
            );
            binding.fakeSearchBar.setLayoutParams(p);
        });

        //temporary test
        binding.fakeSearchBar.setOnClickListener(v -> {
            model.debugFakeregatta();
            /*
            Snackbar.make(v, snackbar_regatta_deleted_text, Snackbar.LENGTH_LONG)
                    .setDuration(10 * 1000)
                    .setAction(R.string.snackbar_regatta_deleted_undo, v1 -> {
                        model.testLogout();
                    })
                    .show();

             */
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
}