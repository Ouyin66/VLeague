package com.example.vleague.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vleague.Class.Team;
import com.example.vleague.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterSpinnerTeam extends BaseAdapter {
    ArrayList<Team> arrayList;

    public AdapterSpinnerTeam(ArrayList<Team> arrayList) {
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
        View rootView = View.inflate(viewGroup.getContext(), R.layout.layout_spinner_team, null);

        TextView tvName = (TextView) rootView.findViewById(R.id.textView);

        Team team = (Team) getItem(i);

        tvName.setText(team.getName());

        return rootView;
    }
}
