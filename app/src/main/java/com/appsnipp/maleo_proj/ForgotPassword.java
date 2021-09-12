package com.appsnipp.maleo_proj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }
}