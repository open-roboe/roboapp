package it.halb.roboapp.ui.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Buoy;

public class BuoyListSimpleAdapter extends ArrayAdapter <Buoy> {

    private final Context context;
    private final List<Buoy> buoys;

    public BuoyListSimpleAdapter(@NonNull Context context, List<Buoy> buoys) {
        super(context, 0, buoys);
        this.context = context;
        this.buoys = buoys;
    }

    public Buoy getItemAt(int i){
        return buoys.get(i);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.buoy_item, parent, false);
        }

        Buoy currentBuoy = buoys.get(position);

        TextView title = convertView.findViewById(R.id.textViewTitleBuoy);
        TextView description = convertView.findViewById(R.id.textViewDescriptionBuoy);


        title.setText(currentBuoy.getId());
        description.setText("Appartiene alla regatta " + currentBuoy.getRegattaName());


        return convertView;
    }
}
