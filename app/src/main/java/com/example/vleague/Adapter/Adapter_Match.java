package com.example.vleague.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vleague.Class.Matches;
import com.example.vleague.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Match extends BaseAdapter {

    ArrayList<Matches> arrayList = new ArrayList<>();

    public Adapter_Match(ArrayList<Matches> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) { // i la vi tri cua phan tu
        return arrayList.get(i); // get(i) lay ra phan tu tai vi tri thu i
    }

    @Override
    public long getItemId(int i) {
        return arrayList.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rootView = View.inflate(viewGroup.getContext(), R.layout.layout_listview_match, null);

        //Anh xa tu layout match
        ImageView imgLogo1 = (ImageView) rootView.findViewById(R.id.imageLogo1);
        ImageView imgLogo2 = (ImageView) rootView.findViewById(R.id.imageLogo2);
        TextView tvDate = (TextView) rootView.findViewById(R.id.tvDate);
        TextView tvTime = (TextView) rootView.findViewById(R.id.tvTime);

        Matches matches = (Matches) getItem(position);

//        imgLogo1.setImageResource(matches.getLogo1());
//        imgLogo2.setImageResource(matches.getLogo2());

        Picasso.get().load(matches.getLogo1()).into(imgLogo1);
        Picasso.get().load(matches.getLogo2()).into(imgLogo2);

        tvDate.setText(matches.getDate());
        tvTime.setText(matches.getTime());

        return rootView;
    }
}
