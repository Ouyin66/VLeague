package com.example.vleague.Activity.AddnEdit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vleague.Class.Location;
import com.example.vleague.MyDatabase_Location;
import com.example.vleague.R;

public class Activity_Edit_Location extends AppCompatActivity {

    EditText edtNameLocal, edtTotalSeat;
    Button btnEditLocal;

    long id;
    String nameLocal;
    int totalSeatLocal;
    MyDatabase_Location myDatabase_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);

        myDatabase_location = new MyDatabase_Location(this);
        Location location = new Location();

        edtNameLocal = (EditText) findViewById(R.id.edtNameLocal);
        edtTotalSeat = (EditText) findViewById(R.id.edtTotalSeat);
        btnEditLocal = (Button) findViewById(R.id.btnEdit);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("GoiLocal");

        id = bundle.getLong("id");
        nameLocal = bundle.getString("localName");
        totalSeatLocal = bundle.getInt("localSeat");

        edtNameLocal.setText(nameLocal);
        edtTotalSeat.setText(String.valueOf(totalSeatLocal));



        btnEditLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameLocal = edtNameLocal.getText().toString();
                String totalSeat = edtTotalSeat.getText().toString();

                if(nameLocal.isEmpty() || totalSeat.isEmpty()) {
                    Toast.makeText(Activity_Edit_Location.this,
                            "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {

                    Location location1 = layDuLieu();

                    AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_Edit_Location.this);

                    dialog.setTitle("Thông báo!");
                    dialog.setMessage("Bạn có chắn chắn không?");

                    dialog.setPositiveButton("Chắc chắn", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(location1 != null && id != -1) {
                                myDatabase_location.suaDiaDiem(location1);

                                finish();
                            }
                        }
                    });

                    dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    dialog.create();
                    dialog.show();
                }

            }
        });
    }

    public Location layDuLieu() {
        String name = edtNameLocal.getText().toString();
        int totalSeat = Integer.parseInt(edtTotalSeat.getText().toString());

        Location location = new Location();

        location.setLocation_id(id);
        location.setLocation_Name(name);
        location.setLocation_Total_Seat(totalSeat);

        return location;
    }

    public void btnBack7_onClick(View view) {
        finish();
    }
}