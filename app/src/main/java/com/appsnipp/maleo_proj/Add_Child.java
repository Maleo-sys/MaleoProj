package com.appsnipp.maleo_proj;

import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;


import androidx.appcompat.app.AppCompatActivity;

public class Add_Child extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
//        setAmbientEnabled();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_male:
                if (checked)
                    Toast.makeText(Add_Child.this, "MALE PICKED.", Toast.LENGTH_LONG).show();
                break;
            case R.id.radio_female:
                if (checked)
                    Toast.makeText(Add_Child.this, "FEMALE PICKED.", Toast.LENGTH_LONG).show();
                break;
        }
    }
}