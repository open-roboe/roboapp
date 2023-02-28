package it.halb.roboapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.halb.roboapp.data.local.Regatta;

public class RegattaListAdapter extends RecyclerView.Adapter<RegattaListAdapter.RegattaHolder> {

    private List<Regatta> regattas = new ArrayList<>();

    @NonNull
    @Override
    public RegattaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //this is where we define and initialize/inflate the layout used by a sigle view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.regatta_item, parent, false);
        return new RegattaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RegattaHolder holder, int position) {
        //this is where we bind data in our regattas list to RegattaHolder instances
        Regatta currentRegatta = regattas.get(position);
        holder.textViewTitle.setText(currentRegatta.getName());
        holder.textViewDescription.setText(currentRegatta.getType());
        holder.textViewDate.setText("dec 12");
    }

    @Override
    public int getItemCount() {
        return regattas.size();
    }

    public void setRegattas(List<Regatta> regattas){
        this.regattas = regattas;
        //TODO: update soon with better notify methods
        notifyDataSetChanged();
    }

    public Regatta getRegattaAt(int position){
        return regattas.get(position);
    }

    class RegattaHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDate;

        public RegattaHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewTitle = itemView.findViewById(R.id.textViewTitle);
            this.textViewDescription = itemView.findViewById(R.id.textViewDescription);
            this.textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}
