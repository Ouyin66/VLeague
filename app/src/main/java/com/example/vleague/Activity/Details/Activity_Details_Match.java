package com.example.vleague.Activity.Details;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vleague.Activity.Activity_Confirm_Ticket;
import com.example.vleague.Activity.Activity_Login;
import com.example.vleague.Activity.Activity_SignUp;
import com.example.vleague.Activity.AddnEdit.Activity_Edit_Match;
import com.example.vleague.Class.Matches;
import com.example.vleague.Class.Ticket;
import com.example.vleague.DBHelper;
import com.example.vleague.MyDatabase_Match;
import com.example.vleague.R;
import com.example.vleague.UserSession;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Activity_Details_Match extends AppCompatActivity {

    TextView tvName1, tvName2, tvDiaDiem
            , tvDate, tvTime, tvRound, tvPrice;
    ImageView Logo1, Logo2;
    Button btnBookticket2;
    MyDatabase_Match myDatabase_match;

    String name1, name2, location, date, time;
    String logo1, logo2;
    int round, price;
    long id;

    Matches matches = new Matches();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_match);

        btnBookticket2 = (Button) findViewById(R.id.btnBookTickets2);

        myDatabase_match = new MyDatabase_Match(this);

        Intent goiIntent = getIntent();
        Bundle bundle = goiIntent.getBundleExtra("GoiMatches");

        id = bundle.getLong("id");
        name1 = bundle.getString("name1");
        name2 = bundle.getString("name2");
        logo1 = bundle.getString("logo1");
        logo2 = bundle.getString("logo2");
        location = bundle.getString("location");
        date = bundle.getString("date");
        time = bundle.getString("time");
        round = bundle.getInt("round");
        price = bundle.getInt("price");

        Logo1 = (ImageView) findViewById(R.id.imgLogo1);
        Logo2 = (ImageView) findViewById(R.id.imgLogo2);
        tvName1 = (TextView) findViewById(R.id.tvNTeam1);
        tvName2 = (TextView) findViewById(R.id.tvNTeam2);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvDiaDiem = (TextView) findViewById(R.id.tvLocation);
        tvRound = (TextView) findViewById(R.id.tvRound);

//        Logo1.setImageResource(logo1);
//        Logo2.setImageResource(logo2);
        Picasso.get().load(logo1).into(Logo1);
        Picasso.get().load(logo2).into(Logo2);

        tvName1.setText(name1);
        tvName2.setText(name2);
        tvDate.setText(date);
        tvTime.setText(time);
        tvDiaDiem.setText(location);
        tvRound.setText("Vòng " + String.valueOf(round));

        matches.setId(id);

        btnBookticket2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(UserSession.isLogged) {
                    Intent intent = new Intent(Activity_Details_Match.this
                            , Activity_Confirm_Ticket.class);
                    Bundle bundle = new Bundle();

                    bundle.putLong("id", id);
                    bundle.putString("logo1", logo1);
                    bundle.putString("logo2", logo2);
                    bundle.putString("name1", name1);
                    bundle.putString("name2", name2);
                    bundle.putString("date", date);
                    bundle.putString("time", time);
                    bundle.putString("location", location);
                    bundle.putInt("price", price);
                    bundle.putInt("round", round);

                    intent.putExtra("GoiMatches" ,bundle);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_Details_Match.this);
                    dialog.setTitle("Thông báo");
                    dialog.setMessage("Bạn cần phải đăng nhập để có thể đặt vé!");

                    dialog.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Activity_Details_Match.this
                                    , Activity_Login.class);
                            startActivity(intent);
                        }
                    });
                    dialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    dialog.create();
                    dialog.show();
                }
            }
        });
    }

    public void btnBack22_onClick(View view) {
        finish();
    }
}