package com.example.vleague.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.vleague.R;

public class Activity_Bank_Link extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_link);
    }

    public void btnBack25_onClick(View view) {
        finish();
    }
}