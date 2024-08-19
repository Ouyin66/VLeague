package com.example.vleague.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vleague.Order;
import com.example.vleague.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Order_Admin extends BaseAdapter {

    ArrayList<Order> arrayList = new ArrayList<>();

    public Adapter_Order_Admin(ArrayList<Order> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public String formatPrice(int price) {
        String priceInput = String.valueOf(price);
        double priceValue = Double.parseDouble(priceInput);
        DecimalFormat decimalFormat = new DecimalFormat("###,### VND");
        String priceOutput = decimalFormat.format(priceValue);

        return priceOutput;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = View.inflate(viewGroup.getContext(), R.layout.layout_list_order_admin, null);

        ImageView logo1 = (ImageView) rootView.findViewById(R.id.imgLogo1);
        ImageView logo2 = (ImageView) rootView.findViewById(R.id.imgLogo2);
        TextView tvName1 = (TextView) rootView.findViewById(R.id.tvNTeam1);
        TextView tvName2 = (TextView) rootView.findViewById(R.id.tvNTeam2);
        TextView tvCusName = (TextView) rootView.findViewById(R.id.tvCusName);
        TextView tvQuantity = (TextView) rootView.findViewById(R.id.tvQuantity);
        TextView tvBookingDate = (TextView) rootView.findViewById(R.id.tvBookingDate);
        TextView tvTotalPrice = (TextView) rootView.findViewById(R.id.tvTotalPrice);

        Order order = (Order) getItem(i);

//        logo1.setImageResource(order.getLogo1());
//        logo2.setImageResource(order.getLogo2());
        Picasso.get().load(order.getLogo1()).into(logo1);
        Picasso.get().load(order.getLogo2()).into(logo2);
        tvName1.setText(order.getName1());
        tvName2.setText(order.getName2());
        tvCusName.setText(order.getCusName());
        tvQuantity.setText(String.valueOf(order.getQuantity()) + " vé");
        tvBookingDate.setText(order.getDateBooking());
        tvTotalPrice.setText(formatPrice(order.getTotalPrice()));

        return rootView;
    }
}
