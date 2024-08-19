package com.example.vleague.Menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.vleague.Activity.Admin.Activity_Manage_News;
import com.example.vleague.Activity.Details.Activity_Details_Admin_News;
import com.example.vleague.Activity.Details.Activity_Details_News;
import com.example.vleague.Adapter.Adapter_News;
import com.example.vleague.Class.News;
import com.example.vleague.DBHelper;
import com.example.vleague.NewsDatabase;
import com.example.vleague.R;

import java.util.ArrayList;

public class Fragment_News extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View rootView;
    NewsDatabase newsDatabase;
    ArrayList<News> newsArrayList;
    ListView listNews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news, container, false);

        listNews = (ListView) rootView.findViewById(R.id.lvMNews);

        newsDatabase = new NewsDatabase(rootView.getContext());
        newsArrayList = new ArrayList<>();

        CapNhatDuLieu();

        // Inflate the layout for this fragment
        return rootView;
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

                Intent intent = new Intent(rootView.getContext()
                        , Activity_Details_News.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("news", news);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}