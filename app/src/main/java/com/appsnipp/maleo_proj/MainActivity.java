package com.appsnipp.maleo_proj;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    public TextView hello_name;
    private GlowButton sign_button;
    private Button choose_baby;
    private DatabaseReference databaseUsers;
    private String name;
    private String gender;

    private Button choose_child_dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign_button = findViewById(R.id.sign_btn);
        choose_baby = findViewById(R.id.choose_child_button);
        hello_name = findViewById(R.id.hello_name);
        hello_name.setText("שלום, \n");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("baby_name");
            gender = extras.getString("baby_gender");
        }

//        choose_child_dialog = findViewById(R.id.display_addbaby_dialog);
        choose_baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Choose_Child.class));
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
                        Intent i = new Intent(getApplicationContext(), FollowUpCenter.class);
                        i.putExtra("baby_name",name);
                        i.putExtra("baby_gender",gender);
                        startActivity(i);
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
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser == null) {
                return;
            }
            String currentId = currentUser.getUid();

            DatabaseReference ref0 = FirebaseDatabase.getInstance().getReference("users");
            ref0.child(currentId).child("children").orderByChild("name").limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.v("mug3", String.valueOf(snapshot));
                    if(snapshot.getValue(Baby.class) == null){
                        Log.v("mug2", String.valueOf("here"));
                        Toast.makeText(MainActivity.this, "נדרש להוסיף תינוק", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        return;
                    }
                    Map<String, Object> td = (HashMap<String, Object>) snapshot.getValue();
                    Log.v("mug2", String.valueOf(snapshot));
                    String[] keys = td.keySet().toArray(new String[0]);
                    Map<String, String> key = (HashMap <String, String>) td.get(keys[0]);
                    if (name == null) {
                        name = key.get("name");
                    }
                    if (gender == null) {
                        gender = key.get("gender");
                    }
                    Log.v("mug2", String.valueOf(name));
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
            Query refi = ref.child(currentId).child("children").orderByChild("scales").equalTo(name);
            ref.child(currentId).child("children").orderByChild("name").equalTo(name).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Scale stat = null;
                    int current = 100;
                    for (DataSnapshot ds : snapshot.child(name).child("scales").getChildren()) {
                        Scale s = ds.getValue(Scale.class);
                        if (s != null && s.getAdj_age() < current){
                            stat = s;
                            current = s.getAdj_age();
                        }
                    }
                    new FancyGifDialog.Builder(MainActivity.this)
                            .setTitle("מדדים עדכניים:")
                            .setMessage(" גיל מתוקן: " + stat.getAdj_age() + " שנים " + " \n " + " משקל: " + stat.getWeight() + " קילוגרם \n " + " גובה: " + stat.getHeight() +  " סנטימטר \n " + " היקף ראש: " + stat.getHead() + " סנטימטר\n ")
//                            .setMessage("גיל מתוקן: 0.2 שנים\nמשקל: 940 גרם\nהיקף ראש: 35 סנטימטר\nעדכון אחרון: 39 ימים")
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
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });}}

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

    public void DisplayAboutApplication(View view) {
        new FancyGifDialog.Builder(this)
                .setTitle("אודות האפליקציה:")
                .setMessage("יוצרים:\nבן גנדלר\nרועי מש\nמתן גרינברג \n גרסת האפליקציה: 2.18")
                .setNegativeBtnText("יותר מאוחר")
                .setPositiveBtnBackground(R.color.gradientLightYellow2)
                .setPositiveBtnText("צור קשר")
                .setNegativeBtnBackground(R.color.gradientLightGreen)
                .setGifResource(R.drawable.information)
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
//                        startActivity(new Intent(getApplicationContext(), FollowUpCenter.class));

                    }
                })
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                    }
                })

                .build();
    }

    public void DisplayPersonalDetails(View view) {
        new FancyGifDialog.Builder(this)
                .setTitle("פרטי המשתמש:")
                .setMessage("אימייל: test@gmail.com \n  פלאפון: 0549342221 \n תאריך הרשמה: 24.06.21")
                .setNegativeBtnText("יציאה")
                .setPositiveBtnBackground(R.color.gradientLightYellow2)
                .setPositiveBtnText("עריכה")
                .setNegativeBtnBackground(R.color.gradientLightGreen)
                .setGifResource(R.drawable.mydetails)
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
//                        startActivity(new Intent(getApplicationContext(), FollowUpCenter.class));

                    }
                })
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                    }
                })

                .build();
    }

    private String log(String gen) {
        Log.v("score", String.valueOf(gen));
        return gen;
    }

}
