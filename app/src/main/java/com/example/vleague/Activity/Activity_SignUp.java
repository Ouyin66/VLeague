package com.example.vleague.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vleague.MyDatabase_User;
import com.example.vleague.R;
import com.example.vleague.Class.User;

public class Activity_SignUp extends AppCompatActivity {
    Button btnSignUp;
    EditText edtName, edtEmail, edtPhone, edtPass, edtRePass;
    CheckBox checkXacNhan;
    MyDatabase_User database_user;

    private String name, email, phone, pass, repass;
    private long id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPass = (EditText) findViewById(R.id.edtPass);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtRePass = (EditText) findViewById(R.id.edtRePass);
        checkXacNhan = (CheckBox) findViewById(R.id.chkXacNhan);

        database_user = new MyDatabase_User(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = edtName.getText().toString();
                email = edtEmail.getText().toString();
                phone = edtPhone.getText().toString();
                pass = edtPass.getText().toString();
                repass = edtRePass.getText().toString();

                boolean tontai = database_user.KiemTraEmail(email);
                String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

                if(name.isEmpty() || email.isEmpty() || phone.isEmpty()
                        || pass.isEmpty() || repass.isEmpty()) {
                    Toast.makeText(Activity_SignUp.this
                            , "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    if(pass.equals(repass)) {
                        if(tontai) {
                            Toast.makeText(Activity_SignUp.this
                                    ,"Email đã tồn tại!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(!email.matches(emailRegex)) {
                            Toast.makeText(Activity_SignUp.this,
                                    "Vui lòng nhập đúng email!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(checkXacNhan.isChecked()) {
                            User user = LayDuLieu();

                            if (user != null) {
                                if (database_user.ThemUser(user) != -1) {
                                    edtName.setText(null);
                                    edtEmail.setText(null);
                                    edtPass.setText(null);
                                    edtPhone.setText(null);
                                    id = -1;

                                    finish(); // Trở về trang trước đó
                                }
                            }
                        } else {
                            Toast.makeText(Activity_SignUp.this,
                                    "Vui lòng chọn Xác nhận thông tin", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(Activity_SignUp.this
                                , "Vui lòng nhập lại mật khẩu cho khớp!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public User LayDuLieu() {
        name = edtName.getText().toString();
        email = edtEmail.getText().toString();
        phone = edtPhone.getText().toString();
        pass = edtPass.getText().toString();

        User user = new User();

        user.setId(id);
        user.setHoTen(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(pass);
        user.setRole(0); // mặc định là Khách hàng

        return user;
    }

    public void btnLogin_onClick(View view) {
        Intent login = new Intent(Activity_SignUp.this, Activity_Login.class);
        startActivity(login);
    }
}