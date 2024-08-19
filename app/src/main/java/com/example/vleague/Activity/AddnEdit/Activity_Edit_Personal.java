package com.example.vleague.Activity.AddnEdit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vleague.Class.User;
import com.example.vleague.MyDatabase_User;
import com.example.vleague.R;
import com.example.vleague.UserSession;

public class Activity_Edit_Personal extends AppCompatActivity {

    EditText edtName, edtPhone, edtPass, edtRePass;
    Button btnEdit;
    MyDatabase_User myDatabase_user;

    String passDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal);

        edtName = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtPass = (EditText) findViewById(R.id.edtPass);
        edtRePass = (EditText) findViewById(R.id.edtRePass);
        btnEdit = (Button) findViewById(R.id.btnEdit);

        myDatabase_user = new MyDatabase_User(this);

        Bundle bundle = getIntent().getExtras();
        User user1 = (User) bundle.getSerializable("user");

        edtName.setText(user1.getHoTen());
        edtPhone.setText(user1.getPhone());
        passDefault = user1.getPassword();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = LayThongTinChinhSua();

                AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_Edit_Personal.this);

                dialog.setTitle("Thông báo");
                dialog.setMessage("Bạn có chắc chắn không?");

                String pass = edtPass.getText().toString();
                String repass = edtRePass.getText().toString();


                if(!repass.equals(pass)) {
                    Toast.makeText(Activity_Edit_Personal.this,
                            "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                        dialog.setPositiveButton("Chắc chắn", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(myDatabase_user.SuaUserCuaUser(user) != -1) {
                                    Toast.makeText(Activity_Edit_Personal.this,
                                            "Thành công", Toast.LENGTH_SHORT).show();
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
        });
    }

    public User LayThongTinChinhSua() {
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String pass = edtPass.getText().toString();
        String repass = edtRePass.getText().toString();

        if(pass.isEmpty() && repass.isEmpty()) {
            pass = passDefault;
        }

        User user = new User();

        user.setHoTen(name);
        user.setPhone(phone);
        user.setPassword(pass);
        user.setId(UserSession.userId);

        return user;
    }

    public void btnBack10_onClick(View view) {
        finish();
    }
}