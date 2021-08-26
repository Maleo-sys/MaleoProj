package com.appsnipp.maleo_proj;

import android.app.Dialog;
import android.content.Intent;
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

import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    public TextView hello_name;
    private GlowButton sign_button, choose_baby;
    private DatabaseReference databaseUsers;
    private Button add_child_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign_button = findViewById(R.id.sign_btn);
        choose_baby = findViewById(R.id.choose_child);
        hello_name = findViewById(R.id.hello_name);
        hello_name.setText("שלום, \n");
        add_child_dialog = findViewById(R.id.display_dialog);
        add_child_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddBabyDialog();
            }
        });


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
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
        } else {
            hello_name = findViewById(R.id.hello_name);
            hello_name.setText("שלום, \nאורח");

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
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigationPersonalSpace:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigationDataCenter:
                        startActivity(new Intent(getApplicationContext(), DataCenter.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navigationAppointments:
                        startActivity(new Intent(getApplicationContext(), AppointmentsCenter.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_male:
                if (checked)
                    Toast.makeText(MainActivity.this, "MALE PICKED.", Toast.LENGTH_LONG).show();
                break;
            case R.id.radio_female:
                if (checked)
                    Toast.makeText(MainActivity.this, "FEMALE PICKED.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void showAddBabyDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.add_child_dialog);

        //Initializing the views of the dialog.
        final EditText baby_name = dialog.findViewById(R.id.baby_name);
        final RadioGroup baby_gender = dialog.findViewById(R.id.baby_gender);
        final EditText child_age_by_weeks = dialog.findViewById(R.id.child_age_by_weeks);
        Button submit_child_stats = dialog.findViewById(R.id.submit_child_stats);


        submit_child_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = baby_name.getText().toString();
                String age_by_weeks_str = child_age_by_weeks.getText().toString();
                DatePicker bdaypick = (DatePicker)findViewById(R.id.birth_date_picker); // initiate a date picker
                int day = bdaypick.getDayOfMonth(); // get the selected day of the month

                Toast.makeText(MainActivity.this, day, Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("myTag", "resumed");

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {

            sign_button.setText("התנתק");
            choose_baby.setVisibility(View.VISIBLE);


        } else {
            sign_button.setText("התחבר");
            choose_baby.setVisibility(View.GONE);

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
        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            overridePendingTransition(0, 0);
        }
    }


    public void sign_out() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mAuth.signOut();
                hello_name.setText("שלום, \nאורח");

                Toast.makeText(MainActivity.this, "Signed Out Successfully.", Toast.LENGTH_LONG).show();
                onStart();
            }
        });
    }
}
