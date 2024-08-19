package com.example.vleague.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vleague.Activity.Details.Activity_Details_Admin_Order;
import com.example.vleague.Adapter.Adapter_Order_Admin;
import com.example.vleague.Class.Matches;
import com.example.vleague.DBHelper;
import com.example.vleague.MyDatabase_ticket;
import com.example.vleague.Order;
import com.example.vleague.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Activity_Manage_Ticket extends AppCompatActivity {

    ListView listOrder;
    TextView tvQuantity, tvTotal;
    EditText edtSearch;
    ArrayList<Order> orderArrayList;
    MyDatabase_ticket myDatabase_ticket;

    int totalMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_ticket);

        listOrder = (ListView) findViewById(R.id.listOrder);
        tvQuantity = (TextView) findViewById(R.id.tvQuantityTicket);
        tvTotal = (TextView) findViewById(R.id.tvTotalTicket);
        edtSearch = (EditText) findViewById(R.id.edtSearch);

        orderArrayList = new ArrayList<>();
        myDatabase_ticket = new MyDatabase_ticket(this);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString();

                if(searchText.isEmpty()) {
                    DanhSachHoaDon();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString();

                if(searchText.isEmpty()) {
                    DanhSachHoaDon();
                } else {
                    Search(searchText);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        DanhSachHoaDon();
        tvTotal.setText(formatPrice(totalMoney));
        tvQuantity.setText(String.valueOf(listOrder.getCount()));
    }

    public String formatPrice(int price) {
        String priceInput = String.valueOf(price);
        double priceValue = Double.parseDouble(priceInput);
        DecimalFormat decimalFormat = new DecimalFormat("###,### VND");
        String priceOutput = decimalFormat.format(priceValue);

        return priceOutput;
    }

    @SuppressLint("Range")
    public void Search(String nameCus) {
        if(orderArrayList == null) {
            orderArrayList = new ArrayList<Order>();
        } else {
            orderArrayList.removeAll(orderArrayList);
        }

        String newText = nameCus;

        Cursor cursor = myDatabase_ticket.Search(newText);

        if(cursor != null) {
            while (cursor.moveToNext()) {
                Order order = new Order();

                String logo1 = cursor.getString(cursor.getColumnIndex(DBHelper.LOGO_1));
                String logo2 = cursor.getString(cursor.getColumnIndex(DBHelper.LOGO_2));
                String name1 = cursor.getString(cursor.getColumnIndex(DBHelper.NAME_1));
                String name2 = cursor.getString(cursor.getColumnIndex(DBHelper.NAME_2));
                String date = cursor.getString(cursor.getColumnIndex(DBHelper.DATE_MATCH));
                String location = cursor.getString(cursor.getColumnIndex(DBHelper.LOCATION_MATCH));
                String time = cursor.getString(cursor.getColumnIndex(DBHelper.TIME_MATCH));
                int round = cursor.getInt(cursor.getColumnIndex(DBHelper.ROUND_MATCH));
                String seat = cursor.getString(cursor.getColumnIndex(DBHelper.SEATING_AREA));
                int Price = cursor.getInt(cursor.getColumnIndex(DBHelper.PRICE_MATCH));
                int totalPrice = cursor.getInt(cursor.getColumnIndex(DBHelper.TOTAL_PRICE));
                int Quantity = cursor.getInt(cursor.getColumnIndex(DBHelper.QUANTITY));
                String dateBooking = cursor.getString(cursor.getColumnIndex(DBHelper.BOOKING_DATE));
                int status = cursor.getInt(cursor.getColumnIndex(DBHelper.STATUS_PAY));
                String NameUser = cursor.getString(cursor.getColumnIndex(DBHelper.CUS_NAME));

                String message = "";

                if(status == 0) {
                    message = "Đã thanh toán";
                }

                order.setLogo1(logo1);
                order.setLogo2(logo2);
                order.setName1(name1);
                order.setName2(name2);
                order.setDate(date);
                order.setLocal(location);
                order.setTime(time);
                order.setRound(round);
                order.setSeat(seat);
                order.setPrice(Price);
                order.setTotalPrice(totalPrice);
                order.setQuantity(Quantity);
                order.setDateBooking(dateBooking);
                order.setStatus(message);
                order.setCusName(NameUser);

                totalMoney += order.getTotalPrice();

                orderArrayList.add(order);
            }
        }

        setListViewTicket();
    }

    @SuppressLint("Range")
    public void DanhSachHoaDon() {
        if(orderArrayList == null) {
            orderArrayList = new ArrayList<>();
        } else {
            orderArrayList.remove(orderArrayList);
        }

        Cursor cursor = myDatabase_ticket.LayTatCaHoaDon();

        if(cursor != null) {
            while (cursor.moveToNext()) {
                Order order = new Order();

                String logo1 = cursor.getString(cursor.getColumnIndex(DBHelper.LOGO_1));
                String logo2 = cursor.getString(cursor.getColumnIndex(DBHelper.LOGO_2));
                String name1 = cursor.getString(cursor.getColumnIndex(DBHelper.NAME_1));
                String name2 = cursor.getString(cursor.getColumnIndex(DBHelper.NAME_2));
                String date = cursor.getString(cursor.getColumnIndex(DBHelper.DATE_MATCH));
                String location = cursor.getString(cursor.getColumnIndex(DBHelper.LOCATION_MATCH));
                String time = cursor.getString(cursor.getColumnIndex(DBHelper.TIME_MATCH));
                int round = cursor.getInt(cursor.getColumnIndex(DBHelper.ROUND_MATCH));
                String seat = cursor.getString(cursor.getColumnIndex(DBHelper.SEATING_AREA));
                int Price = cursor.getInt(cursor.getColumnIndex(DBHelper.PRICE_MATCH));
                int totalPrice = cursor.getInt(cursor.getColumnIndex(DBHelper.TOTAL_PRICE));
                int Quantity = cursor.getInt(cursor.getColumnIndex(DBHelper.QUANTITY));
                String dateBooking = cursor.getString(cursor.getColumnIndex(DBHelper.BOOKING_DATE));
                int status = cursor.getInt(cursor.getColumnIndex(DBHelper.STATUS_PAY));
                String NameUser = cursor.getString(cursor.getColumnIndex(DBHelper.CUS_NAME));

                String message = "";

                if(status == 0) {
                    message = "Đã thanh toán";
                }

                order.setLogo1(logo1);
                order.setLogo2(logo2);
                order.setName1(name1);
                order.setName2(name2);
                order.setDate(date);
                order.setLocal(location);
                order.setTime(time);
                order.setRound(round);
                order.setSeat(seat);
                order.setPrice(Price);
                order.setTotalPrice(totalPrice);
                order.setQuantity(Quantity);
                order.setDateBooking(dateBooking);
                order.setStatus(message);
                order.setCusName(NameUser);

                totalMoney += order.getTotalPrice();

                orderArrayList.add(order);
            }
        }

        setListViewTicket();
    }

    public void setListViewTicket() {
        if(orderArrayList != null) {
            Adapter_Order_Admin adapterOrder = new Adapter_Order_Admin(orderArrayList);
            listOrder.setAdapter(adapterOrder);
        }

        listOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long idTicket) {
                Order order1 = (Order) adapterView.getItemAtPosition(i);

                Bundle bundle = new Bundle();

                bundle.putSerializable("order", order1);

                Intent intent = new Intent(Activity_Manage_Ticket.this
                        , Activity_Details_Admin_Order.class);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    public void btnBack18_onClick(View view) {
        finish();
    }
}