package com.example.vleague;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vleague.Class.Location;

public class MyDatabase_Location {
    SQLiteDatabase database;
    DBHelper helper;

    public MyDatabase_Location(Context context) {
        helper = new DBHelper(context);

        database = helper.getWritableDatabase();
    }

    public boolean KiemTraTrungTen(String name) {
        boolean TonTai = false;

        String[] cot = {name};

        String query = "select * from Location where Location_Name = ?";

        Cursor cursor = null;

        cursor = database.rawQuery(query, cot);

        if(cursor.moveToFirst()) {
            TonTai = true;
        }

        return TonTai;
    }

    public long AddLocation(Location location) {
        ContentValues values = new ContentValues();

        values.put(DBHelper.LOCATION_NAME, location.getLocation_Name());
        values.put(DBHelper.LOCATION_TOTAL_SEAT, location.getLocation_Total_Seat());

        return database.insert(DBHelper.TEN_BANG_LOCATION, null, values);
    }

    public long suaDiaDiem(Location location) {
        ContentValues values = new ContentValues();

        values.put(DBHelper.LOCATION_NAME, location.getLocation_Name());
        values.put(DBHelper.LOCATION_TOTAL_SEAT, location.getLocation_Total_Seat());

        return database.update(DBHelper.TEN_BANG_LOCATION, values
                , DBHelper.LOCATION_ID + " = " + location.getLocation_id(), null);
    }

    public Cursor LayTatCaDuLieu() {
        String[] cot = { DBHelper.LOCATION_ID
                , DBHelper.LOCATION_NAME
                , DBHelper.LOCATION_TOTAL_SEAT};

        Cursor cursor = null;

        cursor = database.query(DBHelper.TEN_BANG_LOCATION,
                cot, null, null, null, null,
                null);

        return cursor;
    }

    public Cursor LayDiaDiem(long id) {
        String cot[] = {String.valueOf(id)};

        String query = "Select * from Location where Location_id = ?";

        Cursor cursor = database.rawQuery(query, cot);

        return cursor;
    }

    public long delLocation(Location location) {
        return database.delete(DBHelper.TEN_BANG_LOCATION
                , DBHelper.LOCATION_ID + " = " + location.getLocation_id(), null);
    }
}
