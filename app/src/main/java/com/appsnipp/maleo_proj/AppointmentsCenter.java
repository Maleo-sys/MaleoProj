package com.appsnipp.maleo_proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.appsnipp.maleo_proj.App.appointments_channel_id;
import static com.appsnipp.maleo_proj.App.index_channel_id;

public class AppointmentsCenter extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_center);
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        // Set selected view
        bottomNavigationView.setSelectedItemId(R.id.navigationAppointments);
        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigationFollowUp:
                        startActivity(new Intent(getApplicationContext(), FollowUpCenter.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.navigationPersonalSpace:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.navigationDataCenter:
                        startActivity(new Intent(getApplicationContext(), DataCenter.class));
                        overridePendingTransition(0,0);
                        return true;

                    case  R.id.navigationAppointments:
                        startActivity(new Intent(getApplicationContext(), AppointmentsCenter.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        notificationManager = NotificationManagerCompat.from(this);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextMessage = findViewById(R.id.edit_text_message);
    }

    public void sendOnAptChan(View v) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();
        Notification notification = new NotificationCompat.Builder(this, appointments_channel_id)
                .setSmallIcon(R.drawable.ic_menu_gallery)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
    }
    public void sendOnIndexChan(View v) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();
        Notification notification = new NotificationCompat.Builder(this, index_channel_id)
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
        notificationManager.notify(2, notification);
    }
}