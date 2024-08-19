package com.example.vleague.Activity.AddnEdit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vleague.Activity.Admin.Activity_Manage_Location;
import com.example.vleague.Class.Location;
import com.example.vleague.MyDatabase_Location;
import com.example.vleague.R;

public class Activity_Add_Location extends AppCompatActivity {

    EditText edtNameLocal, edtTotalSeat;
    Button btnAddLocal;
    String nameLocation;
    int totalSeat;
    private long id = -1;
    MyDatabase_Location myDatabase_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        edtNameLocal = (EditText) findViewById(R.id.edtNameLocal);
        edtTotalSeat = (EditText) findViewById(R.id.edtTotalSeat);
        btnAddLocal = (Button) findViewById(R.id.btnAddLocal);
        myDatabase_location = new MyDatabase_Location(this);

        btnAddLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameLocal = edtNameLocal.getText().toString();
                String totalSeat = edtTotalSeat.getText().toString();

                if(!nameLocal.isEmpty() || !totalSeat.isEmpty()) {
                    //Khi nhấn vào nút thêm sẽ lấy dữ liệu của edt ra thông qua
                    // hàm LayDiaDiem()

                    if(myDatabase_location.KiemTraTrungTen(nameLocal)) {
                        Toast.makeText(Activity_Add_Location.this,
                                "Địa điểm này đã tồn tại!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Location location = LayDiaDiem();

                    //Kiểm tra nếu khác null thì thực hiện
                    if(location != null) {
                        //Kiểm tra nếu thêm thành công tức khác -1 thì
                        //set giá trị null cho tất cả các edt
                        if(myDatabase_location.AddLocation(location) != -1) {
                            edtNameLocal = null;
                            edtTotalSeat = null;
                            id = -1;

                            finish();
                        }
                    } else {
                        //Nếu class location không có giá trị sẽ trả về null
                        //Tức là admin không nhập gì cả mà vẫn nhấn thêm
                        //Thông báo yêu cầu nhập thông tin đầy đủ mới đc
                        String mess = "Vui lòng nhập đầy đủ thông tin!";
                        Toast.makeText(Activity_Add_Location.this, mess, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Activity_Add_Location.this,
                            "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public Location LayDiaDiem() {
        nameLocation = edtNameLocal.getText().toString();
        totalSeat = Integer.parseInt(edtTotalSeat.getText().toString());

        Location location = new Location();

        location.setLocation_id(id);
        location.setLocation_Name(nameLocation);
        location.setLocation_Total_Seat(totalSeat);

        return location;
    }

    public void btnBack1_onClick(View view) {
        finish();
    }
}