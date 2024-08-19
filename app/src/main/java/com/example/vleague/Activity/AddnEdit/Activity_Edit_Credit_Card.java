package com.example.vleague.Activity.AddnEdit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.vleague.R;

public class Activity_Edit_Credit_Card extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_credit_card);
    }

    public void btnBack5_onClick(View view) {
        finish();
    }
}