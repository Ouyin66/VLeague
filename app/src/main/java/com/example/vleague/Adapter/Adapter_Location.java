package com.example.vleague.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vleague.Class.Location;
import com.example.vleague.MyDatabase_Location;
import com.example.vleague.R;

import java.util.ArrayList;

public class Adapter_Location extends BaseAdapter {
    ArrayList<Location> arrayList = new ArrayList<>();
    Context context;
    TextView tvName, tvSeat;
    MyDatabase_Location myDatabase_location;

    public Adapter_Location(ArrayList<Location> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
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
        return arrayList.get(i).getLocation_id();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.layout_listview_location, viewGroup, false);

        tvName = (TextView) rootView.findViewById(R.id.tvNameLocation);
        tvSeat = (TextView) rootView.findViewById(R.id.tvTotalSeat);
        myDatabase_location = new MyDatabase_Location(rootView.getContext());

        Location Location = arrayList.get(i);

        tvName.setText(Location.getLocation_Name());
        tvSeat.setText("Số lượng ghế: " + Location.getLocation_Total_Seat());
        ((ImageButton) rootView.findViewById(R.id.ibtnDelete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabase_location.delLocation(Location);
                Toast.makeText(rootView.getContext(), "Thanh cong", Toast.LENGTH_SHORT).show();

                arrayList.remove(i);
                notifyDataSetChanged();
            }
        });

        return rootView;
    }
}
