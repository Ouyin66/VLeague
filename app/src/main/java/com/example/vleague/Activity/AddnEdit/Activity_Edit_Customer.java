package com.example.vleague.Activity.AddnEdit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vleague.MyDatabase_User;
import com.example.vleague.R;
import com.example.vleague.Class.User;

import java.util.ArrayList;

public class Activity_Edit_Customer extends AppCompatActivity {

    EditText edtHoTen, edtPhone, edtEmail, edtPass;
    Button btnEditUser;
    Spinner spinnerRole;

    long id, role;
    String name, phone, email, pass;

    MyDatabase_User myDatabase_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);

        edtHoTen = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPass = (EditText) findViewById(R.id.edtPass);
        btnEditUser = (Button) findViewById(R.id.btnEdit);
        spinnerRole = (Spinner) findViewById(R.id.spinnerRole);

        myDatabase_user = new MyDatabase_User(this);

        setRoleForSpinner();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("GoiUser");

        id = bundle.getLong("id");
        name = bundle.getString("name");
        phone = bundle.getString("phone");
        email = bundle.getString("email");
        role = bundle.getLong("role");
        pass = bundle.getString("pass");

        edtHoTen.setText(name);
        edtEmail.setText(email);
        edtPhone.setText(phone);
        edtPass.setText(pass);

        int roleDefault = (int) role;
        Log.i("DEBUG", String.valueOf(roleDefault));

        for(int i = 0; i < spinnerRole.getCount(); i++) {
            if(i == roleDefault) {
                spinnerRole.setSelection(i);
            }
        }

        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        role = 0;
                        break;
                    case 1:
                        role = 1;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editUser();
    }

    public void setRoleForSpinner() {
        ArrayList<String> roles = new ArrayList<>();

        roles.add("User");
        roles.add("Admin");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this
                , androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, roles);

        spinnerRole.setAdapter(adapter);

    }

    public void editUser() {
        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
                String email = edtEmail.getText().toString();
                String hoten = edtHoTen.getText().toString();
                String phone = edtPhone.getText().toString();
                String pass = edtPass.getText().toString();

                if(email.isEmpty() || hoten.isEmpty() || phone.isEmpty()
                        || pass.isEmpty()) {
                    Toast.makeText(Activity_Edit_Customer.this,
                            "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(email.matches(emailRegex)) {
                        User user = LayDuLieu();

                        AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_Edit_Customer.this);

                        dialog.setTitle("Thông báo!");
                        dialog.setMessage("Bạn có chắn chắn muốn thay dổi không?");

                        dialog.setPositiveButton("Chắn chắn", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(user != null && id != -1) {
                                    myDatabase_user.SuaUser(user);

                                    Toast.makeText(Activity_Edit_Customer.this, "Thành công"
                                            , Toast.LENGTH_SHORT).show();
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
                    } else {
                        Toast.makeText(Activity_Edit_Customer.this,
                                "Vui lòng nhập đúng email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public User LayDuLieu() {
        String name = edtHoTen.getText().toString();
        String phone = edtPhone.getText().toString();
        String email = edtEmail.getText().toString();
        String pass = edtPass.getText().toString();
        long role1 = role;

        User user = new User();

        user.setId(id);
        user.setHoTen(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole(role1);
        user.setPassword(pass);

        return user;
    }

    public void btnBack6_onClick(View view) {
        finish();
    }
}