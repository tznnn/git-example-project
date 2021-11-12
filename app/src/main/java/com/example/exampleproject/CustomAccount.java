package com.example.exampleproject;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Adapter;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAccount extends LinearLayout {

    RecyclerView rcvAccount;
    CustomAccountAdapter customAccountAdapter;
    ArrayList<CustomAccountModel> accountList;

    String infoText = "";

    public CustomAccount(Context context) {
        super(context,null);
    }

    public CustomAccount(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CustomAccount(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public CustomAccount(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_custom_account, this);
        rcvAccount = findViewById(R.id.recyclerViewAccount);
        accountList = new ArrayList<>();

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomAccount);
            infoText = a.getString(R.styleable.CustomAccount_infoText);
            a.recycle();
        }

        rcvAccount.setLayoutManager(new LinearLayoutManager(getContext()));

        //RowModel rowModel = new RowModel("Atakan Tüzün", "Sa As hahaa", R.drawable.ic_launcher_background);
        accountList.add(new CustomAccountModel(1234567,"Vadeli Hesap",1232,"SAHRAYICEDİT ŞB./İSTANBUL",1560));
        accountList.add(new CustomAccountModel(2736583,"Vadeli Hesap",9083,"GAZİ ÜNİVERSİTESİ ŞB./ANKARA",980));
        accountList.add(new CustomAccountModel(7878484,"Vadesiz Hesap",1627,"KIZILAY/ANKARA",0));
        accountList.add(new CustomAccountModel(7145629,"Portföy Hesap",2325,"ALTUNİZADE ŞB./İSTANBUL",2030));
        accountList.add(new CustomAccountModel(3432343,"Vadeli Hesap",2637,"TEST ŞB./İSTANBUL",455));
        accountList.add(new CustomAccountModel(3434434,"Vadesiz Hesap",5745,"MAMAK ŞB./ANKARA",9859));
        accountList.add(new CustomAccountModel(5656567,"Portföy Hesap",3456,"NEREDE ŞB./BURADA",8999));
        accountList.add(new CustomAccountModel(3145607,"Yatırım Hesabı",4565,"HAHAHAH ŞB./ANTALYA",333));


        customAccountAdapter = new CustomAccountAdapter(getContext(), accountList);
        rcvAccount.setAdapter(customAccountAdapter);

    }
}
