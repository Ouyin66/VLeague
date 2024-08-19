package com.example.vleague;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vleague.Class.Ticket;

public class MyDatabase_ticket {
    SQLiteDatabase database;
    DBHelper helper;

    public MyDatabase_ticket(Context context) {
        helper = new DBHelper(context);

        database = helper.getWritableDatabase();
    }

    public long them(Ticket ticket) {
        ContentValues values = new ContentValues();

        values.put(DBHelper.ID_CUS, ticket.getId_cus());
        values.put(DBHelper.CUS_NAME, ticket.getCus_name());
        values.put(DBHelper.CUS_PHONE, ticket.getCus_phone());
        values.put(DBHelper.CUS_EMAIL, ticket.getCus_email());
        values.put(DBHelper.ID_MATCH_TICKET, ticket.getId_match());
        values.put(DBHelper.SEATING_AREA, ticket.getSeating_area());
        values.put(DBHelper.QUANTITY, ticket.getQuantity());
        values.put(DBHelper.TOTAL_PRICE, ticket.getTotal_price());
        values.put(DBHelper.BOOKING_DATE, ticket.getBooking_date());
        values.put(DBHelper.STATUS_PAY, ticket.getStatusPay());

        return database.insert(DBHelper.TEN_BANG_CHI_TIET_VE,
                null, values);
    }

    public Cursor LayHoaDon(long idCus) {
        String[] cot = {String.valueOf(idCus)};

        String query = "Select * from Ticket " +
                "join Matches on Ticket.id_match = Matches.id" +
                " where Ticket.id_cus = ?";

        Cursor cursor = null;

        cursor = database.rawQuery(query, cot);

        return cursor;
    }

    public Cursor LayTatCaHoaDon() {

        String query = "select * from Ticket, Matches where Ticket.id_match = Matches.id";

        Cursor cursor = null;

        cursor = database.rawQuery(query, null);

        return cursor;
    }

    public Cursor Search(String name) {
        String[] cot = {"%"+name+"%"};

        String query = "select * from Ticket, Matches where Ticket.id_match = Matches.id" +
                " and Ticket.cus_name like ?";

        Cursor cursor = null;

        cursor = database.rawQuery(query, cot);

        return cursor;
    }
}
