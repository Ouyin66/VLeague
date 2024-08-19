package com.example.vleague;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.vleague.Menu.Fragment_Book_Tickets;
import com.example.vleague.Menu.Fragment_Home;
import com.example.vleague.Menu.Fragment_News;
import com.example.vleague.Menu.Fragment_Personal;
import com.example.vleague.Menu.Fragment_Schedule;
import com.example.vleague.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MyDatabase_User myDatabase_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        myDatabase_user = new MyDatabase_User(this);

        setContentView(binding.getRoot());
        replaceFragment(new Fragment_Home());

        binding.Navigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    replaceFragment(new Fragment_Home());
                    break;
                case R.id.nav_news:
                    replaceFragment(new Fragment_News());
                    break;
                case R.id.nav_buy:
                    replaceFragment(new Fragment_Book_Tickets());
                    break;
                case R.id.nav_schedule:
                    replaceFragment(new Fragment_Schedule());
                    break;
                case R.id.nav_account:
                    replaceFragment(new Fragment_Personal());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.LinearLayout, fragment);
        fragmentTransaction.commit();
    }
}