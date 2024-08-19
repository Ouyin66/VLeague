package com.example.vleague.Activity.Details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vleague.Order;
import com.example.vleague.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class Activity_Details_Admin_Order extends AppCompatActivity {

    ImageView logo1;
    ImageView logo2;
    TextView tvName1;
    TextView tvName2;
    TextView tvDate;
    TextView tvLocal;
    TextView tvTime;
    TextView tvRound;
    TextView tvSeat, tvPrice;

    TextView tvTotalPrice, tvQuantity, tvDateBooking, tvTrangThai, tvCusName;
    Order order = new Order();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_admin_order);

        logo1 = (ImageView) findViewById(R.id.imgLogo1);
        logo2 = (ImageView) findViewById(R.id.imgLogo2);
        tvName1 = (TextView) findViewById(R.id.tvNTeam1);
        tvName2 = (TextView) findViewById(R.id.tvNTeam2);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvLocal = (TextView) findViewById(R.id.tvLocation);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvRound = (TextView) findViewById(R.id.tvRound);
        tvSeat = (TextView) findViewById(R.id.tvSeat);
        tvTotalPrice = (TextView) findViewById(R.id.tvTotal);
        tvQuantity = (TextView) findViewById(R.id.tvQuantity);
        tvDateBooking = (TextView) findViewById(R.id.tvBookingDate);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvTrangThai = (TextView) findViewById(R.id.tvTrangThai);
        tvCusName = (TextView) findViewById(R.id.tvCusName);

        Bundle bundle = getIntent().getExtras();
        order = (Order) bundle.getSerializable("order");

        setInfo(order);
    }

    public void setInfo(Order order) {
//        logo1.setImageResource(order.getLogo1());
//        logo2.setImageResource(order.getLogo2());
        Picasso.get().load(order.getLogo1()).into(logo1);
        Picasso.get().load(order.getLogo2()).into(logo2);

        tvName1.setText(order.getName1());
        tvName2.setText(order.getName2());
        tvDate.setText(order.getDate());
        tvLocal.setText(order.getLocal());
        tvTime.setText(order.getTime());
        tvRound.setText("Vòng " + String.valueOf(order.getRound()));
        tvSeat.setText(order.getSeat());
        tvTotalPrice.setText(formatPrice(order.getTotalPrice()));
        tvQuantity.setText(String.valueOf(order.getQuantity()) + " vé");
        tvDateBooking.setText(order.getDateBooking());
        tvPrice.setText(formatPrice(order.getPrice()));
        tvTrangThai.setText(order.getStatus());
        tvCusName.setText(order.getCusName());
    }

    public String formatPrice(int price) {
        String priceInput = String.valueOf(price);
        double priceValue = Double.parseDouble(priceInput);
        DecimalFormat decimalFormat = new DecimalFormat("###,### VND");
        String priceOutput = decimalFormat.format(priceValue);

        return priceOutput;
    }

    public void btnBack24_onClick(View view) {
        finish();
    }
}