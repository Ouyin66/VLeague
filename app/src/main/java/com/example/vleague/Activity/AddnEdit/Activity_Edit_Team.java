package com.example.vleague.Activity.AddnEdit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vleague.Class.Team;
import com.example.vleague.MyDatabase_team;
import com.example.vleague.R;
import com.squareup.picasso.Picasso;

public class Activity_Edit_Team extends AppCompatActivity {

    ImageView imgLogo;
    EditText edtLogo, edtName;
    Button btnXacNhan; ImageButton btnXoa;
    long id = -1;
    MyDatabase_team myDatabase_team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        edtLogo = (EditText) findViewById(R.id.edtLogoTeam);
        edtName = (EditText) findViewById(R.id.edtNameTeam);
        btnXacNhan = (Button) findViewById(R.id.btnEdit);
        btnXoa = (ImageButton) findViewById(R.id.btnDelete);

        myDatabase_team = new MyDatabase_team(this);

        Bundle bundle = getIntent().getExtras();
        Team team = (Team) bundle.getSerializable("team");

        Picasso.get().load(team.getLogo()).into(imgLogo);
        id = team.getId();
        edtLogo.setText(team.getLogo());
        edtName.setText(team.getName());

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Team team1 = LayDuLieu();

                if(myDatabase_team.xoaTeam(team1) != -1) {
                    Toast.makeText(Activity_Edit_Team.this,
                            "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Team team1 = LayDuLieu();

                if(myDatabase_team.suaTeam(team1) != -1) {
                    Toast.makeText(Activity_Edit_Team.this,
                            "Sửa thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    public Team LayDuLieu() {
        Team team = new Team();

        String logoLink = edtLogo.getText().toString();
        String name = edtName.getText().toString();

        team.setId(id);
        team.setLogo(logoLink);
        team.setName(name);

        return team;
    }

    public void btnBack11_onClick(View view) {
        finish();
    }
}