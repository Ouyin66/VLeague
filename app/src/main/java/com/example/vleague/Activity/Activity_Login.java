package com.example.vleague.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vleague.Activity.Admin.Activity_Manage;
import com.example.vleague.DBHelper;
import com.example.vleague.MainActivity;
import com.example.vleague.MyDatabase_User;
import com.example.vleague.R;
import com.example.vleague.Class.User;
import com.example.vleague.UserSession;

public class Activity_Login extends AppCompatActivity {
    Button btnLogin;
    EditText edtMail, edtPass;
    CheckBox checkSave;
    MyDatabase_User database_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtMail = (EditText) findViewById(R.id.inputEmail);
        edtPass = (EditText) findViewById(R.id.inputPass);
        checkSave = (CheckBox) findViewById(R.id.chkSave);

        database_user = new MyDatabase_User(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtMail.getText().toString();
                String pass = edtPass.getText().toString();

                if(database_user.KiemTraDangNhap(email, pass)) {
                    String userRole = database_user.KiemTraRole(email);

                    if(userRole.equals("0")) {
                        UserSession.isLogged = true;
                        UserSession.userEmailCurrent = email;

                        LayThongTinUser(email);

                        SavingPreferences();

                        Intent login = new Intent(Activity_Login.this, MainActivity.class);
                        Activity_Login.this.startActivity(login);
                    } else {
                        Intent login = new Intent(Activity_Login.this, Activity_Manage.class);
                        login.putExtra("email", email);
                        Activity_Login.this.startActivity(login);
                    }

                } else {
                    Toast.makeText(Activity_Login.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SavingPreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();

        retoringPreferences();
    }

    public void btnSignUp_onClick(View view) {
        Intent signup = new Intent(Activity_Login.this, Activity_SignUp.class);
        startActivity(signup);
    }

    private void SavingPreferences() {
        SharedPreferences pre = this.getSharedPreferences("preFile", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pre.edit();

        String userEmail = edtMail.getText().toString();
        String userPass = edtPass.getText().toString();

        boolean check = checkSave.isChecked();

        if(!check) {
            editor.clear();
        } else {
            editor.putString("email", userEmail);
            editor.putString("pass", userPass);
            editor.putBoolean("checked", check);
        }

        editor.commit();
    }

    private void retoringPreferences() {
        SharedPreferences preferences = this.getSharedPreferences("preFile", Context.MODE_PRIVATE);

        if(preferences != null) {
            boolean isCheck = preferences.getBoolean("checked", false);

            if(isCheck) {
                String userEmail = preferences.getString("email", "");
                String pass = preferences.getString("pass", "");

                edtMail.setText(userEmail);
                edtPass.setText(pass);
            }

            checkSave.setChecked(isCheck);
        }
    }

    @SuppressLint("Range")
    public void LayThongTinUser(String emailUser) {
        Cursor cursor = database_user.getUserInfo(emailUser);
        User user = new User();

        if(cursor != null) {
            while(cursor.moveToNext()) {

                user.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.ID_USER)));
            }

            UserSession.userId = user.getId();
        }

        cursor.close();
    }
}