package com.example.vleague.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.vleague.MainActivity;
import com.example.vleague.MyDatabase_User;
import com.example.vleague.R;
import com.example.vleague.UserSession;

public class Activity_Manage extends AppCompatActivity {
    ImageButton mbtnCustomer, mbtnNews, mbtnMatch,
            mbtnOTicket, mbtnLocation, mbtnTeam;

    Button mbtnExit;

    TextView tvNameAdmin, tvPhoneAdmin;

    MyDatabase_User myDatabase_user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        mbtnCustomer = (ImageButton) findViewById(R.id.ibtnCustomer);
        mbtnNews = (ImageButton) findViewById(R.id.ibtnNews);
        mbtnMatch = (ImageButton) findViewById(R.id.ibtnMatch);
        mbtnOTicket = (ImageButton) findViewById(R.id.ibtnOTicket);
        mbtnLocation = (ImageButton) findViewById(R.id.ibtnLocation);
        mbtnTeam = (ImageButton) findViewById(R.id.ibtnTeam);
        tvNameAdmin = (TextView) findViewById(R.id.tvName);
        tvPhoneAdmin = (TextView) findViewById(R.id.tvPhoneNum);
        mbtnExit = (Button) findViewById(R.id.btnExit);
        myDatabase_user = new MyDatabase_User(this);

        String email = getIntent().getStringExtra("email");

        String[] adminInfo = myDatabase_user.getAdminInfo(email);

        tvNameAdmin.setText(adminInfo[0]);
        tvPhoneAdmin.setText(adminInfo[1]);

        // Đến trang quản lý khách hàng
        mbtnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mCustomer = new Intent(Activity_Manage.this, Activity_Manage_Customer.class);
                startActivity(mCustomer);
            }
        });

        // Đến trang quản lý tin tức
        mbtnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mNews = new Intent(Activity_Manage.this, Activity_Manage_News.class);
                startActivity(mNews);
            }
        });

        // Đến trang quản lý trận đấu
        mbtnMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mMatch = new Intent(Activity_Manage.this, Activity_Manage_Match.class);
                startActivity(mMatch);
            }
        });

        // Đến trang quản lý đơn đặt vé
        mbtnOTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mOTicket = new Intent(Activity_Manage.this, Activity_Manage_Ticket.class);
                startActivity(mOTicket);
            }
        });

        // Đến trang quản lý địa điểm
        mbtnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mLocation = new Intent(Activity_Manage.this, Activity_Manage_Location.class);
                startActivity(mLocation);
            }
        });

        // Đến trang quản lý đội bóng
        mbtnTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mTeam = new Intent(Activity_Manage.this, Activity_Manage_Team.class);
                startActivity(mTeam);
            }
        });


        mbtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSession.isLogged = false;

                Intent intent = new Intent(Activity_Manage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}