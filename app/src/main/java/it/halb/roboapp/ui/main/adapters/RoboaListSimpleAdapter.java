package it.halb.roboapp.ui.main.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.roboa_item, null);
        }

        Roboa currentRoboa = roboas.get(position);

        TextView title = convertView.findViewById(R.id.textViewTitleRoboaItem);
        title.setText(currentRoboa.getId());
        TextView description = convertView.findViewById(R.id.textViewDescriptionRoboaItem);
        description.setText("Roboa: " + currentRoboa.getName() + ", stato: " + currentRoboa.isActive());


        return convertView;
    }
}
