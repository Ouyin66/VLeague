package com.example.vleague.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vleague.Activity.Details.Activity_Details_Order;
import com.example.vleague.Adapter.Adapter_Order;
import com.example.vleague.DBHelper;
import com.example.vleague.MyDatabase_ticket;
import com.example.vleague.Order;
import com.example.vleague.R;
import com.example.vleague.UserSession;

import java.util.ArrayList;

public class Activity_Order_History extends AppCompatActivity {

    ListView listOrder;
    ArrayList<Order> orderArrayList;
    MyDatabase_ticket myDatabase_ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        listOrder = (ListView) findViewById(R.id.listOrder);

        orderArrayList = new ArrayList<>();

        myDatabase_ticket = new MyDatabase_ticket(this);

        DanhSachHoaDon();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        DanhSachHoaDon();
//    }

    @SuppressLint("Range")
    public void DanhSachHoaDon() {
        if(orderArrayList == null) {
            orderArrayList = new ArrayList<>();
        } else {
            orderArrayList.remove(orderArrayList);
        }

        Cursor cursor = myDatabase_ticket.LayHoaDon(UserSession.userId);

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

                orderArrayList.add(order);
            }
        }

        setListViewTicket();
    }

    public void setListViewTicket() {
        if(orderArrayList != null) {
            Adapter_Order adapterOrder = new Adapter_Order(orderArrayList);
            listOrder.setAdapter(adapterOrder);
        }

        listOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long idTicket) {
                Order order1 = (Order) adapterView.getItemAtPosition(i);

//                order1.setLogo1(order.getLogo1());
//                order1.setLogo2(order.getLogo2());
//                order1.setName1(order.getName1());
//                order1.setName2(order.getName2());
//                order1.setDate(order.getDate());
//                order1.setLocal(order.getLocal());
//                order1.setTime(order.getTime());
//                order1.setRound(order.getRound());
//                order1.setSeat(order.getSeat());
//                order1.setPrice(order.getPrice());
//                order1.setTotalPrice(order.getTotalPrice());
//                order1.setQuantity(order.getQuantity());
//                order1.setDateBooking(order.getDateBooking());

                Bundle bundle = new Bundle();

                bundle.putSerializable("order", order1);

                Intent intent = new Intent(Activity_Order_History.this
                        , Activity_Details_Order.class);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    public void btnBack27_onClick(View view) {
        finish();
    }
}