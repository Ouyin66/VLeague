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
import android.widget.Toast;

import com.example.vleague.Activity.AddnEdit.Activity_Edit_News;
import com.example.vleague.Class.Matches;
import com.example.vleague.Class.News;
import com.example.vleague.DBHelper;
import com.example.vleague.NewsDatabase;
import com.example.vleague.R;
import com.squareup.picasso.Picasso;

public class Activity_Details_Admin_News extends AppCompatActivity {

    ImageView imgNews;
    TextView tvTitle, tvDate, tvSum, tvBody;
    Button btnEdit;
    ImageButton btnDelete;
    NewsDatabase newsDatabase;
    long id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_admin_news);

        imgNews = (ImageView) findViewById(R.id.imgBanner);
        tvTitle = (TextView) findViewById(R.id.tvNewsTitle);
        tvDate = (TextView) findViewById(R.id.tvNewsDate);
        tvSum = (TextView) findViewById(R.id.tvSummary);
        tvBody = (TextView) findViewById(R.id.tvNewsBody);
        btnDelete = (ImageButton) findViewById(R.id.ibtnDelete);
        btnEdit = (Button) findViewById(R.id.btnEdit);

        newsDatabase = new NewsDatabase(this);

        Bundle bundle = getIntent().getExtras();

        News news = (News) bundle.getSerializable("news");

        id = news.getId();
//        imgNews.setImageResource(news.getNewsImg());
        Picasso.get().load(news.getNewsImg()).into(imgNews);
        tvTitle.setText(news.getTitle());
        tvDate.setText(news.getDate());
        tvSum.setText(news.getSummary());
        tvBody.setText(news.getBody());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newsDatabase.XoaNews(news) != -1) {
                    Toast.makeText(Activity_Details_Admin_News.this,
                            "Xóa thành công", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Details_Admin_News.this
                        , Activity_Edit_News.class);

                Bundle bundle1 = new Bundle();

                bundle1.putSerializable("news1", news);

                intent.putExtras(bundle1);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        CapNhatThongTinMoi();
    }

    @SuppressLint("Range")
    public void CapNhatThongTinMoi() {
        News news = new News();
        Cursor cursor = newsDatabase.LayNewsTheoid(id);

        if(cursor != null && cursor.getCount() > 0
                && cursor.moveToFirst()) {

            do {
                @SuppressLint("Range") int idNews = cursor.getInt(cursor.getColumnIndex(DBHelper.NEWS_ID));

                if(idNews == id) {
                    news.setId(cursor.getInt(
                            cursor.getColumnIndex(DBHelper.NEWS_ID)
                    ));
                    news.setNewsImg(cursor.getString(
                            cursor.getColumnIndex(DBHelper.NEWS_IMG)
                    ));
                    news.setTitle(cursor.getString(
                            cursor.getColumnIndex(DBHelper.NEWS_TITLE)
                    ));
                    news.setDate(cursor.getString(
                            cursor.getColumnIndex(DBHelper.NEWS_DATE)
                    ));
                    news.setSummary(cursor.getString(
                            cursor.getColumnIndex(DBHelper.NEWS_SUM)
                    ));
                    news.setBody(cursor.getString(
                            cursor.getColumnIndex(DBHelper.NEWS_BODY)
                    ));
                    break;
                }
            } while (cursor.moveToNext());

//            imgNews.setImageResource(news.getNewsImg());
            Picasso.get().load(news.getNewsImg()).into(imgNews);
            tvTitle.setText(news.getTitle());
            tvDate.setText(news.getDate());
            tvSum.setText(news.getSummary());
            tvBody.setText(news.getBody());
        } else {
            Log.i("DEBUG", "Hang ko co");
        }
    }

    public void btnBack20_onClick(View view) {
        finish();
    }
}