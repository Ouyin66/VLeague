package com.example.vleague.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.vleague.Activity.AddnEdit.Activity_Add_Credit_Card;
import com.example.vleague.Adapter.Adapter_Bank;
import com.example.vleague.Class.Bank;
import com.example.vleague.Class.BankSession;
import com.example.vleague.DBHelper;
import com.example.vleague.MyDatabase_bank;
import com.example.vleague.R;
import com.example.vleague.UserSession;

import java.util.ArrayList;

public class Activity_List_Bank extends AppCompatActivity {

    MyDatabase_bank myDatabase_bank;
    ArrayList<Bank> bankArrayList;
    ListView listBank;
    Button btnAddBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bank);

        listBank = (ListView) findViewById(R.id.listBank);
        btnAddBank = (Button) findViewById(R.id.btnListBank);

        myDatabase_bank = new MyDatabase_bank(this);
        bankArrayList = new ArrayList<>();

        DanhSachNganHang();

        btnAddBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_List_Bank.this
                        , Activity_Add_Credit_Card.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        DanhSachNganHang();
    }

    @Override
    protected void onResume() {
        super.onResume();

        DanhSachNganHang();
    }

    @SuppressLint("Range")
    public void DanhSachNganHang() {
        long idUserCurrent = UserSession.userId;

        if(bankArrayList == null) {
            bankArrayList = new ArrayList<>();
        } else {
            bankArrayList.removeAll(bankArrayList);
        }

        Cursor cursor = null;

        cursor = myDatabase_bank.LayTatCaBankTheoId(idUserCurrent);

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

        listBank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long idBank) {
                BankSession.idBank = idBank;
                finish();
            }
        });
    }

    public void btnBack28_onClick(View view) {
        finish();
    }


}