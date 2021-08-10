package com.appsnipp.homedesigns;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sanojpunchihewa.glowbutton.GlowButton;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static com.appsnipp.homedesigns.App.appointments_channel_id;
import static com.appsnipp.homedesigns.App.index_channel_id;

public class MainActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    public TextView hello_name;
    private GlowButton sign_button;
    private DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hello_name = findViewById(R.id.textView3);
        sign_button = findViewById(R.id.sign_btn);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            databaseUsers = database.getReference("users");
            String id = mAuth.getCurrentUser().getUid();
            DatabaseReference username = databaseUsers.child(id).child("name");
            username.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String username = dataSnapshot.getValue().toString();
                    hello_name.setText("שלום, \n" + username);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        // Set selected view
        bottomNavigationView.setSelectedItemId(R.id.navigationPersonalSpace);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestIdToken(getString(R.string.button_sign_in))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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


    }
    @Override
    public void onStart(){
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            sign_button.setText("התנתק");

        } else {
            sign_button.setText("התחבר");
        }
    }

//    Dialogs Zone
    public void DisplayAppointmentsDialog(View view) {
        new FancyGifDialog.Builder(this)
                .setTitle("המפגשים הקרובים (חודש מהיום)")
                .setMessage("תור לחיסון טטנוס - 13.13.13\nתור להחלפת צינורות - 14.14.14\nתור ליועצת תזונה - 15.15.15")
                .setNegativeBtnText("חזור")
                .setPositiveBtnBackground(R.color.gradientLightYellow2)
                .setPositiveBtnText("הצג הכל")
                .setNegativeBtnBackground(R.color.gradientLightGreen)
                .setGifResource(R.drawable.alarm)
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        startActivity(new Intent(getApplicationContext(), AppointmentsCenter.class));
                    }
                })
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                    }
                })
                .build();
    }


    public void DisplayMeasurementsDialog(View view) {
        new FancyGifDialog.Builder(this)
                .setTitle("מדדים עדכניים:")
                .setMessage("גיל מתוקן: 0.2 שנים\nמשקל: 940 גרם\nהיקף ראש: 35 סנטימטר\nעדכון אחרון: 39 ימים")
                .setNegativeBtnText("יותר מאוחר")
                .setPositiveBtnBackground(R.color.gradientLightYellow2)
                .setPositiveBtnText("הצג\\עדכן")
                .setNegativeBtnBackground(R.color.gradientLightGreen)
                .setGifResource(R.drawable.measurements)
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        startActivity(new Intent(getApplicationContext(), FollowUpCenter.class));

                    }
                })
                    .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                    }
                })

                .build();
    }

    //    endof Dialogs Zone
    public void DisplayNotifications(View view) {
        openNotificationSettings();
    }

    private void openNotificationSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            startActivity(intent);
        } else {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }
    }

    public void DisplayLoginPage(View view) {
        if (mAuth.getCurrentUser() != null) {
            sign_out();
        }
        else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            overridePendingTransition(0, 0);
        }
    }


    public void sign_out(){
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mAuth.signOut();
                hello_name.setText("שלום, \nאורח");
                Toast.makeText(MainActivity.this,"U Sign out successfully",Toast.LENGTH_LONG).show();
                onStart();
            }
        });
    }
}
