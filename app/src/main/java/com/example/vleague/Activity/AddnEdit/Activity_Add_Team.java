package com.example.vleague.Activity.AddnEdit;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class Activity_Add_Team extends AppCompatActivity {

    ImageView imgLogo;
    EditText edtLogo, edtName;
    Button btnXacNhan;

    String name, logo;
    long id = -1;
    MyDatabase_team myDatabase_team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);

        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        edtLogo = (EditText) findViewById(R.id.edtLogoTeam);
        edtName = (EditText) findViewById(R.id.edtNameTeam);
        btnXacNhan = (Button) findViewById(R.id.btnEdit);

        myDatabase_team = new MyDatabase_team(this);

        edtLogo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String url = charSequence.toString().trim();

                if (!url.isEmpty()) {
                    Picasso.get().load(url).into(imgLogo);
                } else {
                    Picasso.get().load(R.drawable.viettelfc).into(imgLogo);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtName.getText().toString();
                logo = edtLogo.getText().toString();

                if(name.isEmpty() || logo.isEmpty()) {
                    Toast.makeText(Activity_Add_Team.this,
                            "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    if(myDatabase_team.KiemTraTrungTen(name)) {
                        Toast.makeText(Activity_Add_Team.this,
                                "Đội bóng này đã tồn tại!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Team team = LayDuLieuAdd();

                    if(myDatabase_team.AddTeam(team) != -1) {
                        edtLogo.setText(null);
                        edtName.setText(null);
                        id = -1;

                        Toast.makeText(Activity_Add_Team.this,
                                "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

//                Team team = LayDuLieuAdd();
//
//                if(myDatabase_team.AddTeam(team) != -1) {
//                    edtLogo.setText(null);
//                    edtName.setText(null);
//                    id = -1;
//
//                    Toast.makeText(Activity_Add_Team.this,
//                            "Thêm thành công!", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
            }
        });

    }

    public Team LayDuLieuAdd() {
        logo = edtLogo.getText().toString();
        name = edtName.getText().toString();

        Team team = new Team();

        team.setLogo(logo);
        team.setName(name);

        return team;
    }

    public void btnBack4_onClick(View view) {
        finish();
    }
}