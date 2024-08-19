package com.example.vleague.Activity.AddnEdit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vleague.Class.News;
import com.example.vleague.NewsDatabase;
import com.example.vleague.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class Activity_Edit_News extends AppCompatActivity {

    EditText edtTitle, edtDate, edtDes, edtContent, edtImg;
    Button btnEdit;
    ImageView imgBanner;
    long id = -1;
    NewsDatabase newsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news);
        edtImg = (EditText) findViewById(R.id.edtImg);
        edtTitle = (EditText) findViewById(R.id.tvNewsTitle);
        edtDate = (EditText) findViewById(R.id.tvNewsDate);
        edtDes = (EditText) findViewById(R.id.tvSummary);
        edtContent = (EditText) findViewById(R.id.tvNewsBody);
        imgBanner = (ImageView) findViewById(R.id.imgBanner);

        btnEdit = (Button) findViewById(R.id.btnEdit);

        newsDatabase = new NewsDatabase(this);

        Bundle bundle = getIntent().getExtras();
        News news = (News) bundle.getSerializable("news1");

        id = news.getId();

        edtImg.setText(news.getNewsImg());
        Picasso.get().load(news.getNewsImg()).into(imgBanner);
        edtTitle.setText(news.getTitle());
        edtDate.setText(news.getDate());
        edtDes.setText(news.getSummary());
        edtContent.setText(news.getBody());

        edtImg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String url = charSequence.toString().trim();

                if (!url.isEmpty()) {
                    Picasso.get().load(url).into(imgBanner);
                } else {
                    Picasso.get().load(R.drawable.banner).into(imgBanner);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                //Khởi tạo datePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Edit_News.this,
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

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String img = edtImg.getText().toString();
                String title = edtTitle.getText().toString();
                String date = edtDate.getText().toString();
                String summary = edtDes.getText().toString();
                String body = edtContent.getText().toString();

                if(img.isEmpty() || title.isEmpty() || date.isEmpty()
                        || summary.isEmpty() || body.isEmpty()) {
                    Toast.makeText(Activity_Edit_News.this,
                            "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    News news = LayDuLieu();

                    Picasso.get().load(news.getNewsImg()).into(imgBanner);

                    if(news != null && id != -1) {
                        newsDatabase.SuaNews(news);
                        Toast.makeText(Activity_Edit_News.this,
                                "Sửa thành công", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }
            }
        });
    }

    public News LayDuLieu() {
        News news = new News();

        news.setId(id);
        news.setNewsImg(edtImg.getText().toString());
        news.setTitle(edtTitle.getText().toString());
        news.setDate(edtDate.getText().toString());
        news.setSummary(edtDes.getText().toString());
        news.setBody(edtContent.getText().toString());

        return news;
    }

    public void btnBack9_onClick(View view) {
        finish();
    }
}