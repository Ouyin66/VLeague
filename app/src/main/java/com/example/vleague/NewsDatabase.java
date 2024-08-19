package com.example.vleague;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.vleague.Class.News;
import com.example.vleague.Class.User;

public class NewsDatabase{

    SQLiteDatabase database;
    DBHelper helper;

    public NewsDatabase(Context context) {
        helper = new DBHelper(context);

        database = helper.getWritableDatabase();
    }

    public Cursor HienThiTatCa() {

        Cursor cursor = null;

        cursor = database.query(DBHelper.TEN_BANG_NEWS
                , null, null, null, null, null, null);

        return cursor;
    }

    public long ThemNews(News news) {
        ContentValues values = new ContentValues();

        values.put(DBHelper.NEWS_IMG, news.getNewsImg());
        values.put(DBHelper.NEWS_TITLE, news.getTitle());
        values.put(DBHelper.NEWS_DATE, news.getDate());
        values.put(DBHelper.NEWS_SUM, news.getSummary());
        values.put(DBHelper.NEWS_BODY, news.getBody());

        return database.insert(DBHelper.TEN_BANG_NEWS, null, values);
    }

    public long XoaNews(News news) {
        return database.delete(DBHelper.TEN_BANG_NEWS
                , DBHelper.NEWS_ID + " = " + news.getId(), null);
    }

    public long SuaNews(News news) {
        ContentValues values = new ContentValues();

        values.put(DBHelper.NEWS_IMG, news.getNewsImg());
        values.put(DBHelper.NEWS_TITLE, news.getTitle());
        values.put(DBHelper.NEWS_DATE, news.getDate());
        values.put(DBHelper.NEWS_SUM, news.getSummary());
        values.put(DBHelper.NEWS_BODY, news.getBody());

        return database.update(DBHelper.TEN_BANG_NEWS, values
                , DBHelper.NEWS_ID + " = " + news.getId(), null);
    }

    public Cursor LayNewsTheoid(long id) {
        String[] cot = {String.valueOf(id)};

        Cursor cursor = database.query(DBHelper.TEN_BANG_NEWS, null,
                "News_id = ?", cot, null, null, null);

        return cursor;
    }
}
