package it.halb.roboapp.ui.main.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Boat;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class BoatsListAdapter extends ListAdapter<Boat, BoatsListAdapter.BoatsHolder> {


    public BoatsListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Boat> DIFF_CALLBACK = new DiffUtil.ItemCallback<Boat>() {
        @Override
        public boolean areItemsTheSame(@NonNull Boat oldItem, @NonNull Boat newItem) {
            //we compare the primary keys
            return oldItem.getUsername().equals(newItem.getUsername());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Boat oldItem, @NonNull Boat newItem) {
            //we compare the content
            return oldItem.hashCode() == newItem.hashCode();
        }
    };
    @NonNull
    @Override
    public BoatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.boat_item, parent, false);
        return new BoatsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BoatsHolder holder, int position) {
        Boat currentBoat = getItem(position);
        try{
            holder.user_online.setText(currentBoat.getLastUpdate() + "");
            holder.description.setText(currentBoat.getUsername());
            holder.distanza_metri.setText("distanza tra (" + currentBoat.getLatitude() + ", " + currentBoat.getLongitude() + ") e posizione attuale");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Boat getBoatAt(int position){
        return getItem(position);
    }

    public class BoatsHolder extends RecyclerView.ViewHolder {
        private TextView user_online;
        private TextView distanza_metri;
        private TextView description;
        public BoatsHolder(@NonNull View itemView) {
            super(itemView);
            this.user_online = itemView.findViewById(R.id.textViewUpdate);
            this.distanza_metri = itemView.findViewById(R.id.textViewTitle);
            this.description = itemView.findViewById(R.id.textViewDescription);
        }
    }

}
