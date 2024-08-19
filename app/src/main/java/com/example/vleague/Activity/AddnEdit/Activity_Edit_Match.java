package com.example.vleague.Activity.AddnEdit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

public class Activity_Edit_Match extends AppCompatActivity {

    Spinner spinnerTeam1, spinnerTeam2, spinnerLocation, spinnerRound;
    ImageView logoTeam1, logoTeam2;
    EditText edtDate, edtPrice, edtTime;
    Button btnEditMatch;
    ArrayList<String> Round = new ArrayList<>();

    String date, time, name1, name2, location;
    int price, round;
    String logo1, logo2;

    private long id = -1;
    String NameTeam1, NameTeam2;
    String logoLink1, logoLink2;
    MyDatabase_Match myDatabase_match;
    MyDatabase_Location myDatabase_location;
    MyDatabase_team myDatabase_team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_match);

        spinnerTeam1 = (Spinner) findViewById(R.id.spinnerTeam1);
        spinnerTeam2 = (Spinner) findViewById(R.id.spinnerTeam2);
        spinnerLocation = (Spinner) findViewById(R.id.spinnerLocation);
        spinnerRound = (Spinner) findViewById(R.id.spinnerVong);
        logoTeam1 = (ImageView) findViewById(R.id.logo1);
        logoTeam2 = (ImageView) findViewById(R.id.logo2);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        edtTime = (EditText) findViewById(R.id.edtTime);
        btnEditMatch = (Button) findViewById(R.id.btnEditMatch);
        myDatabase_match = new MyDatabase_Match(this);
        myDatabase_location = new MyDatabase_Location(this);
        myDatabase_team = new MyDatabase_team(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("GoiEditMatches");

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



        setLogoForSpinner();
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Edit_Match.this,
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

        for (int i = 0; i < spinnerTeam1.getCount(); i++) {
            Team item = (Team) spinnerTeam1.getItemAtPosition(i);

            if (item.getName().equals(name1)) {
                spinnerTeam1.setSelection(i);
            }
        }

        for (int i = 0; i < spinnerTeam2.getCount(); i++) {
            Team item = (Team) spinnerTeam2.getItemAtPosition(i);

            if (item.getName().equals(name2)) {
                spinnerTeam2.setSelection(i);
            }
        }

        edtDate.setText(date);
        edtTime.setText(time);
        edtPrice.setText(String.valueOf(price));

        for (int i = 0; i < spinnerRound.getCount(); i++) {
            String item = (String) spinnerRound.getItemAtPosition(i);

            if (item.equals("Vòng " + round)) {
                spinnerRound.setSelection(i);
                break;
            }
        }

        Log.i("DEBUG", location);

        for (int i = 0; i < spinnerLocation.getCount(); i++) {
            String item = spinnerLocation.getItemAtPosition(i).toString();

            if (item.equals(location)) {
                spinnerLocation.setSelection(i);
                break;
            }
        }

        btnEditMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String time = edtTime.getText().toString();
                String priceInput = edtPrice.getText().toString();

                if(time.isEmpty() || priceInput.isEmpty()) {
                    Toast.makeText(Activity_Edit_Match.this,
                            "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    Matches matches = LayDuLieuMatch();

                    if(matches.getName1().equals(matches.getName2())) {
                        Toast.makeText(Activity_Edit_Match.this,
                                "Tên đội bóng bị trùng", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_Edit_Match.this);

                    dialog.setTitle("Thông báo");
                    dialog.setMessage("Bạn có chắc chắn sửa không?");

                    dialog.setPositiveButton("Chắc chắn", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(matches != null && id != -1) {
                                myDatabase_match.suaTranDau(matches);

                                finish();
                            }
                        }
                    });

                    dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
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

    public Matches LayDuLieuMatch() {
        logo1 = logoLink1;
        logo2 = logoLink2;
        date = edtDate.getText().toString();
        time = edtTime.getText().toString();
        price = Integer.parseInt(edtPrice.getText().toString());
        //round
        //location

        Matches match = new Matches();

        match.setId(id);
        match.setLogo1(logo1);
        match.setLogo2(logo2);
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

    public void btnBack8_onClick(View view) {
        finish();
    }
}