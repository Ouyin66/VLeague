package com.example.vleague.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vleague.Activity.AddnEdit.Activity_Edit_Customer;
import com.example.vleague.Adapter.Adapter_Customer;
import com.example.vleague.Class.Matches;
import com.example.vleague.DBHelper;
import com.example.vleague.MyDatabase_User;
import com.example.vleague.R;
import com.example.vleague.Class.User;

import java.util.ArrayList;

public class Activity_Manage_Customer extends AppCompatActivity {

    ListView listCus;
    EditText edtSearch;
    ArrayList<User> users = new ArrayList<>();
    MyDatabase_User myDatabase_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_customer);

        listCus = (ListView) findViewById(R.id.listCus);
        edtSearch = (EditText) findViewById(R.id.edtSearch);

        myDatabase_user = new MyDatabase_User(this);

        CapNhatDuLieu();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString();

                if(searchText.isEmpty()) {
                    CapNhatDuLieu();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString();

                if(searchText.isEmpty()) {
                    CapNhatDuLieu();
                } else {
                    Search(searchText);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listCus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("Range")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                User user = new User();

                long itemId = adapterView.getItemIdAtPosition(i);
                Log.i("DEBUG", String.valueOf(itemId));

                Cursor cursor = myDatabase_user.LayNguoiDungBoiId(itemId);

                if(cursor != null && cursor.getCount() > 0
                        && cursor.moveToFirst()) {
                    do {
                        int idUser = cursor.getInt(cursor.getColumnIndex(DBHelper.ID_USER));
                        Log.i("DEBUG", String.valueOf(idUser));

                        if(idUser == itemId) {
                            user.setId(cursor.getInt(
                                    cursor.getColumnIndex(DBHelper.ID_USER)
                            ));
                            user.setHoTen(cursor.getString(
                                    cursor.getColumnIndex(DBHelper.HOTEN_USER)
                            ));
                            user.setPhone(cursor.getString(
                                    cursor.getColumnIndex(DBHelper.PHONE_USER)
                            ));
                            user.setEmail(cursor.getString(
                                    cursor.getColumnIndex(DBHelper.EMAIL_USER)
                            ));
                            user.setRole(cursor.getInt(
                                    cursor.getColumnIndex(DBHelper.ROLE_USER)
                            ));
                            user.setPassword(cursor.getString(
                                    cursor.getColumnIndex(DBHelper.PASSWORD_USER)
                            ));

                            Intent intent = new Intent(Activity_Manage_Customer.this
                                    , Activity_Edit_Customer.class);

                            Bundle bundle = new Bundle();

                            bundle.putLong("id", user.getId());
                            bundle.putString("name", user.getHoTen());
                            bundle.putString("phone", user.getPhone());
                            bundle.putString("email", user.getEmail());
                            bundle.putString("pass", user.getPassword());
                            bundle.putLong("role", user.getRole());

                            intent.putExtra("GoiUser", bundle);
                            startActivity(intent);

                            break;
                        }
                    } while (cursor.moveToNext());
                } else {
                    Log.i("DEBUG", "Failed");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        CapNhatDuLieu();
    }

    @SuppressLint("Range")
    public void Search(String nameUser) {
        if(users == null) {
            users = new ArrayList<User>();
        } else {
            users.removeAll(users);
        }

        String newText = nameUser;

        Cursor cursor = myDatabase_user.Search(newText);

        if(cursor != null) {
            while (cursor.moveToNext()) {
                User user = new User();

                user.setId(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.ID_USER)
                ));
                user.setHoTen(cursor.getString(
                        cursor.getColumnIndex(DBHelper.HOTEN_USER)
                ));
                user.setPhone(cursor.getString(
                        cursor.getColumnIndex(DBHelper.PHONE_USER)
                ));

                users.add(user);
            }
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }

        CapNhatListView();
    }

    @SuppressLint("Range")
    public void CapNhatDuLieu() {
        if(users == null) {
            users = new ArrayList<>();
        } else {
            users.removeAll(users);
        }

        Cursor cursor = myDatabase_user.LayDuLieuNguoiDung();

        if(cursor != null) {
            while (cursor.moveToNext()) {
                User user1 = new User();

                user1.setId(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.ID_USER)
                ));
                user1.setHoTen(cursor.getString(
                        cursor.getColumnIndex(DBHelper.HOTEN_USER)
                ));
                user1.setPhone(cursor.getString(
                        cursor.getColumnIndex(DBHelper.PHONE_USER)
                ));

                users.add(user1);
            }
        }

        cursor.close();
        CapNhatListView();
        listCus.invalidateViews();
    }

    public void CapNhatListView() {
        if(users != null) {
            listCus.setAdapter(new Adapter_Customer(users));
        }
    }

    public void btnBack13_onClick(View view) {
        finish();
    }
}