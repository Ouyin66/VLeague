package com.example.vleague.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.vleague.Activity.AddnEdit.Activity_Add_News;
import com.example.vleague.Activity.AddnEdit.Activity_Edit_Location;
import com.example.vleague.Activity.AddnEdit.Activity_Edit_News;
import com.example.vleague.Activity.Details.Activity_Details_Admin_News;
import com.example.vleague.Adapter.Adapter_News;
import com.example.vleague.Class.News;
import com.example.vleague.DBHelper;
import com.example.vleague.NewsDatabase;
import com.example.vleague.R;

import java.util.ArrayList;
import java.util.Currency;

public class Activity_Manage_News extends AppCompatActivity {

    ImageButton btnAdd;
    NewsDatabase newsDatabase;
    ArrayList<News> newsArrayList;
    ListView listNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_news);

        btnAdd = (ImageButton) findViewById(R.id.ibtnAdd);
        listNews = (ListView) findViewById(R.id.listNews);

        newsDatabase = new NewsDatabase(this);
        newsArrayList = new ArrayList<>();

        CapNhatDuLieu();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(Activity_Manage_News.this, Activity_Add_News.class);
                startActivity(add);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        CapNhatDuLieu();
    }

    @SuppressLint("Range")
    public void CapNhatDuLieu() {
        if(newsArrayList == null) {
            newsArrayList = new ArrayList<>();
        } else {
            newsArrayList.removeAll(newsArrayList);
        }

        Cursor cursor = newsDatabase.HienThiTatCa();

        if(cursor != null) {
            while (cursor.moveToNext()) {
                News news = new News();

                news.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.NEWS_ID)));
                news.setNewsImg(cursor.getString(cursor.getColumnIndex(DBHelper.NEWS_IMG)));
                news.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.NEWS_TITLE)));
                news.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.NEWS_DATE)));
                news.setSummary(cursor.getString(cursor.getColumnIndex(DBHelper.NEWS_SUM)));
                news.setBody(cursor.getString(cursor.getColumnIndex(DBHelper.NEWS_BODY)));

                newsArrayList.add(news);
            }
        }

        SetListView();
    }

    public void SetListView() {
        if(newsArrayList != null) {
            Adapter_News adapter_news = new Adapter_News(newsArrayList);
            listNews.setAdapter(adapter_news);
        }

        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News news = (News) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(Activity_Manage_News.this
                        , Activity_Details_Admin_News.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("news", news);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    public void btnBack16_onClick(View view) {
        finish();
    }
}