package com.example.vleague.Activity.AddnEdit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vleague.Class.Bank;
import com.example.vleague.MyDatabase_bank;
import com.example.vleague.R;
import com.example.vleague.UserSession;

import java.util.ArrayList;

public class Activity_Add_Credit_Card extends AppCompatActivity {

    EditText edtBankNum, edtCvv, edtDate, edtMoney;
    Spinner NameBank;
    Button btnAddBank;
    MyDatabase_bank myDatabase_bank;

    String name, num, cvv, date;
    long id = -1;
    int money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_card);

        NameBank = (Spinner) findViewById(R.id.edtBank);
        edtBankNum = (EditText) findViewById(R.id.edtBankNum);
        edtCvv = (EditText) findViewById(R.id.edtCvv);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtMoney = (EditText) findViewById(R.id.edtMoney);
        btnAddBank = (Button) findViewById(R.id.btnListBank);

        myDatabase_bank = new MyDatabase_bank(this);

        setBankName();

        btnAddBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num = edtBankNum.getText().toString();
                cvv = edtCvv.getText().toString();
                date = edtDate.getText().toString();
                String Money = edtMoney.getText().toString();

                if(num.isEmpty() || cvv.isEmpty() || date.isEmpty() || Money.isEmpty()) {
                    Toast.makeText(Activity_Add_Credit_Card.this,
                            "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                    Bank bank = LayDuLieuBank();

                    if(myDatabase_bank.ThemBank(bank) != -1) {
                        id = -1;

                        Toast.makeText(Activity_Add_Credit_Card.this
                                , "Liên kết thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Activity_Add_Credit_Card.this
                                , "Liên kết thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }

    public void setBankName() {
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Vietcombank");
        arrayList.add("Sacombank");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayList);

        NameBank.setAdapter(adapter);

        NameBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        name = arrayList.get(0);
                        break;
                    case 1:
                        name = arrayList.get(1);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public Bank LayDuLieuBank() {
        num = edtBankNum.getText().toString();
        cvv = edtCvv.getText().toString();
        date = edtDate.getText().toString();
        money = Integer.parseInt(edtMoney.getText().toString());

        Bank bank = new Bank();

        bank.setId(id);
        bank.setBankCusId((int) UserSession.userId);
        bank.setBankCvv(cvv);
        bank.setBankMoney(money);
        bank.setBankNum(num);
        bank.setNameBank(name);
        bank.setBankDate(date);

        return bank;
    }

    public void btnBack_onClick(View view) {
        finish();
    }
}