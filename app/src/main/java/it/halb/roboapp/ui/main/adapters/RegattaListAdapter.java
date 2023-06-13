package it.halb.roboapp.ui.main.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.time.LocalDate;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Regatta;

public class RegattaListAdapter extends ListAdapter<Regatta, RegattaListAdapter.RegattaHolder> {


    public RegattaListAdapter() {
        super(DIFF_CALLBACK);
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
        return new RegattaHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RegattaHolder holder, int position) {
        //this is where we bind data in our regattas list to RegattaHolder instances
        Regatta currentRegatta = getItem(position);
        holder.textViewTitle.setText(currentRegatta.getName());
        holder.textViewDescription.setText(currentRegatta.getType());
        holder.textViewDate.setText(LocalDate.now().toString());
    }


    public Regatta getRegattaAt(int position){
        return getItem(position);
    }

    class RegattaHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDate;

        public RegattaHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewTitle = itemView.findViewById(R.id.textViewTitle);
            this.textViewDescription = itemView.findViewById(R.id.textViewDescription);
            this.textViewDate = itemView.findViewById(R.id.textViewUpdate);
        }
    }


}
