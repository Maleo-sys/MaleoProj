package com.appsnipp.maleo_proj;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class Add_Child extends AppCompatActivity {

    private EditText name, week_number_of_birth, head, height, weight;
    private DatePicker date_of_birth;
    private int year_of_birth, month_of_birth, day_of_birth;
    private Button submit_child_stats;

    private FirebaseAuth firebaseAuth;

    private String name_string, gender_string = "m";
    private int week_int;
    private double head_double, height_double, weight_double;
    private Baby baby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);


        name = findViewById(R.id.baby_name);
        week_number_of_birth = findViewById(R.id.weeks_to_birth);
        head = findViewById(R.id.head_measurement);
        height = findViewById(R.id.height_measurement);
        weight = findViewById(R.id.weight_measurement);
        date_of_birth = findViewById(R.id.birth_date_picker);
        submit_child_stats = findViewById(R.id.submit_child_stats);
        firebaseAuth = FirebaseAuth.getInstance();

        submit_child_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
            }
            // Enables Always-on
//        setAmbientEnabled();
        });
    }

    private void inputData() {
        name_string = name.getText().toString().trim();
        week_int = Integer.parseInt(week_number_of_birth.getText().toString().trim());
        head_double = Double.parseDouble(head.getText().toString().trim());
        height_double = Double.parseDouble(height.getText().toString().trim());
        weight_double = Double.parseDouble(weight.getText().toString().trim());

        /**
         * TODO: Connect Calender to firebase
         */

        if(TextUtils.isEmpty(name_string)){
            Toast.makeText(this,"לא נבחר שם",Toast.LENGTH_LONG).show();
            name.setError("Invalid Title");
            name.setFocusable(true);
            return;
        }
        if(TextUtils.isEmpty(""+ week_int)){
            Toast.makeText(this,"לא נבחר מספר השבוע ללידה",Toast.LENGTH_LONG).show();
            week_number_of_birth.setError("Invalid value");
            week_number_of_birth.setFocusable(true);
            return;
        }
        if(TextUtils.isEmpty(""+ head_double)){
            Toast.makeText(this,"לא נבחר היקף ראש",Toast.LENGTH_LONG).show();
            head.setError("Invalid value");
            head.setFocusable(true);
            return;
        }
        if(TextUtils.isEmpty(""+ height_double)){
            Toast.makeText(this,"לא נבחר גובה",Toast.LENGTH_LONG).show();
            height.setError("Invalid value");
            height.setFocusable(true);
            return;
        }

        if(TextUtils.isEmpty(""+ weight_double)){
            Toast.makeText(this,"לא נבחר משקל",Toast.LENGTH_LONG).show();
            weight.setError("Invalid value");
            weight.setFocusable(true);
            return;
        }

        addchild();

    }

    private void addchild() {
        //add data to db
        // progressDialog.setMessage("saving...");
        // progressDialog.show();



        year_of_birth = date_of_birth.getYear();
        month_of_birth = date_of_birth.getMonth()+1;
        day_of_birth = date_of_birth.getDayOfMonth();

        Baby baby = new Baby(name_string,gender_string,week_int, year_of_birth, month_of_birth, day_of_birth);
        Scale first_scale = new Scale(baby.getAdj_age(), weight_double, height_double, head_double);

        /**
         *  Adding baby
         */

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
            ref.child(Objects.requireNonNull(firebaseAuth.getUid())).child("children").child(name_string).setValue(baby)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //added to db
                        //progressDialog.dismiss();
                        Intent i = new Intent(Add_Child.this, MainActivity.class);
                        i.putExtra("baby_name",baby.getName());
                        i.putExtra("baby_gender",baby.getGender());

                        /**
                         *  Adding first scale
                         */
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(firebaseAuth.getUid())).child("children");
                        ref.child(name_string).child("scales").child(String.valueOf(first_scale.getAdj_age())).setValue(first_scale).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });

                        clearData();
                        startActivity(i);
                        Toast.makeText(Add_Child.this, "פרטי התינוק הושלמו", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //progressDialog.dismiss();
                        Toast.makeText(Add_Child.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void clearData() {
        // clear data after uploading
        name.setText("");
        week_number_of_birth.setText("");
        head.setText("");
        height.setText("");
        weight.setText("");
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_male:
                if (checked) {
                    Toast.makeText(Add_Child.this, "MALE PICKED.", Toast.LENGTH_LONG).show();
                    gender_string = "m";
                }
                break;
            case R.id.radio_female:
                if (checked){
                    Toast.makeText(Add_Child.this, "FEMALE PICKED.", Toast.LENGTH_LONG).show();
                    gender_string = "f";
                }
                break;
        }
    }
}