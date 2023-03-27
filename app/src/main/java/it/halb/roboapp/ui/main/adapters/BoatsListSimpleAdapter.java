package it.halb.roboapp.ui.main.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;

import it.halb.roboapp.R;
import it.halb.roboapp.dataLayer.localDataSource.Boat;

public class BoatsListSimpleAdapter extends ArrayAdapter<Boat>{
    private final Context mContext;
    private final List<Boat> mBoats;

    public BoatsListSimpleAdapter(Context context, List<Boat> boats) {
        super(context, 0, boats);
        mContext = context;
        mBoats = boats;
    }

    public Boat getItemAt(int i){
        return mBoats.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.boat_item, parent, false);
        }

        Boat currentBoat = mBoats.get(position);

        TextView lastUpdate = convertView.findViewById(R.id.textViewUpdate);
        TextView title = convertView.findViewById(R.id.textViewTitle);
        TextView subtitle = convertView.findViewById(R.id.textViewDescription);
        ImageView image = convertView.findViewById(R.id.imageView2);

        //write last update
        long timestamp = currentBoat.getLastUpdate();
        if(timestamp - System.currentTimeMillis()/1000 < 60 ){
            lastUpdate.setText(R.string.status_online);
        }
        else{
            String timeDeltaString = DateUtils.getRelativeTimeSpanString(
                    timestamp * 1000, // Convert timestamp to milliseconds
                    System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS, // Show result in minutes
                    DateUtils.FORMAT_ABBREV_RELATIVE
            ).toString();
            lastUpdate.setText(timeDeltaString);
        }

        //write name and distance
        //todo: calculate distance, maybe from inside mapviewmodel
        title.setText(
                currentBoat.getUsername()
        );

        //write race officer status
        if(currentBoat.isRaceOfficer()){
            subtitle.setText(R.string.race_officer);
            image.setImageDrawable(
                    ContextCompat.getDrawable(
                            image.getContext(),
                            R.drawable.speedboat_yellow1
                    )
            );
        }
        else{
            subtitle.setText(R.string.support_boat);
            image.setImageDrawable(
                    ContextCompat.getDrawable(
                            image.getContext(),
                            R.drawable.speedboat_gray1
                    )
            );
        }

        return convertView;
    }
}
