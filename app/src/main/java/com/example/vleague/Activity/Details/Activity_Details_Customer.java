package com.example.vleague.Activity.Details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.vleague.R;

public class Activity_Details_Customer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_customer);
    }

    public void btnBack21_onClick(View view) {
        finish();
    }
}