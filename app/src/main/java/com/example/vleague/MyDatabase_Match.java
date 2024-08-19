package com.example.vleague;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vleague.Class.Matches;

public class MyDatabase_Match {
    SQLiteDatabase database;
    DBHelper helper;

    public MyDatabase_Match(Context context) {
        helper = new DBHelper(context);

        database = helper.getWritableDatabase();
    }

    public long AddMatch(Matches match) {
        ContentValues values = new ContentValues();

        values.put(DBHelper.LOGO_1, match.getLogo1());
        values.put(DBHelper.LOGO_2, match.getLogo2());
        values.put(DBHelper.NAME_1, match.getName1());
        values.put(DBHelper.NAME_2, match.getName2());
        values.put(DBHelper.TIME_MATCH, match.getTime());
        values.put(DBHelper.PRICE_MATCH, match.getPrice());
        values.put(DBHelper.ROUND_MATCH, match.getRound());
        values.put(DBHelper.LOCATION_MATCH, match.getLocation());
        values.put(DBHelper.DATE_MATCH, match.getDate());

        return database.insert(DBHelper.TEN_BANG_MATCH, null, values);
    }

    public Cursor LayTatCaDuLieu() {
        String query = "select * from Matches";

        Cursor cursor = null;

        cursor = database.rawQuery(query, null);

        return cursor;
    }

    public Cursor LayDuLieuTheoNgay(String date) {
        String[] cot = {date};

        String query = "select * from Matches where Date = ?";

        Cursor cursor = null;

        cursor = database.rawQuery(query, cot);

        return cursor;
    }

    public Cursor LayTinTucTheoNgay(String date) {
        String[] cot = {date};

        String query = "select * from News where News_date = ?";

        Cursor cursor = null;

        cursor = database.rawQuery(query, cot);

        return cursor;
    }

    public Cursor searchMatches(String searchText) {
        String query = "SELECT * FROM Matches WHERE Name_1 like ? " +
                "OR Name_2 like ? " +
                "OR Time like ? " +
                "OR Round like ? " +
                "OR Location like ? " +
                "OR Date like ?";

        String[] cot = new String[]{"%"+searchText+"%", "%"+searchText+"%", "%"+searchText+"%"
                , "%"+searchText+"%", "%"+searchText+"%", "%"+searchText+"%"};

        Cursor cursor = null;

        cursor = database.rawQuery(query, cot);

        return cursor;
    }

    public Cursor LayDiaDiem() {
        String[] cot = { DBHelper.LOCATION_NAME, DBHelper.LOCATION_TOTAL_SEAT};

        Cursor cursor = null;

        cursor = database.query(DBHelper.TEN_BANG_LOCATION,
                cot, null, null, null, null,
                null);

        return cursor;
    }

    public Cursor LayTranDau(long id) {
        String[] cot = {String.valueOf(id)};

        Cursor cursor = database.query(DBHelper.TEN_BANG_MATCH, null,
                "id = ?", cot, null, null, null);

        return cursor;
    }

    public long xoaTranDau(Matches matches) {
        return database.delete(DBHelper.TEN_BANG_MATCH
                , DBHelper.ID_MATCH + " = " + matches.getId(), null);
    }

    public long suaTranDau(Matches matches) {
        ContentValues values = new ContentValues();

        values.put(DBHelper.LOGO_1, matches.getLogo1());
        values.put(DBHelper.LOGO_2, matches.getLogo2());
        values.put(DBHelper.NAME_1, matches.getName1());
        values.put(DBHelper.NAME_2, matches.getName2());
        values.put(DBHelper.TIME_MATCH, matches.getTime());
        values.put(DBHelper.PRICE_MATCH, matches.getPrice());
        values.put(DBHelper.ROUND_MATCH, matches.getRound());
        values.put(DBHelper.LOCATION_MATCH, matches.getLocation());
        values.put(DBHelper.DATE_MATCH, matches.getDate());

        return database.update(DBHelper.TEN_BANG_MATCH, values
                , DBHelper.ID_MATCH + " = " + matches.getId(), null);
    }

    public Cursor LayTranDauTheoVong(long round) {
        String[] cot = {String.valueOf(round)};

        String query = "Select * from Matches where Round = ?";

        Cursor cursor = database.rawQuery(query, cot);

        return cursor;
    }

    @SuppressLint("Recycle")
    public boolean TruyVanTrungLapTranDau(String name1, String name2, String time, String date
            , String local, int round) {
        boolean TrungLap = false; //mac dinh la khong co trung lap

        String[] cot = {String.valueOf(round), name1, name2, date, local, time};

        String query = "select * from Matches where Round = ? " +
                "and (" +
                "(Name_1 = ? and Name_2 = ?)" +
                " or (Date = ? and Location = ? and Time = ?))";

        Cursor cursor = database.rawQuery(query, cot);

        if (cursor.moveToNext()) {
            TrungLap = true;
        }

        return TrungLap;
    }
}
