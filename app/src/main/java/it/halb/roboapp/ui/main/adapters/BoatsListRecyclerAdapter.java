package it.halb.roboapp.ui.main.adapters;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Boat;

/**
 * This class is here only as a reference for future work. Will be removed soon
 *
 * Boat lists are being displayed with BoatsListSimpleAdapter, which is a basic ListView.
 * The complexity of a RecyclerView is not justified for by the small amount of data displayed
 */
public class BoatsListRecyclerAdapter extends ListAdapter<Boat, BoatsListRecyclerAdapter.BoatsHolder> {


    public BoatsListRecyclerAdapter() {
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

        //write last update
        long timestamp = currentBoat.getLastUpdate();
        String timeDeltaString = DateUtils.getRelativeTimeSpanString(
                timestamp * 1000, // Convert timestamp to milliseconds
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS, // Show result in minutes
                DateUtils.FORMAT_ABBREV_RELATIVE
        ).toString();
        holder.lastUpdate.setText(timeDeltaString);

        //write name and distance
        //todo: calculate distance, maybe from inside mapviewmodel
        holder.title.setText(
                currentBoat.getUsername() + " (250 mt)"
        );

        //write race officer status
        if(currentBoat.isRaceOfficer()){
            holder.subtitle.setText(R.string.race_officer);
            holder.image.setImageDrawable(
                    ContextCompat.getDrawable(
                            holder.image.getContext(),
                            R.drawable.speedboat_yellow1
                    )
            );
        }
        else{
            holder.subtitle.setText(R.string.support_boat);
            holder.image.setImageDrawable(
                    ContextCompat.getDrawable(
                            holder.image.getContext(),
                            R.drawable.speedboat_gray1
                    )
            );
        }
    }

    public Boat getBoatAt(int position){
        return getItem(position);
    }

    public class BoatsHolder extends RecyclerView.ViewHolder {
        private final TextView subtitle;
        private final ImageView image;
        private TextView lastUpdate;
        private TextView title;
        public BoatsHolder(@NonNull View itemView) {
            super(itemView);
            this.lastUpdate = itemView.findViewById(R.id.textViewUpdate);
            this.title = itemView.findViewById(R.id.textViewTitle);
            this.subtitle = itemView.findViewById(R.id.textViewDescription);
            this.image = itemView.findViewById(R.id.imageView2);
        }
    }

}
