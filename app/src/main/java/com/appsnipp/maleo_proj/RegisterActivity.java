package com.appsnipp.maleo_proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;

public class RegisterActivity extends AppCompatActivity {
    EditText mFullname, mEmail, mPassword, mConfirm;
    Button mRegisterbtn;
    ImageView google_sign, facebook_sign;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference mDatabase;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFullname = findViewById(R.id.et_username);
        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        mConfirm = findViewById(R.id.et_confirm_password);
        mRegisterbtn = findViewById(R.id.button_signup);



        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("user");



        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String confirm = mConfirm.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required.");
                    return;
                }

                if (!confirm.equals(password)) {
                    mConfirm.setError("incorrect password.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("password must be at least 6 characters.");
                }


                //connect to firebase

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            User u = new User(mFullname.getText().toString().trim(), mEmail.getText().toString().trim(), fAuth.getUid());
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(RegisterActivity.this, "user saved!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, " ERROR!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getApplicationContext(),register.class));
                        }
                    }
                });
            }
        });
    }


}