package com.example.vleague.Menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vleague.Activity.Details.Activity_Details_Match;
import com.example.vleague.Activity.Details.Activity_Details_News;
import com.example.vleague.Adapter.Adapter_Match;
import com.example.vleague.Adapter.Adapter_News;
import com.example.vleague.Class.Matches;
import com.example.vleague.Class.News;
import com.example.vleague.DBHelper;
import com.example.vleague.MyDatabase_Match;
import com.example.vleague.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Fragment_Home extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ListView listSchedule, listNew;
    ArrayList<Matches> matchesArrayList;
    ArrayList<News> newsArrayList;

    MyDatabase_Match myDatabase_match;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        listSchedule = (ListView) rootView.findViewById(R.id.lvHSchedule);
        listNew = (ListView) rootView.findViewById(R.id.lvHNews);

        myDatabase_match = new MyDatabase_Match(rootView.getContext());

        matchesArrayList = new ArrayList<>();
        newsArrayList = new ArrayList<>();

        CapNhatDuLieu();
        CapNhatDuLieuTinTuc();

        return rootView;
    }

    @SuppressLint("Range")
    public void CapNhatDuLieuTinTuc() {
        if(newsArrayList == null) {
            newsArrayList = new ArrayList<>();
        } else {
            newsArrayList.removeAll(newsArrayList);
        }

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String Date = String.format(Locale.US,"%02d/%02d/%d", day, month, year);
        Log.i("DEBUG", Date);

        Cursor cursor = myDatabase_match.LayTinTucTheoNgay(Date);

        if(cursor != null) {
            while (cursor.moveToNext()) {
                News news = new News();

                news.setId(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.NEWS_ID)
                ));
                news.setTitle(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NEWS_TITLE)
                ));
                news.setSummary(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NEWS_SUM)
                ));
                news.setBody(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NEWS_BODY)
                ));
                news.setDate(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NEWS_DATE)
                ));
                news.setNewsImg(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NEWS_IMG)
                ));

                newsArrayList.add(news);
            }
        } else {
            Toast.makeText(rootView.getContext(), "Failed", Toast.LENGTH_SHORT).show();
        }

        setListViewNews();
    }

    public void setListViewNews() {
        if(newsArrayList != null) {
            Adapter_News adapter_news = new Adapter_News(newsArrayList);
            listNew.setAdapter(adapter_news);
        } else {
            Log.i("DEBUG", "rong");
        }

        listNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News news = (News) adapterView.getItemAtPosition(i);

                Bundle bundle = new Bundle();
                bundle.putSerializable("news", news);
                Intent intent = new Intent(rootView.getContext(), Activity_Details_News.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("Range")
    public void CapNhatDuLieu() {
        if(matchesArrayList == null) {
            matchesArrayList = new ArrayList<Matches>();
        } else {
            matchesArrayList.removeAll(matchesArrayList);
        }

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String Date = String.format(Locale.US,"%02d/%02d/%d", day, month, year);
        Log.i("DEBUG", Date);

        Cursor cursor = myDatabase_match.LayDuLieuTheoNgay(Date);

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
            Adapter_Match adapter_match = new Adapter_Match(matchesArrayList);
            listSchedule.setAdapter(adapter_match);
            Log.i("DEBUG", String.valueOf(matchesArrayList));
        } else {
            Log.i("DEBUG", "rong");
        }

        selectListView();
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
}