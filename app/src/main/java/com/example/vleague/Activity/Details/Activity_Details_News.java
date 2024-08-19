package com.example.vleague.Activity.Details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vleague.Activity.AddnEdit.Activity_Edit_News;
import com.example.vleague.Class.News;
import com.example.vleague.R;
import com.squareup.picasso.Picasso;

public class Activity_Details_News extends AppCompatActivity {

    ImageView imgNews;
    TextView tvTitle, tvDate, tvSum, tvBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_news);

        imgNews = (ImageView) findViewById(R.id.imgBanner);
        tvTitle = (TextView) findViewById(R.id.tvNewsTitle);
        tvDate = (TextView) findViewById(R.id.tvNewsDate);
        tvBody = (TextView) findViewById(R.id.tvNewsBody);

        Bundle bundle = getIntent().getExtras();

        News news = (News) bundle.getSerializable("news");

//        imgNews.setImageResource(news.getNewsImg());
        Picasso.get().load(news.getNewsImg()).into(imgNews);
        tvTitle.setText(news.getTitle());
        tvDate.setText(news.getDate());
        tvBody.setText(news.getBody());
    }

    public void btnEditNews_onClick(View view) {
        Intent editNews = new Intent(Activity_Details_News.this, Activity_Edit_News.class);
        startActivity(editNews);
    }

    public void btnBack23_onClick(View view) {
        finish();
    }
}