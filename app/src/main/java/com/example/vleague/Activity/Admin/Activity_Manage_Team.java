package com.example.vleague.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.vleague.Activity.AddnEdit.Activity_Add_Location;
import com.example.vleague.Activity.AddnEdit.Activity_Add_Team;
import com.example.vleague.Activity.AddnEdit.Activity_Edit_News;
import com.example.vleague.Activity.AddnEdit.Activity_Edit_Team;
import com.example.vleague.Adapter.Adapter_Team;
import com.example.vleague.Class.Team;
import com.example.vleague.DBHelper;
import com.example.vleague.MyDatabase_team;
import com.example.vleague.R;

import java.util.ArrayList;

public class Activity_Manage_Team extends AppCompatActivity {

    ImageButton btnAdd;
    ListView listTeam;
    ArrayList<Team> teamArrayList;
    MyDatabase_team myDatabase_team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_team);

        btnAdd = (ImageButton) findViewById(R.id.ibtnAdd);
        listTeam = (ListView) findViewById(R.id.ListTeam);

        teamArrayList = new ArrayList<>();
        myDatabase_team = new MyDatabase_team(this);

        CapNhatDuLieu();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(Activity_Manage_Team.this, Activity_Add_Team.class);
                startActivity(add);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        CapNhatDuLieu();
    }

    @SuppressLint("Range")
    public void CapNhatDuLieu() {
        if(teamArrayList == null) {
            teamArrayList = new ArrayList<>();
        } else {
            teamArrayList.removeAll(teamArrayList);
        }

        Cursor cursor = myDatabase_team.LayTatCaDuLieu();

        if(cursor != null) {
            while (cursor.moveToNext()) {
                Team team = new Team();

                team.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.TEAM_ID)));
                team.setLogo(cursor.getString(cursor.getColumnIndex(DBHelper.TEAM_IMG)));
                team.setName(cursor.getString(cursor.getColumnIndex(DBHelper.TEAM_NAME)));

                teamArrayList.add(team);
            }
        }

        CapNhatListView();
    }

    public void CapNhatListView() {
        if(teamArrayList != null) {
            Adapter_Team adapter_team = new Adapter_Team(teamArrayList);
            listTeam.setAdapter(adapter_team);
        }

        listTeam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Team team = (Team) adapterView.getItemAtPosition(i);

                Bundle bundle = new Bundle();
                bundle.putSerializable("team", team);
                Intent intent = new Intent(Activity_Manage_Team.this,
                        Activity_Edit_Team.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public void btnBack17_onClick(View view) {
        finish();
    }
}