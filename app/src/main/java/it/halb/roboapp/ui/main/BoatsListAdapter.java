package it.halb.roboapp.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Account;
//sostituire tutti gli oggetti account con boat
public class BoatsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private final List<Account> boatsList;

    public BoatsListAdapter(List<Account> boatsList) {
        super();
        this.boatsList = boatsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.boat_item, parent, false);
        return new BoatsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BoatsHolder holder, int position) {
        Account currentAccount = getItem(position);
        try{
            holder.user_online.setText(currentAccount.getUsername());
            holder.distanza_metri.setText("distanza da calcolare");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    private Account getItem(int position) {
        Account account = null;
        try {
            account = boatsList.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }


    @Override
    public int getItemCount() {
        if (boatsList != null) {
            return boatsList.size();
        }
        return 0;
    }



    public class BoatsHolder extends RecyclerView.ViewHolder {
        private TextView user_online;
        private TextView distanza_metri;
        public BoatsHolder(@NonNull View itemView) {
            super(itemView);
            this.user_online = itemView.findViewById(R.id.user_online);
            this.distanza_metri = itemView.findViewById(R.id.distanza_metri);
        }
    }

}
