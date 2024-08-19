package com.example.vleague.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.vleague.Activity.AddnEdit.Activity_Add_Credit_Card;
import com.example.vleague.Activity.AddnEdit.Activity_Add_Location;
import com.example.vleague.Activity.AddnEdit.Activity_Edit_Credit_Card;
import com.example.vleague.R;

public class Activity_Manage_Credit_Card extends AppCompatActivity {

    ImageButton btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_credit_card);

        btnAdd = (ImageButton) findViewById(R.id.ibtnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(Activity_Manage_Credit_Card.this, Activity_Add_Credit_Card.class);
                startActivity(add);
            }
        });
    }

    public void btnBack12_onClick(View view) {
        finish();
    }
}