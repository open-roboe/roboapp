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
            //TODO: finire bene questo metodo
            return true;
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
            holder.user_online.setText(currentBoat.getUsername());
            holder.distanza_metri.setText("distanza da calcolare");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        holder.boat_item_view.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Clicked: " + position, Toast.LENGTH_SHORT).show();
            Log.d("prova", "Clicked: " + position);
        });
    }

    public class BoatsHolder extends RecyclerView.ViewHolder {
        private TextView user_online;
        private TextView distanza_metri;
        private View boat_item_view;
        public BoatsHolder(@NonNull View itemView) {
            super(itemView);
            this.user_online = itemView.findViewById(R.id.user_online);
            this.distanza_metri = itemView.findViewById(R.id.distanza_metri);
            this.boat_item_view = itemView.findViewById(R.id.boatItemAdapterXML);
        }
    }

}
