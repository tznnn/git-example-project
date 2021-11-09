package com.example.exampleproject;

import android.content.Context;
import android.content.res.TypedArray;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.Locale;

public class PhoneNumber extends LinearLayout {

    EditText editTextPhone;

    String phoneNumberHint = "";

    private boolean backspacingFlag = false;
    private boolean editedFlag = false;
    private int cursorComplement;

    public PhoneNumber(Context context) {
        super(context);
        init(context, null);
    }

    public PhoneNumber(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PhoneNumber(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public PhoneNumber(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_phone_number, this);
        editTextPhone = findViewById(R.id.editTextPhone);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PhoneNumber);
            phoneNumberHint = a.getString(R.styleable.PhoneNumber_editTextHint);
            a.recycle();
        }
        editTextPhone.setHint(phoneNumberHint);

        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                cursorComplement = s.length() - editTextPhone.getSelectionStart();
                if (count > after) {
                    backspacingFlag = true;
                } else {
                    backspacingFlag = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int cursorPosition, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                String phone = string.replaceAll("[^\\d]", "");

                if (!editedFlag) {

                    if (phone.length() >= 9 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = phone.substring(0, 1) + " (" + phone.substring(1, 4) + ") " + phone.substring(4, 7) + " " + phone.substring(7, 9) + " " + phone.substring(9);
                        editTextPhone.setText(ans);
                        editTextPhone.setSelection(editTextPhone.getText().length() - cursorComplement);

                    } else if (phone.length() >= 4 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = phone.substring(0, 1) + " (" + phone.substring(1, 4) + ") " + phone.substring(4);
                        editTextPhone.setText(ans);
                        editTextPhone.setSelection(editTextPhone.getText().length() - cursorComplement);
                    }
                } else {
                    editedFlag = false;
                }
            }
        });


    }


}