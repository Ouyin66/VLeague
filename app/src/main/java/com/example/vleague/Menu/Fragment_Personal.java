package com.example.vleague.Menu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vleague.Activity.Activity_List_Bank;
import com.example.vleague.Activity.Activity_Login;
import com.example.vleague.Activity.Activity_Order_History;
import com.example.vleague.Activity.AddnEdit.Activity_Edit_Personal;
import com.example.vleague.Class.Bank;
import com.example.vleague.DBHelper;
import com.example.vleague.MainActivity;
import com.example.vleague.MyDatabase_User;
import com.example.vleague.MyDatabase_bank;
import com.example.vleague.MyDatabase_ticket;
import com.example.vleague.Order;
import com.example.vleague.R;
import com.example.vleague.Class.User;
import com.example.vleague.UserSession;

import java.util.ArrayList;

public class Fragment_Personal extends Fragment {

    Button btnChinhSua, btnListBank, btnOrderHistory;
    ImageButton ibtnDangXuat;
    View rootView;
    MyDatabase_User myDatabase_user;

    TextView tvName, tvPhone, tvEmail;
    ListView listBank, listOrder;
    ArrayList<Bank> bankArrayList;
    MyDatabase_bank myDatabase_bank;
    MyDatabase_ticket myDatabase_ticket;
    ArrayList<Order> orderArrayList;

    String name, phone, pass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_personal, container, false);

        btnChinhSua = (Button) rootView.findViewById(R.id.btnEdit);
        ibtnDangXuat = (ImageButton) rootView.findViewById(R.id.ibtnExit);

        tvName = (TextView) rootView.findViewById(R.id.tvName);
        tvEmail = (TextView) rootView.findViewById(R.id.tvEmail);
        tvPhone = (TextView) rootView.findViewById(R.id.tvPhone);
        listBank = (ListView) rootView.findViewById(R.id.listBank);
        btnListBank = (Button) rootView.findViewById(R.id.btnListBank);
        btnOrderHistory = (Button) rootView.findViewById(R.id.btnOrderHistory);
        listOrder = (ListView) rootView.findViewById(R.id.listOrder);

        bankArrayList = new ArrayList<>();
        orderArrayList = new ArrayList<>();

        myDatabase_bank = new MyDatabase_bank(rootView.getContext());
        myDatabase_user = new MyDatabase_User(rootView.getContext());
        myDatabase_ticket = new MyDatabase_ticket(rootView.getContext());

        btnChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();

                user.setId(UserSession.userId);
                user.setHoTen(name);
                user.setPhone(phone);
                user.setPassword(pass);

                Bundle bundle = new Bundle();

                bundle.putSerializable("user", user);

                Intent intent = new Intent(rootView.getContext(), Activity_Edit_Personal.class);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        dangNhapNguoiDung();

        ibtnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSession.isLogged = false;

                Intent intent = new Intent(rootView.getContext(), MainActivity.class);
                rootView.getContext().startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        dangNhapNguoiDung();
//        DanhSachNganHang();
    }

    public void dangNhapNguoiDung() {
        if (UserSession.isLogged) {
            String emailUser = UserSession.userEmailCurrent;

            LayThongTinUser(emailUser);

//            DanhSachNganHang();
//
//            DanhSachHoaDon();

            btnListBank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(rootView.getContext(), Activity_List_Bank.class);
                    rootView.getContext().startActivity(intent);
                }
            });

            btnOrderHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(rootView.getContext(), Activity_Order_History.class);
                    rootView.getContext().startActivity(intent);
                }
            });

            Toast.makeText(rootView.getContext(), "Hello", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(rootView.getContext());

            builder.setTitle("Thông báo");
            builder.setMessage("Vui lòng đăng nhập!");

            builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(rootView.getContext(), Activity_Login.class);
                    rootView.getContext().startActivity(intent);
                }
            });

            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();

                    Intent intent = new Intent(rootView.getContext()
                            , MainActivity.class);
                    startActivity(intent);

                }
            });

            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    @SuppressLint("Range")
    public void LayThongTinUser(String emailUser) {
        Cursor cursor = myDatabase_user.getUserInfo(emailUser);
        User user = new User();

        if(cursor != null) {
            while(cursor.moveToNext()) {

                user.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.ID_USER)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(DBHelper.EMAIL_USER)));
                user.setHoTen(cursor.getString(cursor.getColumnIndex(DBHelper.HOTEN_USER)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(DBHelper.PHONE_USER)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(DBHelper.PASSWORD_USER)));
            }

            name = user.getHoTen();
            phone = user.getPhone();
            pass = user.getPassword();

            tvPhone.setText(user.getPhone());
            tvEmail.setText(user.getEmail());
            tvName.setText(user.getHoTen());
            UserSession.userId = user.getId();
        }

        cursor.close();
    }

}