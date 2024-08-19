package com.example.vleague;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vleague.Class.Bank;

public class MyDatabase_bank {
    SQLiteDatabase database;
    DBHelper helper;

    public MyDatabase_bank(Context context) {
        helper = new DBHelper(context);

        database = helper.getWritableDatabase();
    }

    public Cursor LayBankTheoId(long idBank) {
        String[] cot = {String.valueOf(idBank)};

        String query = "Select * from Bank where id_bank = ?";

        Cursor cursor = null;

        cursor = database.rawQuery(query, cot);

        return cursor;
    }

    public Cursor LayTatCaBankTheoId(long id) {
        String[] cot = {String.valueOf(id)};

        String query = "Select * from Bank where id_bank_cus = ?";

        Cursor cursor = null;

        cursor = database.rawQuery(query, cot);

        return cursor;
    }

    public boolean KiemTraTonTaiBank(String bankNum) {
        boolean tontai = false;
        String[] cot = {bankNum};

        String query = "select * from Bank where bank_num = ?";

        Cursor cursor = null;

        cursor = database.rawQuery(query, cot);

        if(cursor.moveToFirst()) {
            tontai = true;
        }

        cursor.close();
        return tontai;
    }

    public long ThemBank(Bank bank) {
        ContentValues values = new ContentValues();

        values.put(DBHelper.BANK_NAME, bank.getNameBank());
        values.put(DBHelper.ID_BANK_CUS, bank.getBankCusId());
        values.put(DBHelper.BANK_NUM, bank.getBankNum());
        values.put(DBHelper.CVV, bank.getBankCvv());
        values.put(DBHelper.BANK_DATE, bank.getBankDate());
        values.put(DBHelper.BANK_MONEY, bank.getBankMoney());

       return database.insert(DBHelper.TEN_BANG_BANK, null, values);
    }


    @SuppressLint("Range")
    public int ThanhToan(long idBank) {
        String[] cot = {String.valueOf(idBank)};

        String query = "Select bank_money from Bank where id_bank = ?";

        Cursor cursor = database.rawQuery(query, cot);

        int bankMoney = 0;

        if (cursor.moveToFirst()) {
            bankMoney = cursor.getInt(cursor.getColumnIndex("bank_money"));
        }

        return bankMoney;
    }

    public long CapNhatCotBankMoney(int bankMoney, long idBank) {
        ContentValues values = new ContentValues();

        values.put(DBHelper.BANK_MONEY, bankMoney);

        String whereClause = "id_bank = ?";
        String[] whereArgs = {String.valueOf(idBank)};

        int status = database.update(DBHelper.TEN_BANG_BANK, values
                , whereClause, whereArgs);

        return status;
    }
}
