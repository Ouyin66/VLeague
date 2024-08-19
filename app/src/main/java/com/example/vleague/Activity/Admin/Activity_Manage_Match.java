package com.example.vleague.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vleague.Activity.AddnEdit.Activity_Add_Match;
import com.example.vleague.Activity.Details.Activity_Details_Admin_Match;
import com.example.vleague.Class.Team;
import com.example.vleague.Class.User;
import com.example.vleague.DBHelper;
import com.example.vleague.Adapter.Adapter_Match;
import com.example.vleague.Class.Matches;
import com.example.vleague.MyDatabase_Match;
import com.example.vleague.R;

import java.util.ArrayList;

public class Activity_Manage_Match extends AppCompatActivity {

    ListView listManageMatches;
    EditText edtSearch;
    ArrayList<Matches> matches = new ArrayList<>();
    MyDatabase_Match myDatabase_match;
    ImageButton btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_match);

        btnAdd = (ImageButton) findViewById(R.id.ibtnAdd);
        edtSearch = (EditText) findViewById(R.id.edtSearch);

        listManageMatches = (ListView) findViewById(R.id.listManageMatches);
        myDatabase_match = new MyDatabase_Match(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(Activity_Manage_Match.this, Activity_Add_Match.class);
                startActivity(add);
            }
        });

        CapNhatDuLieuChoMatches();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString();

                if(searchText.isEmpty()) {
                    CapNhatDuLieuChoMatches();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString();

                if(searchText.isEmpty()) {
                    CapNhatDuLieuChoMatches();
                } else {
                    Search(searchText);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listManageMatches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("Range")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Matches match = new Matches();

                Cursor cursor = myDatabase_match.LayTranDau(id);

                Log.i("DEBUG", String.valueOf(id));
                if(cursor != null && cursor.getCount() > 0
                        && cursor.moveToFirst()) {

                    do {
                        int idMatches = cursor.getInt(cursor.getColumnIndex(DBHelper.ID_MATCH));

                        if(idMatches == id) {
                            match.setId(cursor.getInt(
                                    cursor.getColumnIndex(DBHelper.ID_MATCH)
                            ));
                            match.setLogo1(cursor.getString(
                                    cursor.getColumnIndex(DBHelper.LOGO_1)
                            ));
                            match.setLogo2(cursor.getString(
                                    cursor.getColumnIndex(DBHelper.LOGO_2)
                            ));
                            match.setName1(cursor.getString(
                                    cursor.getColumnIndex(DBHelper.NAME_1)
                            ));
                            match.setName2(cursor.getString(
                                    cursor.getColumnIndex(DBHelper.NAME_2)
                            ));
                            match.setDate(cursor.getString(
                                    cursor.getColumnIndex(DBHelper.DATE_MATCH)
                            ));
                            match.setTime(cursor.getString(
                                    cursor.getColumnIndex(DBHelper.TIME_MATCH)
                            ));
                            match.setRound(cursor.getInt(
                                    cursor.getColumnIndex(DBHelper.ROUND_MATCH)
                            ));
                            match.setPrice(cursor.getInt(
                                    cursor.getColumnIndex(DBHelper.PRICE_MATCH)
                            ));
                            match.setLocation(cursor.getString(
                                    cursor.getColumnIndex(DBHelper.LOCATION_MATCH)));

                            Intent intent = new Intent(Activity_Manage_Match.this
                                    , Activity_Details_Admin_Match.class);
                            Bundle bundle = new Bundle();

                            bundle.putLong("id", match.getId());
                            bundle.putString("logo1", match.getLogo1());
                            bundle.putString("logo2", match.getLogo2());
                            bundle.putString("name1", match.getName1());
                            bundle.putString("name2", match.getName2());
                            bundle.putString("date", match.getDate());
                            bundle.putString("time", match.getTime());
                            bundle.putString("location", match.getLocation());
                            bundle.putInt("price", match.getPrice());
                            bundle.putInt("round", match.getRound());

                            intent.putExtra("GoiMatches" ,bundle);
                            startActivity(intent);

                            break;
                        }
                    } while (cursor.moveToNext());
                } else {
                    Log.i("DEBUG", "Hang ko co");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        CapNhatDuLieuChoMatches();
    }

    @SuppressLint("Range")
    public void Search(String nameUser) {
        if(matches == null) {
            matches = new ArrayList<Matches>();
        } else {
            matches.removeAll(matches);
        }

        String newText = nameUser;

        Cursor cursor = myDatabase_match.searchMatches(newText);

        if(cursor != null) {
            while (cursor.moveToNext()) {
                Matches match = new Matches();

                match.setId(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.ID_MATCH)
                ));
                match.setLogo1(cursor.getString(
                        cursor.getColumnIndex(DBHelper.LOGO_1)
                ));
                match.setLogo2(cursor.getString(
                        cursor.getColumnIndex(DBHelper.LOGO_2)
                ));
                match.setName1(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NAME_1)
                ));
                match.setName2(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NAME_2)
                ));
                match.setDate(cursor.getString(
                        cursor.getColumnIndex(DBHelper.DATE_MATCH)
                ));
                match.setTime(cursor.getString(
                        cursor.getColumnIndex(DBHelper.TIME_MATCH)
                ));
                match.setRound(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.ROUND_MATCH)
                ));

                matches.add(match);
            }
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }

        CapNhatListView();
    }

    //Lay du lieu gan vao cho arrayList
    @SuppressLint("Range")
    public void CapNhatDuLieuChoMatches() {
        if(matches == null) {
            matches = new ArrayList<>();
        } else {
            matches.removeAll(matches);
        }

        Cursor cursor = myDatabase_match.LayTatCaDuLieu();

        if(cursor != null) {
            while (cursor.moveToNext()) {
                Matches match = new Matches();

                match.setId(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.ID_MATCH)
                ));
                match.setLogo1(cursor.getString(
                        cursor.getColumnIndex(DBHelper.LOGO_1)
                ));
                match.setLogo2(cursor.getString(
                        cursor.getColumnIndex(DBHelper.LOGO_2)
                ));
                match.setName1(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NAME_1)
                ));
                match.setName2(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NAME_2)
                ));
                match.setDate(cursor.getString(
                        cursor.getColumnIndex(DBHelper.DATE_MATCH)
                ));
                match.setTime(cursor.getString(
                        cursor.getColumnIndex(DBHelper.TIME_MATCH)
                ));

                matches.add(match);
            }
        }
        cursor.close();
        CapNhatListView();
        listManageMatches.invalidateViews();
    }

    //Gan danh sachs moi vao cho list view
    public void CapNhatListView() {
        if(matches != null) {
            listManageMatches.setAdapter(new Adapter_Match(matches));
        }
    }

    public void btnBack15_onClick(View view) {
        finish();
    }
}