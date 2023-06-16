package it.halb.roboapp.ui.main.adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Roboa;

public class RoboaListSimpleAdapter extends ArrayAdapter<Roboa> {

    private final Context context;
    private final List<Roboa> roboas;


    public RoboaListSimpleAdapter(Context context, List<Roboa> roboas) {
        super(context, 0, roboas);
        this.context = context;
        this.roboas = roboas;
    }

    public Roboa getItemAt(int i){ return roboas.get(i); }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.roboa_item, parent, false);
        }

        Roboa currentRoboa = roboas.get(position);

        ImageView dot = convertView.findViewById(R.id.imageView2RoboaItem);
        TextView date = convertView.findViewById(R.id.textViewUpdateRoboaItem);
        TextView title = convertView.findViewById(R.id.textViewTitleRoboaItem);
        TextView description = convertView.findViewById(R.id.textViewDescriptionRoboaItem);
        TextView buoyBinded = convertView.findViewById(R.id.textViewBuoyBinded);

        date.setText("last time online: " + LocalTime.now().toString().substring(0, 8));
        title.setText("Roboa: " + currentRoboa.getId() + "");
        description.setText("name: " + currentRoboa.getName() + ", state: " + currentRoboa.getStatus());
        if(currentRoboa.getBindedBuoy() != null)
            buoyBinded.setText("binded buoy: " + currentRoboa.getBindedBuoy());
        else
            buoyBinded.setText("binded buoy: none");

        if(!currentRoboa.isActive()){
            title.setAlpha(0.5f);
            description.setAlpha(0.5f);
            date.setText("offline");
            date.setAlpha(0.5f);
            dot.setAlpha(0.5f);
            buoyBinded.setAlpha(0.5f);
        }

        return convertView;
    }
}
