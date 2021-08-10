package com.appsnipp.homedesigns;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class App extends  Application {
    public static final String appointments_channel_id = "Appointments";
    public static final String index_channel_id = "Update Indexes";
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            NotificationChannel appointments_channel = new NotificationChannel(appointments_channel_id, "מפגשים עתידיים", NotificationManager.IMPORTANCE_DEFAULT);
            appointments_channel.setDescription("This is Appointments notification channel");

            NotificationChannel track_index_channel = new NotificationChannel(index_channel_id, "מעקב מדדים", NotificationManager.IMPORTANCE_DEFAULT);
            track_index_channel.setDescription("This is Index tracking notification channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(appointments_channel);
            manager.createNotificationChannel(track_index_channel);
        }

    }
}
