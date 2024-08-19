package com.example.vleague.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vleague.Class.User;
import com.example.vleague.R;

import java.util.ArrayList;

public class Adapter_Customer extends BaseAdapter {
    ArrayList<User> arrayList = new ArrayList<>();
    TextView tvName, tvPhone;

    public Adapter_Customer(ArrayList<User> arrayList) {
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
        return arrayList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = View.inflate(viewGroup.getContext(), R.layout.layout_listview_customer, null);

        tvName = (TextView) rootView.findViewById(R.id.tvName);
        tvPhone = (TextView) rootView.findViewById(R.id.tvPhone);

        User user = (User) getItem(i);

        tvName.setText(user.getHoTen());
        tvPhone.setText(user.getPhone());

        return rootView;
    }
}
