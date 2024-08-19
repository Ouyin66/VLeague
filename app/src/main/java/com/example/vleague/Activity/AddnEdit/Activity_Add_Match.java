package com.example.vleague.Activity.AddnEdit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vleague.Activity.Admin.Activity_Manage_Match;
import com.example.vleague.Adapter.AdapterSpinnerTeam;
import com.example.vleague.Class.Team;
import com.example.vleague.DBHelper;
import com.example.vleague.Class.Location;
import com.example.vleague.Class.Matches;
import com.example.vleague.MyDatabase_Location;
import com.example.vleague.MyDatabase_Match;
import com.example.vleague.MyDatabase_team;
import com.example.vleague.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class Activity_Add_Match extends AppCompatActivity {

    Spinner spinnerTeam1, spinnerTeam2, spinnerLocation, spinnerRound;
    ImageView logoTeam1, logoTeam2;
    EditText edtDate, edtPrice, edtTime;
    Button btnAddMatch;
    ArrayList<String> Round = new ArrayList<>();

    String date, time, name1, name2, location;
    int price, round, logo1, logo2;

    private long id = -1;
    String NameTeam1, NameTeam2;
    String logoLink1, logoLink2;
    MyDatabase_Match myDatabase_match;
    MyDatabase_Location myDatabase_location;
    MyDatabase_team myDatabase_team;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);

        spinnerTeam1 = (Spinner) findViewById(R.id.spinnerTeam1);
        spinnerTeam2 = (Spinner) findViewById(R.id.spinnerTeam2);
        spinnerLocation = (Spinner) findViewById(R.id.spinnerLocation);
        spinnerRound = (Spinner) findViewById(R.id.spinnerVong);
        logoTeam1 = (ImageView) findViewById(R.id.logo1);
        logoTeam2 = (ImageView) findViewById(R.id.logo2);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        edtTime = (EditText) findViewById(R.id.edtTime);
        btnAddMatch = (Button) findViewById(R.id.btnAddMatch);
        myDatabase_match = new MyDatabase_Match(this);
        myDatabase_location = new MyDatabase_Location(this);
        myDatabase_team = new MyDatabase_team(this);

        setLogoForSpinner();

