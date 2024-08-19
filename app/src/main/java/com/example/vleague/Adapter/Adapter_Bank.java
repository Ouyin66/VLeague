package com.example.vleague.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.vleague.Class.Bank;
import com.example.vleague.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Bank extends BaseAdapter {

    ArrayList<Bank> arrayList  = new ArrayList<>();

    public Adapter_Bank(ArrayList<Bank> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return arrayList.get(i).getId();
    }

    public String formatPrice(int price) {
        String priceInput = String.valueOf(price);
        double priceValue = Double.parseDouble(priceInput);
        DecimalFormat decimalFormat = new DecimalFormat("###,### VND");
        String priceOutput = decimalFormat.format(priceValue);

        return priceOutput;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = View.inflate(viewGroup.getContext(), R.layout.layout_listview_bank, null);

        TextView tvBankName = (TextView) rootView.findViewById(R.id.tvNameBank);
        TextView tvBankNum = (TextView) rootView.findViewById(R.id.tvNumBank);
        TextView tvMoney = (TextView) rootView.findViewById(R.id.tvMoney);
        ImageView imgBank = (ImageView) rootView.findViewById(R.id.imgBank);
        CardView cardView = (CardView) rootView.findViewById(R.id.cvBank);

        Bank bank = (Bank) getItem(i);
        if (bank.getNameBank().equals("Sacombank") )
        {
            imgBank.setImageDrawable(ContextCompat.getDrawable
                    (rootView.getContext(), R.drawable.logo_sacombank_new));
            cardView.setCardBackgroundColor(ContextCompat.getColor
                    (rootView.getContext(), R.color.Sacombank));
        }
        tvBankName.setText(bank.getNameBank());
        tvBankNum.setText(bank.getBankNum());
        tvMoney.setText(formatPrice(bank.getBankMoney()));

        return rootView;
    }
}
