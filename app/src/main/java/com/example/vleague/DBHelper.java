package com.example.vleague;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TEN_DATABASE = "QuanLyDatVe";
    public static final String TEN_BANG_USER = "User";
    public static final String TEN_BANG_MATCH = "Matches";
    public static final String TEN_BANG_CHI_TIET_VE = "Ticket";
    public static final String TEN_BANG_BANK = "Bank";
    public static final String TEN_BANG_NEWS = "News";
    public static final String TEN_BANG_TEAM = "Team";

    public static final String NEWS_ID = "News_id";
    public static final String NEWS_IMG = "News_img";
    public static final String NEWS_TITLE = "News_title";
    public static final String NEWS_DATE = "News_date";
    public static final String NEWS_SUM = "News_sum";
    public static final String NEWS_BODY = "News_body";


    private static final String TAO_BANG_NEWS = ""
            + "create table " + TEN_BANG_NEWS + " ( "
            + NEWS_ID + " integer primary key autoincrement, "
            + NEWS_IMG + " text, "
            + NEWS_TITLE + " text not null, "
            + NEWS_DATE + " text not null, "
            + NEWS_SUM + " text not null, "
            + NEWS_BODY + " text not null );";

    // Location
    public static final String TEN_BANG_LOCATION = "Location";

    public static final String LOCATION_ID = "Location_Id";
    public static final String LOCATION_NAME = "Location_Name";
    public static final String LOCATION_TOTAL_SEAT = "Total_Seat";

    private static final String TAO_BANG_LOCATION = ""
            + "create table " + TEN_BANG_LOCATION + " ( "
            + LOCATION_ID + " integer primary key autoincrement, "
            + LOCATION_NAME + " text not null, "
            + LOCATION_TOTAL_SEAT + " integer not null );";

    // User
    public static final String ID_USER = "id";
    public static final String HOTEN_USER = "HoTen";
    public static final String EMAIL_USER = "Email";
    public static final String PHONE_USER = "Phone";
    public static final String PASSWORD_USER = "Password";
    public static final String ROLE_USER = "Role";

    private static final String TAO_BANG_USER = ""
            + "create table " + TEN_BANG_USER + " ( "
            + ID_USER + " integer primary key autoincrement, "
            + HOTEN_USER + " text not null, "
            + EMAIL_USER + " text not null, "
            + PHONE_USER + " text not null, "
            + PASSWORD_USER + " text not null, "
            + ROLE_USER + " text not null);";


    // Match
    public static final String ID_MATCH = "id";
    public static final String LOGO_1 = "Logo_1";
    public static final String LOGO_2 = "Logo_2";
    public static final String NAME_1 = "Name_1";
    public static final String NAME_2 = "Name_2";
    public static final String DATE_MATCH = "Date";
    public static final String TIME_MATCH = "Time";
    public static final String PRICE_MATCH = "Price";
    public static final String ROUND_MATCH = "Round";
    public static final String LOCATION_MATCH = "Location";
    private static final String TAO_BANG_MATCH = ""
            + "create table " + TEN_BANG_MATCH + " ( "
            + ID_MATCH + " integer primary key autoincrement, "
            + LOGO_1 + " text not null, "
            + LOGO_2 + " text not null, "
            + NAME_1 + " text not null, "
            + NAME_2 + " text not null, "
            + TIME_MATCH + " text not null, "
            + PRICE_MATCH + " integer not null, "
            + ROUND_MATCH + " integer not null, "
            + LOCATION_MATCH + " integer not null,"
            + DATE_MATCH + " text not null, "
            + "FOREIGN KEY " + "(" + LOCATION_MATCH + ") REFERENCES "
            + TEN_BANG_LOCATION + "(" + LOCATION_ID + "));";

    // Bank
    public static final String ID_BANK = "id_bank";
    public static final String ID_BANK_CUS = "id_bank_cus";
    public static final String BANK_NUM = "bank_num";
    public static final String BANK_NAME = "bank_name";
    public static final String CVV = "bank_cvv";
    public static final String BANK_DATE = "bank_date";
    public static final String BANK_MONEY = "bank_money";
    private static final String TAO_BANG_BANK = ""
            + "create table " + TEN_BANG_BANK + " ( "
            + ID_BANK + " integer primary key autoincrement, "
            + ID_BANK_CUS + " integer not null, "
            + BANK_NAME + " text not null, "
            + BANK_NUM + " text not null, "
            + CVV + " text not null, "
            + BANK_DATE + " text not null, "
            + BANK_MONEY + " integer not null, "
            + "FOREIGN KEY " + "(" + ID_BANK_CUS + ") REFERENCES "
            + TEN_BANG_USER + "(" + ID_USER + "));";

    // Ticket
    public static final String ID_TICKET = "id";
    public static final String ID_CUS = "id_cus";
    public static final String CUS_NAME = "cus_name";
    public static final String CUS_PHONE = "cus_phone";
    public static final String CUS_EMAIL = "cus_email";
    public static final String ID_MATCH_TICKET = "id_match";
    public static final String SEATING_AREA = "seating_area";
    public static final String QUANTITY = "quantity";
    public static final String TOTAL_PRICE = "total_price";
    public static final String BOOKING_DATE = "booking_date";
    public static final String STATUS_PAY = "status_pay";
    private static final String TAO_BANG_CHI_TIET_VE = ""
            + "create table " + TEN_BANG_CHI_TIET_VE + " ( "
            + ID_TICKET + " integer primary key autoincrement, "
            + ID_CUS + " integer not null, "
            + CUS_NAME + " text not null, "
            + CUS_PHONE + " text not null, "
            + CUS_EMAIL + " text not null, "
            + ID_MATCH_TICKET + " integer not null, "
            + SEATING_AREA + " text not null, "
            + QUANTITY + " integer not null, "
            + TOTAL_PRICE + " integer not null, "
            + BOOKING_DATE + " text not null, "
            + STATUS_PAY + " integer not null, "
            + "FOREIGN KEY " + "(" + ID_MATCH_TICKET + ") REFERENCES "
            + TEN_BANG_MATCH + "(" + ID_MATCH + "),"
            + "FOREIGN KEY " + "(" + ID_CUS + ") REFERENCES "
            + TEN_BANG_USER + "(" + ID_USER + "));";

    // Team
    public static final String TEAM_ID = "Team_id";
    public static final String TEAM_IMG = "Team_img";
    public static final String TEAM_NAME = "Team_name";
    private  static final  String TAO_BANG_DOI_BONG = ""
            + "create table " + TEN_BANG_TEAM + " ( "
            + TEAM_ID + " integer primary key autoincrement, "
            + TEAM_IMG + " text not null, "
            + TEAM_NAME + " text not null );";


    public DBHelper(Context context) {
        super(context, TEN_DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TAO_BANG_USER);
        db.execSQL(TAO_BANG_MATCH);
        db.execSQL(TAO_BANG_LOCATION);
        db.execSQL(TAO_BANG_CHI_TIET_VE);
        db.execSQL(TAO_BANG_BANK);
        db.execSQL(TAO_BANG_NEWS);
        db.execSQL(TAO_BANG_DOI_BONG);

        ContentValues values = new ContentValues();
        values.put(HOTEN_USER, "Admin");
        values.put(EMAIL_USER, "admin@gmail.com");
        values.put(PHONE_USER, "0123456789");
        values.put(PASSWORD_USER, "123");
        values.put(ROLE_USER, 1);
        db.insert(TEN_BANG_USER, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
