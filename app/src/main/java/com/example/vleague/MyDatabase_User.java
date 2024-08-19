package com.example.vleague;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vleague.Class.User;

public class MyDatabase_User {
    SQLiteDatabase database;
    DBHelper helper;

    public MyDatabase_User(Context context) {
        helper = new DBHelper(context);

        database = helper.getWritableDatabase();
    }

    public long ThemUser(User user) {
        ContentValues values = new ContentValues();

        values.put(DBHelper.HOTEN_USER, user.getHoTen());
        values.put(DBHelper.EMAIL_USER, user.getEmail());
        values.put(DBHelper.PHONE_USER, user.getPhone());
        values.put(DBHelper.PASSWORD_USER, user.getPassword());
        values.put(DBHelper.ROLE_USER, user.getRole());

        return database.insert(DBHelper.TEN_BANG_USER, null, values);
    }

    public long SuaUser(User user) {
        ContentValues values = new ContentValues();

        values.put(DBHelper.HOTEN_USER, user.getHoTen());
        values.put(DBHelper.PHONE_USER, user.getPhone());
        values.put(DBHelper.EMAIL_USER, user.getEmail());
        values.put(DBHelper.ROLE_USER, user.getRole());
        values.put(DBHelper.PASSWORD_USER, user.getPassword());

        return database.update(DBHelper.TEN_BANG_USER, values,
                DBHelper.ID_USER + " = " + user.getId(), null);
    }

    public long SuaUserCuaUser(User user) {
        ContentValues values = new ContentValues();

        values.put(DBHelper.HOTEN_USER, user.getHoTen());
        values.put(DBHelper.PHONE_USER, user.getPhone());
        values.put(DBHelper.PASSWORD_USER, user.getPassword());

        return database.update(DBHelper.TEN_BANG_USER, values,
                DBHelper.ID_USER + " = " + user.getId(), null);
    }

        public Boolean KiemTraDangNhap(String email, String pass) {
            String[] cot = new String[]{email, pass};
            String query = "Select * from User where Email =? And Password =? ";

            Cursor cursor = database.rawQuery(query, cot);

            if(cursor.getCount() > 0) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }
        }

    public String KiemTraRole(String email) {
        String userRole = "";
        String[] cot = new String[]{email};

        String query = "Select Role from User where Email =? ";

        Cursor cursor = database.rawQuery(query, cot);

        if(cursor.moveToNext()) {
            userRole = cursor.getString(0);
        }

        cursor.close();
        return userRole;
    }

    public boolean KiemTraEmail(String email) {
        String[] cot = new String[]{email};
        boolean TonTai = false;

        String query = "Select Email from User where Email =?";

        Cursor cursor = database.rawQuery(query, cot);

        if(cursor.moveToFirst()) {
            TonTai = true;
        }

        cursor.close();
        return TonTai;
    }

    public String[] getAdminInfo(String email) {
        String[] cot = new String[]{email};
        String query = "select HoTen, Phone, id from User where Email =?";

        Cursor cursor = database.rawQuery(query, cot);

        String hoten = "";
        String phone = "";
        long id = 0;

        if(cursor.moveToNext()) {
            int HotenIndex = cursor.getColumnIndex(DBHelper.HOTEN_USER);
            int PhoneIndex = cursor.getColumnIndex(DBHelper.PHONE_USER);
            long idIndex = cursor.getColumnIndex(DBHelper.ID_USER);

            if(HotenIndex >= 0) {
                hoten = cursor.getString(HotenIndex);
            }
            if(PhoneIndex >= 0) {
                phone = cursor.getString(PhoneIndex);
            }
            if(idIndex >= 0) {
                id = cursor.getInt((int) idIndex);
            }
        }

        String[] info = new String[] {hoten, phone, String.valueOf(id)};

        return info;
    }

    public Cursor getUserInfo(String email) {
        String[] cot = {email};

        Cursor cursor = null;

        String query = "Select * from User where Email = ?";

        cursor = database.rawQuery(query, cot);

        return cursor;
    }

    public Cursor LayDuLieuNguoiDung() {
        String[] cot = {DBHelper.ID_USER, DBHelper.HOTEN_USER
                 , DBHelper.PHONE_USER};

        Cursor cursor = null;

        cursor = database.query(DBHelper.TEN_BANG_USER
                , cot, null, null, null, null, null);

        return cursor;
    }

    public Cursor LayNguoiDungBoiId(long id) {
        String[] cot = {String.valueOf(id)};

        String query = "Select * from User where id = ?";

        Cursor cursor = database.rawQuery(query, cot);

        return cursor;
    }

    public Cursor Search(String nameUser) {
        String[] cot = {"%" + nameUser + "%"};

        String query = "select * from User where HoTen like ?";

        Cursor cursor = null;

        cursor = database.rawQuery(query, cot);

        return cursor;
    }
}
