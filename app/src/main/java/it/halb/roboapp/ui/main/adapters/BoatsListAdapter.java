package it.halb.roboapp.ui.main.adapters;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        //generate last update string
        long timestamp = currentBoat.getLastUpdate();
        String timeDeltaString = DateUtils.getRelativeTimeSpanString(
                timestamp * 1000, // Convert timestamp to milliseconds
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS, // Show result in minutes
                DateUtils.FORMAT_ABBREV_RELATIVE
        ).toString();

        holder.title.setText(
                currentBoat.getUsername() + " (250 mt)"
        );
        holder.lastUpdate.setText(timeDeltaString);

        holder.subtitle.setText("Race officer");
    }

    public Boat getBoatAt(int position){
        return getItem(position);
    }

    public class BoatsHolder extends RecyclerView.ViewHolder {
        private final TextView subtitle;
        private TextView lastUpdate;
        private TextView title;
        public BoatsHolder(@NonNull View itemView) {
            super(itemView);
            this.lastUpdate = itemView.findViewById(R.id.textViewUpdate);
            this.title = itemView.findViewById(R.id.textViewTitle);
            this.subtitle = itemView.findViewById(R.id.textViewDescription);
        }
    }

}
