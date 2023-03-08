package it.halb.roboapp;

import static it.halb.roboapp.util.Constants.NOTIFICATION_CHANNEL_ID;
import static it.halb.roboapp.util.Constants.NOTIFICATION_ID;
import static it.halb.roboapp.util.Constants.POLLING_DELAY_MILLIS;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class RunningRegattaService extends Service {

    private Handler pollingHandler;

    /**
     * This runnable is the core of the service:
     * It runs periodically, calling the repository to update
     * the user position and fetching the regatta information
     *
     */
    private final Runnable pollingRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d("POLLING", "poll!");
            Application application = getApplication();
            //TODO init repository, (probably outside of here), and call repository.poll()

            pollingHandler.postDelayed(this, POLLING_DELAY_MILLIS);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notification_channel_name);
            String description = getString(R.string.notification_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        //define the action for the notification click
        PendingIntent pendingClickIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // set the notification content
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_sailing_24)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingClickIntent);


        //start a foreground service, with the notification defined above
        startForeground(NOTIFICATION_ID, builder.build());

        //initialize the polling loop
        initializePolling();

        return super.onStartCommand(intent, flags, startId);
    }

    private void initializePolling(){
        if(pollingHandler == null){
            pollingHandler = new Handler(Looper.getMainLooper());
            pollingHandler.postDelayed(pollingRunnable, POLLING_DELAY_MILLIS);
        }
    }



}
