package com.example.vleague.Menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vleague.Activity.Details.Activity_Details_Match;
import com.example.vleague.DBHelper;
import com.example.vleague.Adapter.Adapter_Match;
import com.example.vleague.Class.Matches;
import com.example.vleague.MyDatabase_Match;
import com.example.vleague.R;

import java.util.ArrayList;

public class Fragment_Schedule extends Fragment {

    Spinner spinnerRound;
    ListView listSchedule;
    EditText edtSearch;
    View rootView;

    ArrayList<Matches> matchesArrayList = new ArrayList<>();
    MyDatabase_Match myDatabase_match;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        spinnerRound = (Spinner) rootView.findViewById(R.id.spinnerRound);
        listSchedule = (ListView) rootView.findViewById(R.id.lvMatchSchedule);
        edtSearch = (EditText) rootView.findViewById(R.id.edtSearch);

        myDatabase_match = new MyDatabase_Match(rootView.getContext());

        setRound();
        selectListView();

        return rootView;
    }

    public void selectListView() {
        listSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("Range")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
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

                            Intent intent = new Intent(rootView.getContext()
                                    , Activity_Details_Match.class);
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

    public void setRound() {
        ArrayList<String> rounds = new ArrayList<>();

        rounds.add("Vòng 1");
        rounds.add("Vòng 2");
        rounds.add("Vòng 3");
        rounds.add("Vòng 4");
        rounds.add("Vòng 5");
        rounds.add("Vòng 6");
        rounds.add("Vòng 7");
        rounds.add("Vòng 8");
        rounds.add("Vòng 9");
        rounds.add("Vòng 10");
        rounds.add("Vòng 11");
        rounds.add("Vòng 12");
        rounds.add("Vòng 13");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(rootView.getContext()
                , androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, rounds);

        spinnerRound.setAdapter(adapter);

        spinnerRound.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                for(int i = 0; i < spinnerRound.getCount(); i++) {
                    CapNhatDuLieu(id + 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @SuppressLint("Range")
    public void CapNhatDuLieu(long round) {
        if(matchesArrayList == null) {
            matchesArrayList = new ArrayList<Matches>();
        } else {
            matchesArrayList.removeAll(matchesArrayList);
        }

        Cursor cursor = myDatabase_match.LayTranDauTheoVong(round);

        if(cursor != null) {
            while (cursor.moveToNext()) {
                Matches matches = new Matches();

                matches.setId(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.ID_MATCH)
                ));
                matches.setLogo1(cursor.getString(
                        cursor.getColumnIndex(DBHelper.LOGO_1)
                ));
                matches.setLogo2(cursor.getString(
                        cursor.getColumnIndex(DBHelper.LOGO_2)
                ));
                matches.setName1(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NAME_1)
                ));
                matches.setName2(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NAME_2)
                ));
                matches.setDate(cursor.getString(
                        cursor.getColumnIndex(DBHelper.DATE_MATCH)
                ));
                matches.setTime(cursor.getString(
                        cursor.getColumnIndex(DBHelper.TIME_MATCH)
                ));
                matches.setRound(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.ROUND_MATCH)
                ));

                matchesArrayList.add(matches);
            }
        } else {
            Toast.makeText(rootView.getContext(), "Failed", Toast.LENGTH_SHORT).show();
        }

        setListView();
    }

    @SuppressLint("Range")
    public void search(String searchText) {
        if(matchesArrayList == null) {
            matchesArrayList = new ArrayList<Matches>();
        } else {
            matchesArrayList.removeAll(matchesArrayList);
        }

        Cursor cursor = myDatabase_match.searchMatches(searchText);

        if(cursor != null) {
            while (cursor.moveToNext()) {
                Matches matches = new Matches();

                matches.setId(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.ID_MATCH)
                ));
                matches.setLogo1(cursor.getString(
                        cursor.getColumnIndex(DBHelper.LOGO_1)
                ));
                matches.setLogo2(cursor.getString(
                        cursor.getColumnIndex(DBHelper.LOGO_2)
                ));
                matches.setName1(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NAME_1)
                ));
                matches.setName2(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NAME_2)
                ));
                matches.setDate(cursor.getString(
                        cursor.getColumnIndex(DBHelper.DATE_MATCH)
                ));
                matches.setTime(cursor.getString(
                        cursor.getColumnIndex(DBHelper.TIME_MATCH)
                ));
                matches.setRound(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.ROUND_MATCH)
                ));

                matchesArrayList.add(matches);
            }
        } else {
            Toast.makeText(rootView.getContext(), "Failed", Toast.LENGTH_SHORT).show();
        }

        setListView();
    }

    public void setListView() {
        if(matchesArrayList != null) {
            listSchedule.setAdapter(
                    new Adapter_Match(matchesArrayList)
            );
        }
    }
}