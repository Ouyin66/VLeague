package com.example.vleague.Menu;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vleague.Activity.Activity_Confirm_Ticket;
import com.example.vleague.Activity.Activity_Login;
import com.example.vleague.Activity.Details.Activity_Details_Match;
import com.example.vleague.DBHelper;
import com.example.vleague.Class.Matches;
import com.example.vleague.MyDatabase_Match;
import com.example.vleague.R;
import com.example.vleague.Adapter.Adapter_Ticket;
import com.example.vleague.UserSession;

import java.util.ArrayList;

public class Fragment_Book_Tickets extends Fragment {

    View rootView;
    ArrayList<Matches> matches;
    MyDatabase_Match myDatabase_match;
    ListView listTicket;
    EditText edtSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_book_tickets, container, false);

        myDatabase_match = new MyDatabase_Match(rootView.getContext());
        matches = new ArrayList<>();
        listTicket = (ListView) rootView.findViewById(R.id.listTicket);
        edtSearch = (EditText) rootView.findViewById(R.id.edtSearch);

        CapNhatDuLieu();
        selectTicket();

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

        return rootView;
    }

    public void selectTicket() {
        listTicket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("Range")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                if(UserSession.isLogged) {
                    Matches match = new Matches();

                    Cursor cursor = myDatabase_match.LayTranDau(id);

                    Log.i("DEBUG", String.valueOf(id));
                    if(cursor != null && cursor.getCount() > 0
                            && cursor.moveToFirst()) {

                        do {
                            int idMatches = cursor.getInt(cursor.getColumnIndex(DBHelper.ID_MATCH));

                            if(idMatches == id) {
                                match.setId(cursor.getInt(
                                        cursor.getColumnIndex(DBHelper.ID_MATCH)
                                ));
                                match.setLogo1(cursor.getString(
                                        cursor.getColumnIndex(DBHelper.LOGO_1)
                                ));
                                match.setLogo2(cursor.getString(
                                        cursor.getColumnIndex(DBHelper.LOGO_2)
                                ));
                                match.setName1(cursor.getString(
                                        cursor.getColumnIndex(DBHelper.NAME_1)
                                ));
                                match.setName2(cursor.getString(
                                        cursor.getColumnIndex(DBHelper.NAME_2)
                                ));
                                match.setDate(cursor.getString(
                                        cursor.getColumnIndex(DBHelper.DATE_MATCH)
                                ));
                                match.setTime(cursor.getString(
                                        cursor.getColumnIndex(DBHelper.TIME_MATCH)
                                ));
                                match.setRound(cursor.getInt(
                                        cursor.getColumnIndex(DBHelper.ROUND_MATCH)
                                ));
                                match.setPrice(cursor.getInt(
                                        cursor.getColumnIndex(DBHelper.PRICE_MATCH)
                                ));
                                match.setLocation(cursor.getString(
                                        cursor.getColumnIndex(DBHelper.LOCATION_MATCH)));

                                Intent intent = new Intent( rootView.getContext()
                                        , Activity_Confirm_Ticket.class);
                                Bundle bundle = new Bundle();

                                bundle.putLong("id", match.getId());
                                bundle.putString("logo1", match.getLogo1());
                                bundle.putString("logo2", match.getLogo2());
                                bundle.putString("name1", match.getName1());
                                bundle.putString("name2", match.getName2());
                                bundle.putString("date", match.getDate());
                                bundle.putString("time", match.getTime());
                                bundle.putString("location", match.getLocation());
                                bundle.putInt("price", match.getPrice());
                                bundle.putInt("round", match.getRound());

                                intent.putExtra("GoiMatches" ,bundle);
                                startActivity(intent);

                                break;
                            }
                        } while (cursor.moveToNext());
                    } else {
                        Log.i("DEBUG", "Hang ko co");
                    }
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(rootView.getContext());
                    dialog.setTitle("Thông báo");
                    dialog.setMessage("Bạn cần phải đăng nhập để có thể đặt vé!");

                    dialog.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(rootView.getContext()
                                    , Activity_Login.class);
                            startActivity(intent);
                        }
                    });
                    dialog.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    dialog.create();
                    dialog.show();
                }
            }
        });
    }

    @SuppressLint("Range")
    public void Search(String searchText) {
        if(matches == null) {
            matches = new ArrayList<Matches>();
        } else {
            matches.removeAll(matches);
        }

        String newText = searchText;

        Cursor cursor = myDatabase_match.searchMatches(newText);

        if(cursor != null) {
            while (cursor.moveToNext()) {
                Matches match = new Matches();

                match.setId(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.ID_MATCH)
                ));
                match.setLogo1(cursor.getString(
                        cursor.getColumnIndex(DBHelper.LOGO_1)
                ));
                match.setLogo2(cursor.getString(
                        cursor.getColumnIndex(DBHelper.LOGO_2)
                ));
                match.setName1(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NAME_1)
                ));
                match.setName2(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NAME_2)
                ));
                match.setDate(cursor.getString(
                        cursor.getColumnIndex(DBHelper.DATE_MATCH)
                ));
                match.setTime(cursor.getString(
                        cursor.getColumnIndex(DBHelper.TIME_MATCH)
                ));
                match.setRound(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.ROUND_MATCH)
                ));

                matches.add(match);
            }
        } else {
            Toast.makeText(rootView.getContext(), "Failed", Toast.LENGTH_SHORT).show();
        }

        setListView();
    }

    @SuppressLint("Range")
    public void CapNhatDuLieu() {
        if(matches == null) {
            matches = new ArrayList<Matches>();
        } else {
            matches.removeAll(matches);
        }

        Cursor cursor = myDatabase_match.LayTatCaDuLieu();

        if(cursor != null) {
            while (cursor.moveToNext()) {
                Matches match = new Matches();

                match.setId(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.ID_MATCH)
                ));
                match.setLogo1(cursor.getString(
                        cursor.getColumnIndex(DBHelper.LOGO_1)
                ));
                match.setLogo2(cursor.getString(
                        cursor.getColumnIndex(DBHelper.LOGO_2)
                ));
                match.setName1(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NAME_1)
                ));
                match.setName2(cursor.getString(
                        cursor.getColumnIndex(DBHelper.NAME_2)
                ));
                match.setDate(cursor.getString(
                        cursor.getColumnIndex(DBHelper.DATE_MATCH)
                ));
                match.setTime(cursor.getString(
                        cursor.getColumnIndex(DBHelper.TIME_MATCH)
                ));
                match.setRound(cursor.getInt(
                        cursor.getColumnIndex(DBHelper.ROUND_MATCH)
                ));

                matches.add(match);
            }
        } else {
            Toast.makeText(rootView.getContext(), "Failed", Toast.LENGTH_SHORT).show();
        }

        setListView();
    }

    public void setListView() {
        if(matches != null) {
            listTicket.setAdapter(
                    new Adapter_Ticket(matches)
            );
        }
    }
}