package com.example.vleague.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vleague.Class.News;
import com.example.vleague.Class.Team;
import com.example.vleague.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Team extends BaseAdapter {
    ArrayList<Team> teamArrayList;

    public Adapter_Team(ArrayList<Team> newsArrayList) {
        this.teamArrayList = newsArrayList;
    }

    @Override
    public int getCount() {
        return teamArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return teamArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return teamArrayList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = View.inflate(viewGroup.getContext(), R.layout.layout_listview_team, null);

        ImageView imgLogo = (ImageView) rootView.findViewById(R.id.imgLogo);
        TextView nameTeam = (TextView) rootView.findViewById(R.id.tvTeam);

        Team team = (Team) getItem(i);

        Picasso.get().load(team.getLogo()).into(imgLogo);
        nameTeam.setText(team.getName());
        return rootView;
    }
}
