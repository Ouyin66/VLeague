package com.example.vleague.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vleague.Class.News;
import com.example.vleague.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Adapter_News extends BaseAdapter {
    ArrayList<News> newsArrayList;

    public Adapter_News(ArrayList<News> newsArrayList) {
        this.newsArrayList = newsArrayList;
    }

    @Override
    public int getCount() {
        return newsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return newsArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return newsArrayList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = View.inflate(viewGroup.getContext(), R.layout.layout_listview_news, null);

        ImageView imgNews = (ImageView) rootView.findViewById(R.id.imageNew);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        TextView tvSum = (TextView) rootView.findViewById(R.id.tvSummary);
        TextView tvDate = (TextView) rootView.findViewById(R.id.tvDate);

        News news = (News) getItem(i);

//        imgNews.setImageResource(news.getNewsImg());
        Picasso.get().load(news.getNewsImg()).into(imgNews);
        tvTitle.setText(news.getTitle());
        tvSum.setText(news.getSummary());
        tvDate.setText(news.getDate());

        return rootView;
    }
}
