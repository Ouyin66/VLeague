package com.example.vleague.Activity.Details;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vleague.Activity.AddnEdit.Activity_Edit_Match;
import com.example.vleague.DBHelper;
import com.example.vleague.Class.Matches;
import com.example.vleague.MyDatabase_Match;
import com.example.vleague.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class Activity_Details_Admin_Match extends AppCompatActivity {

    TextView tvName1, tvName2, tvDiaDiem
            , tvDate, tvTime, tvRound, tvPrice;
    ImageButton btnDelete;
    Button btnEdit;
    ImageView Logo1, Logo2;
    MyDatabase_Match myDatabase_match;

    String name1, name2, location, date, time;
    String logo1, logo2;
    int round, price;
    long id;

    Matches matches = new Matches();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_admin_match);

        btnDelete = (ImageButton) findViewById(R.id.btnDelete);
        btnEdit = (Button) findViewById(R.id.btnEdit);
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
        tvName1 = (TextView) findViewById(R.id.tvTeam1);
        tvName2 = (TextView) findViewById(R.id.tvTeam2);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvDiaDiem = (TextView) findViewById(R.id.tvLocation);
        tvRound = (TextView) findViewById(R.id.tvRound);
        tvPrice = (TextView) findViewById(R.id.tvPrice);

//        Logo1.setImageResource(logo1);
//        Logo2.setImageResource(logo2);

        Picasso.get().load(logo1).into(Logo1);
        Picasso.get().load(logo2).into(Logo2);

        tvName1.setText(name1);
        tvName2.setText(name2);
        tvDate.setText(date);
        tvTime.setText(time);
        tvPrice.setText(formatPrice(price));
        tvDiaDiem.setText(location);
        tvRound.setText("Vòng " + String.valueOf(round));

        matches.setId(id);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabase_match.xoaTranDau(matches);

                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Details_Admin_Match.this
                        , Activity_Edit_Match.class);
                Bundle bundle1 = new Bundle();

                bundle1.putLong("id", id);
                bundle1.putString("logo1", logo1);
                bundle1.putString("logo2", logo2);
                bundle1.putString("name1", name1);
                bundle1.putString("name2", name2);
                bundle1.putString("date", date);
                bundle1.putString("time", time);
                bundle1.putString("location", location);
                bundle1.putInt("price", price);
                bundle1.putInt("round", round);

                intent.putExtra("GoiEditMatches" ,bundle1);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        CapNhatThongTinMoi();
    }

    public String formatPrice(int price) {
        String priceInput = String.valueOf(price);
        double priceValue = Double.parseDouble(priceInput);
        DecimalFormat decimalFormat = new DecimalFormat("###,### VNĐ");
        String priceOutput = decimalFormat.format(priceValue);

        return priceOutput;
    }

    @SuppressLint("Range")
    public void CapNhatThongTinMoi() {
        Matches matches1 = new Matches();
        Cursor cursor = myDatabase_match.LayTranDau(id);

        if(cursor != null && cursor.getCount() > 0
                && cursor.moveToFirst()) {

            do {
                @SuppressLint("Range") int idMatches = cursor.getInt(cursor.getColumnIndex(DBHelper.ID_MATCH));

                if(idMatches == id) {
                    matches1.setId(cursor.getInt(
                            cursor.getColumnIndex(DBHelper.ID_MATCH)
                    ));
                    matches1.setLogo1(cursor.getString(
                            cursor.getColumnIndex(DBHelper.LOGO_1)
                    ));
                    matches1.setLogo2(cursor.getString(
                            cursor.getColumnIndex(DBHelper.LOGO_2)
                    ));
                    matches1.setName1(cursor.getString(
                            cursor.getColumnIndex(DBHelper.NAME_1)
                    ));
                    matches1.setName2(cursor.getString(
                            cursor.getColumnIndex(DBHelper.NAME_2)
                    ));
                    matches1.setDate(cursor.getString(
                            cursor.getColumnIndex(DBHelper.DATE_MATCH)
                    ));
                    matches1.setTime(cursor.getString(
                            cursor.getColumnIndex(DBHelper.TIME_MATCH)
                    ));
                    matches1.setRound(cursor.getInt(
                            cursor.getColumnIndex(DBHelper.ROUND_MATCH)
                    ));
                    matches1.setPrice(cursor.getInt(
                            cursor.getColumnIndex(DBHelper.PRICE_MATCH)
                    ));
                    matches1.setLocation(cursor.getString(
                            cursor.getColumnIndex(DBHelper.LOCATION_MATCH)));
                    break;
                }
            } while (cursor.moveToNext());

//            Logo1.setImageResource(matches1.getLogo1());
//            Logo2.setImageResource(matches1.getLogo2());

            Picasso.get().load(matches1.getLogo1()).into(Logo1);
            Picasso.get().load(matches1.getLogo2()).into(Logo2);
            tvName1.setText(matches1.getName1());
            tvName2.setText(matches1.getName2());
            tvDate.setText(matches1.getDate());
            tvTime.setText(matches1.getTime());
            tvPrice.setText((String.valueOf(matches1.getPrice() / 1000)) + ".000 VND");
            tvDiaDiem.setText(location);
            tvRound.setText(String.valueOf(matches1.getRound()));
        } else {
            Log.i("DEBUG", "Hang ko co");
        }
    }

    public void btnBack19_onClick(View view) {
        finish();
    }
}