package com.example.vleague.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vleague.Activity.AddnEdit.Activity_Add_Location;
import com.example.vleague.Activity.AddnEdit.Activity_Edit_Location;
import com.example.vleague.DBHelper;
import com.example.vleague.Class.Location;
import com.example.vleague.Adapter.Adapter_Location;
import com.example.vleague.MyDatabase_Location;
import com.example.vleague.R;

import java.util.ArrayList;

public class Activity_Manage_Location extends AppCompatActivity {

    ImageButton btnAdd;
    ListView listLocal;
    ArrayList<Location> locations = new ArrayList<>();
    MyDatabase_Location myDatabase_location;
    Adapter_Location adapter_location;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_location);

        btnAdd = (ImageButton) findViewById(R.id.ibtnAdd);
        listLocal = (ListView) findViewById(R.id.listLocal);
        myDatabase_location = new MyDatabase_Location(this);

        CapNhapDuLieuChoLocal();

//        listLocal.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Toast.makeText(Activity_Manage_Location.this, motionEvent, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(Activity_Manage_Location.this, Activity_Add_Location.class);
                startActivity(add);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        CapNhapDuLieuChoLocal();
    }

    @SuppressLint("Range")
    public void CapNhapDuLieuChoLocal() {
        //Kiểm tra nếu danh sách null thì tạo danh sách mới
        // Còn không thì xóa tất cả phần tử đang có
        if(locations == null) {
            locations = new ArrayList<Location>();
        } else {
            locations.removeAll(locations);
        }

        //Tạo cursor để chạy hàm lấy tất cả dữ liệu trong bảng location
        //ở file MyDatabase_Location
        Cursor cursor = myDatabase_location.LayTatCaDuLieu();

        if(cursor != null) {
            while (cursor.moveToNext()) {
                Location location = new Location();

                location.setLocation_id(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.LOCATION_ID)
                ));
                location.setLocation_Name(cursor.getString(
                        cursor.getColumnIndex(DBHelper.LOCATION_NAME)
                ));
                location.setLocation_Total_Seat(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.LOCATION_TOTAL_SEAT)
                ));

                locations.add(location);
            }
        }

        cursor.close();

        CapNhatListView();
    }

    public void CapNhatListView() {
        if (locations != null) {
            adapter_location = new Adapter_Location(locations, Activity_Manage_Location.this);
            listLocal.setAdapter(adapter_location);
            listLocal.invalidateViews();
        }

        listLocal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("Range")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Location location = new Location();
                Cursor cursor = myDatabase_location.LayDiaDiem(id);

                if(cursor != null && cursor.getCount() > 0
                        && cursor.moveToFirst()) {

                    do {
                        int idLocal = cursor.getInt(cursor.getColumnIndex(DBHelper.LOCATION_ID));

                        if(idLocal == id) {
                            location.setLocation_id(cursor.getInt(
                                    cursor.getColumnIndex(DBHelper.LOCATION_ID)
                            ));
                            location.setLocation_Name(cursor.getString(
                                    cursor.getColumnIndex(DBHelper.LOCATION_NAME)
                            ));
                            location.setLocation_Total_Seat(cursor.getInt(
                                    cursor.getColumnIndex(DBHelper.LOCATION_TOTAL_SEAT)
                            ));

                            Intent intent = new Intent(Activity_Manage_Location.this
                                    , Activity_Edit_Location.class);
                            Bundle bundle = new Bundle();

                            bundle.putLong("id", location.getLocation_id());
                            bundle.putString("localName", location.getLocation_Name());
                            bundle.putInt("localSeat", location.getLocation_Total_Seat());

                            intent.putExtra("GoiLocal" ,bundle);
                            startActivity(intent);

                            break;
                        }
                    } while (cursor.moveToNext());
                } else {
                    Log.i("DEBUG", "Hang ko co");
                }
            }
        });
    }

    public void btnBack14_onClick(View view) {
        finish();
    }
}