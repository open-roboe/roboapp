package it.halb.roboapp.ui.main.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;
import it.halb.roboapp.generated.callback.OnClickListener;
import it.halb.roboapp.ui.main.RegattaListFragment;
import it.halb.roboapp.ui.main.RegattaListFragmentDirections;
import it.halb.roboapp.ui.main.RegattaListViewModel;
import it.halb.roboapp.util.Constants;
import it.halb.roboapp.util.EditingRegatta;
import it.halb.roboapp.util.callback.EditRegattaCallback;

public class RegattaListAdapter extends ListAdapter<Regatta, RegattaListAdapter.RegattaHolder> {

    private RegattaListViewModel model;

    private RegattaListFragment fragment;

    public RegattaListAdapter(RegattaListViewModel model, RegattaListFragment fragment) {
        super(DIFF_CALLBACK);
        this.model = model;
        this.fragment = fragment;
    }

    private static final DiffUtil.ItemCallback<Regatta> DIFF_CALLBACK = new DiffUtil.ItemCallback<Regatta>() {
        @Override
        public boolean areItemsTheSame(@NonNull Regatta oldItem, @NonNull Regatta newItem) {
            //we compare the primary keys
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Regatta oldItem, @NonNull Regatta newItem) {
            //we compare the content
            return oldItem.hashCode() == newItem.hashCode();
        }
    };

    @NonNull
    @Override
    public RegattaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //this is where we define and initialize/inflate the layout used by a sigle view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.regatta_item, parent, false);
        return new RegattaHolder(itemView, model, this, fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull RegattaHolder holder, int position) {
        //this is where we bind data in our regattas list to RegattaHolder instances
        Regatta currentRegatta = getItem(position);
        holder.textViewTitle.setText(currentRegatta.getName());
        holder.textViewDescription.setText(currentRegatta.getType());
        holder.textViewDate.setText("dec 12");
    }


    public Regatta getRegattaAt(int position){
        return getItem(position);
    }

    public static class RegattaHolder extends RecyclerView.ViewHolder{

        private RelativeLayout relativeLayout;
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDate;

        private ImageButton editButton;

        private ImageButton deleteButton;

        public RegattaHolder(@NonNull View itemView, RegattaListViewModel model, RegattaListAdapter adapter, RegattaListFragment fragment) {
            super(itemView);
            this.relativeLayout = itemView.findViewById(R.id.item);
            this.textViewTitle = itemView.findViewById(R.id.textViewTitle);
            this.textViewDescription = itemView.findViewById(R.id.textViewDescription);
            this.textViewDate = itemView.findViewById(R.id.textViewDate);
            this.editButton = itemView.findViewById(R.id.edit_button);
            this.deleteButton = itemView.findViewById(R.id.delete_button);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Regatta regatta = adapter.getRegattaAt(getAdapterPosition());
                    NavHostFragment.findNavController(fragment).navigate(
                            RegattaListFragmentDirections.actionCourseListToLoadFragment(regatta.getName()));
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.regattaName, adapter.getRegattaAt(getAdapterPosition()).getName());
                    bundle.putString(Constants.regattaType, adapter.getRegattaAt(getAdapterPosition()).getType());
                    bundle.putInt(Constants.regattaCreationDate, adapter.getRegattaAt(getAdapterPosition()).getCreationDate());
                    bundle.putInt(Constants.regattaWindDirection, adapter.getRegattaAt(getAdapterPosition()).getWindDirection());
                    bundle.putDouble(Constants.regattaStartLineLen, adapter.getRegattaAt(getAdapterPosition()).getStartLineLen());
                    bundle.putDouble(Constants.regattaBreakDistance, adapter.getRegattaAt(getAdapterPosition()).getBreakDistance());
                    bundle.putDouble(Constants.regattaCourseLength, adapter.getRegattaAt(getAdapterPosition()).getCourseLength()/1000.0);
                    bundle.putDouble(Constants.regattaSecondMarkDistance, adapter.getRegattaAt(getAdapterPosition()).getSecondMarkDistance());
                    bundle.putBoolean(Constants.regattaBottonBuoy, adapter.getRegattaAt(getAdapterPosition()).isBottonBuoy());
                    bundle.putBoolean(Constants.regattaGate, adapter.getRegattaAt(getAdapterPosition()).isGate());
                    bundle.putDouble(Constants.regattaLongitude, adapter.getRegattaAt(getAdapterPosition()).getLongitude());
                    bundle.putDouble(Constants.regattaLatitude, adapter.getRegattaAt(getAdapterPosition()).getLatitude());
                    NavHostFragment.findNavController(fragment).navigate(
                            R.id.action_courseList_to_editRegattaFragment, bundle);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.deleteRegatta(adapter.getRegattaAt(getAdapterPosition()));
                }
            });

        }
    }
}
