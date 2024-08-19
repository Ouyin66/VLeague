package com.example.vleague;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vleague.Class.Matches;
import com.example.vleague.Class.Team;

public class MyDatabase_team {
    SQLiteDatabase database;
    DBHelper helper;

    public MyDatabase_team(Context context) {
        helper = new DBHelper(context);

        database = helper.getWritableDatabase();
    }

    public long AddTeam(Team team) {
        ContentValues values = new ContentValues();

        values.put(DBHelper.TEAM_IMG, team.getLogo());
        values.put(DBHelper.TEAM_NAME, team.getName());

        return database.insert(DBHelper.TEN_BANG_TEAM, null, values);
    }

    public Cursor LayTatCaDuLieu() {
        Cursor cursor = null;

        cursor = database.query(DBHelper.TEN_BANG_TEAM,
                null, null, null, null, null,
                null);

        return cursor;
    }

    public Cursor LayTeamTheoId(long id) {
        String[] cot = {String.valueOf(id)};

        String query = "select * from Team where Team_id = ?";

        Cursor cursor = null;

        cursor = database.rawQuery(query, cot);

        return cursor;
    }

    public long xoaTeam(Team team) {
        return database.delete(DBHelper.TEN_BANG_TEAM
                , DBHelper.TEAM_ID + " = " + team.getId(), null);
    }

    public long suaTeam(Team team) {
        ContentValues values = new ContentValues();

        values.put(DBHelper.TEAM_IMG, team.getLogo());
        values.put(DBHelper.TEAM_NAME, team.getName());

        return database.update(DBHelper.TEN_BANG_TEAM, values
                , DBHelper.TEAM_ID + " = " + team.getId(), null);
    }

    public boolean KiemTraTrungTen(String name) {
        boolean TonTai = false;

        String[] cot = {name};

        String query = "select * from Team where Team_name = ?";

        Cursor cursor = null;

        cursor = database.rawQuery(query, cot);

        if(cursor.moveToFirst()) {
            TonTai = true;
        }

        return TonTai;
    }
}