//        spinnerTeam1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                switch (position) {
//                    case 0:
//                        logoTeam1.setImageResource(R.drawable.hagl);
//                        logoLink1 = R.drawable.hagl;
//                        NameTeam1 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 1:
//                        logoTeam1.setImageResource(R.drawable.khanhhoa);
//                        logoLink1 = R.drawable.khanhhoa;
//                        NameTeam1 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 2:
//                        logoTeam1.setImageResource(R.drawable.namdinh);
//                        logoLink1 = R.drawable.namdinh;
//                        NameTeam1 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 3:
//                        logoTeam1.setImageResource(R.drawable.songlamnghean);
//                        logoLink1 = R.drawable.songlamnghean;
//                        NameTeam1 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 4:
//                        logoTeam1.setImageResource(R.drawable.binhduong);
//                        logoLink1 = R.drawable.binhduong;
//                        NameTeam1 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 5:
//                        logoTeam1.setImageResource(R.drawable.cahn);
//                        logoLink1 = R.drawable.cahn;
//                        NameTeam1 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 6:
//                        logoTeam1.setImageResource(R.drawable.danang);
//                        logoLink1 = R.drawable.danang;
//                        NameTeam1 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 7:
//                        logoTeam1.setImageResource(R.drawable.viettelfc);
//                        logoLink1 = R.drawable.viettelfc;
//                        NameTeam1 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 8:
//                        logoTeam1.setImageResource(R.drawable.thanhhoa);
//                        logoLink1 = R.drawable.thanhhoa;
//                        NameTeam1 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 9:
//                        logoTeam1.setImageResource(R.drawable.honglinhhatinh);
//                        logoLink1 = R.drawable.honglinhhatinh;
//                        NameTeam1 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 10:
//                        logoTeam1.setImageResource(R.drawable.tphcm);
//                        logoLink1 = R.drawable.tphcm;
//                        NameTeam1 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 11:
//                        logoTeam1.setImageResource(R.drawable.binhdinh);
//                        logoLink1 = R.drawable.binhdinh;
//                        NameTeam1 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 12:
//                        logoTeam1.setImageResource(R.drawable.hanoi);
//                        logoLink1 = R.drawable.hanoi;
//                        NameTeam1 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 13:
//                        logoTeam1.setImageResource(R.drawable.haiphong);
//                        logoLink1 = R.drawable.haiphong;
//                        NameTeam1 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        spinnerTeam2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                switch (position) {
//                    case 0:
//                        logoTeam2.setImageResource(R.drawable.hagl);
//                        logoLink2 = R.drawable.hagl;
//                        NameTeam2 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 1:
//                        logoTeam2.setImageResource(R.drawable.khanhhoa);
//                        logoLink2 = R.drawable.khanhhoa;
//                        NameTeam2 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 2:
//                        logoTeam2.setImageResource(R.drawable.namdinh);
//                        logoLink2 = R.drawable.namdinh;
//                        NameTeam2 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 3:
//                        logoTeam2.setImageResource(R.drawable.songlamnghean);
//                        logoLink2 = R.drawable.songlamnghean;
//                        NameTeam2 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 4:
//                        logoTeam2.setImageResource(R.drawable.binhduong);
//                        logoLink2 = R.drawable.binhduong;
//                        NameTeam2 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 5:
//                        logoTeam2.setImageResource(R.drawable.cahn);
//                        logoLink2 = R.drawable.cahn;
//                        NameTeam2 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 6:
//                        logoTeam2.setImageResource(R.drawable.danang);
//                        logoLink2 = R.drawable.danang;
//                        NameTeam2 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 7:
//                        logoTeam2.setImageResource(R.drawable.viettelfc);
//                        logoLink2 = R.drawable.viettelfc;
//                        NameTeam2 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 8:
//                        logoTeam2.setImageResource(R.drawable.thanhhoa);
//                        logoLink2 = R.drawable.thanhhoa;
//                        NameTeam2 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 9:
//                        logoTeam2.setImageResource(R.drawable.honglinhhatinh);
//                        logoLink2 = R.drawable.honglinhhatinh;
//                        NameTeam2 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 10:
//                        logoTeam2.setImageResource(R.drawable.tphcm);
//                        logoLink2 = R.drawable.tphcm;
//                        NameTeam2 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 11:
//                        logoTeam2.setImageResource(R.drawable.binhdinh);
//                        logoLink2 = R.drawable.binhdinh;
//                        NameTeam2 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 12:
//                        logoTeam2.setImageResource(R.drawable.hanoi);
//                        logoLink2 = R.drawable.hanoi;
//                        NameTeam2 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                    case 13:
//                        logoTeam2.setImageResource(R.drawable.haiphong);
//                        logoLink2 = R.drawable.haiphong;
//                        NameTeam2 = adapterView.getItemAtPosition(position).toString();
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        setLocationForSpinner();
        setRoundForSpinner();

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                //Khởi tạo datePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Add_Match.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month + 1;

                                String selectedDate =
                                        String.format("%02d/%02d/%04d", day, month, year);

                                edtDate.setText(selectedDate);
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });

        btnAddMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date = edtDate.getText().toString();
                String time = edtTime.getText().toString();
                String price = edtPrice.getText().toString();

                if(date.isEmpty() || time.isEmpty() || price.isEmpty()) {
                    Toast.makeText(Activity_Add_Match.this,
                            "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Matches match = LayDuLieuMatch();

                    if(match.getName1().equals(match.getName2())) {
                        Toast.makeText(Activity_Add_Match.this,
                                "Tên đội bóng bị trùng", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    boolean TrungLap = myDatabase_match.TruyVanTrungLapTranDau(match.getName1()
                            , match.getName2(), match.getTime(), match.getDate()
                            , match.getLocation(), match.getRound());

                    if (TrungLap) {
                        Toast.makeText(Activity_Add_Match.this,
                                "Đã tồn tại trận đấu này ở vòng này", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(match != null) {
                        if(myDatabase_match.AddMatch(match) != -1) {
                            edtDate = null;
                            edtTime = null;
                            edtPrice = null;
                            id = -1;

                            Toast.makeText(Activity_Add_Match.this, "Success", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                        Toast.makeText(Activity_Add_Match.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public Matches LayDuLieuMatch() {
//        logo1 = logoLink1;
//        logo2 = logoLink2;
//        name1 = NameTeam1;
//        name2 = NameTeam2;
        date = edtDate.getText().toString();
        time = edtTime.getText().toString();
        price = Integer.parseInt(edtPrice.getText().toString());
        //round
        //location

        Matches match = new Matches();

        match.setId(id);
        match.setLogo1(logoLink1);
        match.setLogo2(logoLink2);
        match.setName1(name1);
        match.setName2(name2);
        match.setDate(date);
        match.setLocation(location);
        match.setTime(time);
        match.setPrice(price);
        match.setRound(round);

        return match;
    }

    @SuppressLint("Range")
    public void setLogoForSpinner() {
        ArrayList<Team> teams = new ArrayList<>();

        Cursor cursor = myDatabase_team.LayTatCaDuLieu();

        if(cursor != null) {
            while (cursor.moveToNext()) {
                Team team = new Team();

                team.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.TEAM_ID)));
                team.setLogo(cursor.getString(
                        cursor.getColumnIndex(DBHelper.TEAM_IMG)
                ));
                team.setName(cursor.getString(
                        cursor.getColumnIndex(DBHelper.TEAM_NAME)
                ));

                teams.add(team);
            }
        }

        AdapterSpinnerTeam adapter = new AdapterSpinnerTeam(teams);

        spinnerTeam1.setAdapter(adapter);
        spinnerTeam2.setAdapter(adapter);

        spinnerTeam1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                Cursor cursor1 = myDatabase_team.LayTeamTheoId(id);
                Log.i("DEBUG", String.valueOf(id));

                if(cursor1 != null) {
                    while (cursor1.moveToNext()) {
                        Team team = new Team();

                        team.setId(cursor1.getInt(cursor1.getColumnIndex(DBHelper.TEAM_ID)));
                        team.setLogo(cursor1.getString(cursor1.getColumnIndex(DBHelper.TEAM_IMG)));
                        team.setName(cursor1.getString(cursor1.getColumnIndex(DBHelper.TEAM_NAME)));

                        Picasso.get().load(team.getLogo()).into(logoTeam1);
                        logoLink1 = team.getLogo();
                        name1 = team.getName();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerTeam2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                Cursor cursor2 = myDatabase_team.LayTeamTheoId(id);

                if(cursor2 != null) {
                    while (cursor2.moveToNext()) {
                        Team team = new Team();

                        team.setId(cursor2.getInt(cursor2.getColumnIndex(DBHelper.TEAM_ID)));
                        team.setLogo(cursor2.getString(cursor2.getColumnIndex(DBHelper.TEAM_IMG)));
                        team.setName(cursor2.getString(cursor2.getColumnIndex(DBHelper.TEAM_NAME)));

                        Picasso.get().load(team.getLogo()).into(logoTeam2);
                        logoLink2 = team.getLogo();
                        name2 = team.getName();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @SuppressLint("Range")
    public void setLocationForSpinner() {
//        Location.add("SVD Hàng Đẫy");
//        Location.add("SVD Lạch Tray");
//        Location.add("SVD Thiên Trường");
//        Location.add("SVD Thanh Hóa");
//        Location.add("SVD Vinh");
//        Location.add("SVD Hà Tĩnh");
//        Location.add("SVD Hòa Xuân");
//        Location.add("SVD Quy Nhơn");
//        Location.add("SVD PleiKu");
//        Location.add("SVD Nha Trang");
//        Location.add("SVD Gò Đậu");
//        Location.add("SVD Thống Nhất");

        ArrayList<String> locations = new ArrayList<>();

        Cursor cursor = myDatabase_match.LayDiaDiem();

        if(cursor != null) {
            while (cursor.moveToNext()) {
                Location location1 = new Location();

                location1.setLocation_Name(cursor.getString(
                        cursor.getColumnIndex(DBHelper.LOCATION_NAME)
                ));
                location1.setLocation_Total_Seat(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.LOCATION_TOTAL_SEAT)
                ));

                locations.add(location1.getLocation_Name());
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, locations);

        spinnerLocation.setAdapter(adapter);

        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                location = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setRoundForSpinner() {
        Round.add("Vòng 1");
        Round.add("Vòng 2");
        Round.add("Vòng 3");
        Round.add("Vòng 4");
        Round.add("Vòng 5");
        Round.add("Vòng 6");
        Round.add("Vòng 7");
        Round.add("Vòng 8");
        Round.add("Vòng 9");
        Round.add("Vòng 10");
        Round.add("Vòng 11");
        Round.add("Vòng 12");
        Round.add("Vòng 13");

        ArrayAdapter adapter = new ArrayAdapter(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Round);

        spinnerRound.setAdapter(adapter);

        spinnerRound.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                round = Math.toIntExact(id + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void btnBack2_onClick(View view) {
        finish();
    }
}