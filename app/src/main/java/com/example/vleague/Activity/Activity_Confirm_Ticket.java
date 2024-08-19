package com.example.vleague.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vleague.Activity.AddnEdit.Activity_Add_Credit_Card;
import com.example.vleague.Class.Bank;
import com.example.vleague.Class.BankSession;
import com.example.vleague.DBHelper;
import com.example.vleague.Class.Matches;
import com.example.vleague.Adapter.Adapter_Bank;
import com.example.vleague.MyDatabase_Match;
import com.example.vleague.MyDatabase_User;
import com.example.vleague.MyDatabase_bank;
import com.example.vleague.MyDatabase_ticket;
import com.example.vleague.Order;
import com.example.vleague.R;
import com.example.vleague.Class.Ticket;
import com.example.vleague.Class.User;
import com.example.vleague.StatusPay;
import com.example.vleague.UserSession;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Activity_Confirm_Ticket extends AppCompatActivity {

    ArrayList<Matches> matches;
    ArrayList<Bank> bankArrayList;
    MyDatabase_Match myDatabase_match;
    MyDatabase_User myDatabase_user;
    MyDatabase_bank myDatabase_bank;
    MyDatabase_ticket myDatabase_ticket;

    String name1, name2, location, date, time;
    String seating_area;
    int round, price;
    String logo2, logo1 ;
    int newPrice, totalPrice;
    long idMatches = -1;
    long idTicket = -1;

    TextView tvName1, tvName2, tvDiaDiem
            , tvDate, tvTime, tvRound, tvPrice;
    TextView tvTotal;
    EditText quantity, edtName, edtPhone, edtEmail;
    ImageView Logo1, Logo2;
    Spinner spinner;
    Button btnThemNganHang, btnXacNhan;
    ListView listBank;
    int Quantity;
    int BankMoney;
    long BankId;
    Bank bank1 = new Bank();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_ticket);

        matches = new ArrayList<>();
        bankArrayList = new ArrayList<>();
        myDatabase_match = new MyDatabase_Match(this);
        myDatabase_user = new MyDatabase_User(this);
        myDatabase_bank = new MyDatabase_bank(this);
        myDatabase_ticket = new MyDatabase_ticket(this);

        spinner = (Spinner) findViewById(R.id.spinner);
        quantity = (EditText) findViewById(R.id.edtQuantity);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnThemNganHang = (Button) findViewById(R.id.btnThemNganHang);
        listBank = (ListView) findViewById(R.id.listBank);
        btnXacNhan = (Button) findViewById(R.id.btnXacNhan);

        quantity.setText("1");


        NhanThongTin();
        setSpinner();
        setPriceForSpinner();

        NhapThongTinNguoiDung(UserSession.userEmailCurrent);

        Log.i("DEBUG", String.valueOf(UserSession.isLogged));

        if(UserSession.isLogged) {
            CapNhatDuLieuBank();
            btnThemNganHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Activity_Confirm_Ticket.this,
                            Activity_List_Bank.class);

                    startActivity(intent);
                }
            });
            XacNhanDatVe();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(UserSession.isLogged) {
            CapNhatDuLieuBank();
            XacNhanDatVe();
        }
    }

    public void XacNhanDatVe() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("DEBUG", "hihi");
                AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_Confirm_Ticket.this);

                dialog.setTitle("Thông báo");
                dialog.setMessage("Xác nhận đặt vé?");

                dialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(BankMoney >= totalPrice && BankMoney != 0) {
                            BankMoney = BankMoney - totalPrice;
                            Log.i("DEBUG", "total = " + String.valueOf(totalPrice));

                            String cusName = edtName.getText().toString();
                            String cusPhone = edtPhone.getText().toString();
                            String cusEmail = edtEmail.getText().toString();

                            if(cusName.isEmpty() || cusPhone.isEmpty() || cusEmail.isEmpty()) {
                                Toast.makeText(Activity_Confirm_Ticket.this,
                                        "Vui lòng nhập thông tin!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if(myDatabase_bank.CapNhatCotBankMoney(BankMoney, BankId) != -1) {
                                StatusPay.Status = 0;

                                Ticket ticket = LayDuLieuVe();

                                if(ticket != null) {
                                    if(myDatabase_ticket.them(ticket) != -1) {
                                        idTicket = -1;

                                        Order order = LayDuLieuOrder(ticket);

                                        Toast.makeText(Activity_Confirm_Ticket.this
                                                , "Đặt vé thành công", Toast.LENGTH_SHORT).show();

                                        Bundle bundle = new Bundle();

                                        bundle.putSerializable("order", order);

                                        Intent intent = new Intent(Activity_Confirm_Ticket.this,
                                                Activity_Order_Success.class);

                                        intent.putExtras(bundle);

                                        startActivity(intent);
                                    }
                                }
                            }

                        } else {
                            Toast.makeText(Activity_Confirm_Ticket.this
                                    , "Tài khoản không đủ tiền", Toast.LENGTH_SHORT).show();
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
        });
    }

    public Ticket LayDuLieuVe() {
        String cusName = edtName.getText().toString();
        String cusPhone = edtPhone.getText().toString();
        String cusEmail = edtEmail.getText().toString();

        //Lay ra ngay thang nam hien tai
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String BookDate = day + "/" + month + "/" + year;
        String BookTime = hour + ":" + minute;

        Ticket ticket = new Ticket();

        ticket.setId(idTicket);
        ticket.setId_cus(UserSession.userId);
        ticket.setCus_name(cusName);
        ticket.setCus_phone(cusPhone);
        ticket.setCus_email(cusEmail);
        ticket.setId_match(idMatches);
        ticket.setSeating_area(seating_area);
        ticket.setQuantity(Integer.parseInt(quantity.getText().toString()));
        ticket.setTotal_price(newPrice * Integer.parseInt(quantity.getText().toString()));
        ticket.setBooking_date(BookDate + ", " + BookTime);
        ticket.setStatusPay(StatusPay.Status);

        return ticket;
    }

    public Order LayDuLieuOrder(Ticket ticket) {
        Order order = new Order();

        order.setLogo1(logo1);
        order.setLogo2(logo2);
        order.setName1(name1);
        order.setName2(name2);
        order.setDate(date);
        order.setLocal(location);
        order.setTime(time);
        order.setRound(round);
        order.setSeat(ticket.getSeating_area());
        order.setPrice(price);
        order.setQuantity(ticket.getQuantity());
        order.setTotalPrice((int) ticket.getTotal_price());
        order.setDateBooking(ticket.getBooking_date());

        return order;
    }

    @SuppressLint("Range")
    public void CapNhatDuLieuBank() {

        if(bankArrayList == null) {
            bankArrayList = new ArrayList<>();
        } else {
            bankArrayList.removeAll(bankArrayList);
        }

        Cursor cursor = null;

        cursor = myDatabase_bank.LayBankTheoId(BankSession.idBank);

        if(cursor != null ) {
            while (cursor.moveToNext()) {
                Bank bank = new Bank();

                bank.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.ID_BANK)));
                bank.setBankCusId(cursor.getInt(cursor.getColumnIndex(DBHelper.ID_BANK_CUS)));
                bank.setNameBank(cursor.getString(cursor.getColumnIndex(DBHelper.BANK_NAME)));
                bank.setBankNum(cursor.getString(cursor.getColumnIndex(DBHelper.BANK_NUM)));
                bank.setBankMoney(cursor.getInt(cursor.getColumnIndex(DBHelper.BANK_MONEY)));

                bankArrayList.add(bank);
            }

            setListViewBank();
        } else {
            Log.i("DEBUG", "Khong tim thay bank");
        }
    }

    public void setListViewBank() {
        if(bankArrayList != null) {
            Adapter_Bank adapterBank = new Adapter_Bank(bankArrayList);
            listBank.setAdapter(adapterBank);
        }

        BankId = BankSession.idBank;
        BankMoney = myDatabase_bank.ThanhToan(BankId);
        Log.i("DEBUG", String.valueOf(BankId) + String.valueOf(BankMoney));

//        listBank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long idBankCard) {
//                Log.i("DEBUG", String.valueOf(idBankCard + 2));
//
//
//            }
//        });
    }

    public String formatPrice(int price) {
        String priceInput = String.valueOf(price);
        double priceValue = Double.parseDouble(priceInput);
        DecimalFormat decimalFormat = new DecimalFormat("###,### VND");
        String priceOutput = decimalFormat.format(priceValue);

        return priceOutput;
    }

    public void setChangePrice(int newPrice) {
        Quantity = 1;
        totalPrice = newPrice * Quantity;
        tvTotal.setText(formatPrice(totalPrice));

        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                setPriceForSpinner();
//                Quantity = 1;
//                totalPrice = newPrice * Quantity;
//                tvTotal.setText(formatPrice(totalPrice));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty()) {
                    Quantity = 0;
                } else {
                    Quantity = Integer.parseInt(charSequence.toString());
                }

                if(Quantity <= 0) {
                    quantity.setText("1");
                    Toast.makeText(Activity_Confirm_Ticket.this, "Số lượng vé không hợp lệ!", Toast.LENGTH_SHORT).show();
                } else {
                    totalPrice = newPrice * Quantity;
                }

                tvTotal.setText(formatPrice(totalPrice));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setPriceForSpinner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                switch (i) {
                    case 0:
                    case 1:
                        if(i == 0) {
                            seating_area = "Khu A";
                        } else {
                            seating_area = "Khu B";
                        }

                        newPrice = price + 75000;
                        tvPrice.setText(formatPrice(newPrice));
                        tvTotal.setText(formatPrice(newPrice));

                        setChangePrice(newPrice);
                        break;
                    case 2:
                    case 3:
                        if(i == 2) {
                            seating_area = "Khu C";
                        } else {
                            seating_area = "Khu D";
                        }

                        newPrice = price;
                        tvPrice.setText(formatPrice(newPrice));
                        tvTotal.setText(formatPrice(newPrice));

                        setChangePrice(newPrice);
                        break;
                    default:
                        seating_area = "Khu A";

                        newPrice = price + 75000;
                        tvPrice.setText(formatPrice(newPrice));
                        tvTotal.setText(formatPrice(newPrice));

                        setChangePrice(newPrice);
                        break;
                };
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner.setSelection(0);
    }

    public void setSpinner() {
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Khu A");
        arrayList.add("Khu B");
        arrayList.add("Khu C");
        arrayList.add("Khu D");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayList);

        spinner.setAdapter(adapter);
    }

    @SuppressLint("Range")
    public void NhanThongTin() {
        Intent goiIntent = getIntent();
        Bundle bundle = goiIntent.getBundleExtra("GoiMatches");

        idMatches = bundle.getLong("id");
        name1 = bundle.getString("name1");
        name2 = bundle.getString("name2");
        logo1 = bundle.getString("logo1");
        logo2 = bundle.getString("logo2");
        location = bundle.getString("location");
        date = bundle.getString("date");
        time = bundle.getString("time");
        round = bundle.getInt("round");
        price = bundle.getInt("price");

        Logo1 = (ImageView) findViewById(R.id.imgLogo1);
        Logo2 = (ImageView) findViewById(R.id.imgLogo2);
        tvName1 = (TextView) findViewById(R.id.tvNameTeam1);
        tvName2 = (TextView) findViewById(R.id.tvNameTeam2);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvDiaDiem = (TextView) findViewById(R.id.tvLocation);
        tvRound = (TextView) findViewById(R.id.tvRound);
        tvPrice = (TextView) findViewById(R.id.tvPrice);

//        Logo1.setImageResource(logo1);
//        Logo2.setImageResource(logo2);
        Picasso.get().load(logo1).into(Logo1);
        Picasso.get().load(logo2).into(Logo2);

        tvName1.setText(name1);
        tvName2.setText(name2);
        tvDate.setText(date);
        tvTime.setText(time);
        tvDiaDiem.setText(location);
        tvRound.setText("Vòng " + String.valueOf(round));
    }

    @SuppressLint("Range")
    public void NhapThongTinNguoiDung(String email) {
        Cursor cursor = myDatabase_user.getUserInfo(email);

        if(cursor != null) {
            while(cursor.moveToNext()) {
                User user = new User();
                user.setEmail(
                        cursor.getString(cursor.getColumnIndex(DBHelper.EMAIL_USER))
                );
                user.setHoTen(
                        cursor.getString(cursor.getColumnIndex(DBHelper.HOTEN_USER))
                );
                user.setPhone(
                        cursor.getString(cursor.getColumnIndex(DBHelper.PHONE_USER))
                );

                edtName.setText(user.getHoTen());
                edtEmail.setText(user.getEmail());
                edtPhone.setText(user.getPhone());
            }
        }

        cursor.close();
    }

    public void btnBack26_onClick(View view) {
        finish();
    }
}

