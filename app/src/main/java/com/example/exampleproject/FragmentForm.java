package com.example.exampleproject;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FragmentForm extends Fragment implements DatePickerDialog.OnDateSetListener {


    private static final int RESULT_OK = -1;
    private static final int preqCode = 1;
    private static final int requestCode = 1;

    @BindView(R.id.editTextName)
    EditText editTextName;
    @BindView(R.id.editTextSurname)
    EditText editTextSurname;
    @BindView(R.id.editTextDate)
    EditText editTextDate;
    @BindView(R.id.imageViewCalendar)
    ImageView imgCalendar;
    @BindView(R.id.imageViewProfile)
    ImageView imgProfile;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.radioButtonFemale)
    RadioButton radioButtonFemale;
    @BindView(R.id.radioButtonMale)
    RadioButton radioButtonMale;
    @BindView(R.id.textViewProfile)
    TextView textViewProfile;
    @BindView(R.id.textViewGender)
    TextView textViewGender;
    @BindView(R.id.textViewPhoneNumber)
    TextView textViewPhoneNumber;
    @BindView(R.id.phoneNumber)
    PhoneNumber editTextPhoneNumber;
    @BindView(R.id.checkBoxVadesiz)
    CheckBox cbVadesiz;
    @BindView(R.id.checkBoxVadeli)
    CheckBox cbVadeli;
    @BindView(R.id.checkBoxYatirim)
    CheckBox cbYatirim;
    @BindView(R.id.checkBoxPortfoy)
    CheckBox cbPortfoy;
    @BindView(R.id.textViewAccountType)
    TextView accountTypeTxt;
    @BindView(R.id.textViewCheckBoxInvisible)
    TextView cbInvisibleTxt;
    @BindView(R.id.buttonOpenAccountFragment)
    Button btnOpenAccountFragment;
    @BindView(R.id.editTextAccountInfo)
    EditText accountInfoTxt;
    @BindView(R.id.textViewContract)
    TextView contractTxt;
    @BindView(R.id.checkboxContractAccept)
    CheckBox contractAcceptCheck;

    ExampleAdapter myAdapter;
    ArrayList<ExampleItem> modelList;

    public ArrayList<String> checkBoxResult;

    public boolean imgSelected;
    public int genderId;
    public Uri uri;
    String tmpProfileImageUri, branchName;
    int accountNo, branchNo, balance, contractAccept;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        ButterKnife.bind(this, view);
        editTextDate.setEnabled(false);
        checkBoxResult = new ArrayList<>();
        loadData();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedButtonId) {
                switch (checkedButtonId) {
                    case R.id.radioButtonFemale:
                        genderId = 0;
                        textViewGender.setTextColor(Color.BLACK);
                        break;
                    case R.id.radioButtonMale:
                        textViewGender.setTextColor(Color.BLACK);
                        genderId = 1;
                        break;
                }
            }
        });
        contractAccept();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (this.getArguments() != null) {

            Bundle bundle = this.getArguments();
            if (bundle.getInt("contractAccept") == 0) {

                branchName = bundle.getString("branchName");
                accountNo = bundle.getInt("accountNo");
                branchNo = bundle.getInt("branchNo");
                balance = bundle.getInt("balance");
                accountInfoTxt.setText(accountNo + " - " + branchNo + "  " + branchName + " / " + balance + " TL");
                contractAcceptCheck.setChecked(false);

            } else {
                contractAccept = bundle.getInt("contractAccept");
                contractAcceptCheck.setChecked(true);

            }

        }


    }

    @OnClick(R.id.checkboxContractAccept)
    public void contractClick() {
        contractAccept();
    }

    @OnClick(R.id.buttonSave)
    public void saveClick() {
        saveData();
    }

    @OnClick(R.id.buttonProfilePhoto)
    public void profilePhotoSelect() {
        textViewProfile.setTextColor(Color.BLACK);
        if (Build.VERSION.SDK_INT >= 22) {
            checkAndRequestForPermission();
        } else {
            openGallery();
        }
    }

    @OnClick(R.id.imageViewCalendar)
    public void openCalendar() {
        selectBirthday();
    }

    @OnClick(R.id.checkBoxVadeli)
    public void checkboxVadeliClick() {
        if (cbVadeli.isChecked()) {
            checkBoxResult.add(cbVadeli.getText().toString());
        } else {
            checkBoxResult.remove(cbVadeli.getText().toString());
        }
    }

    @OnClick(R.id.checkBoxVadesiz)
    public void checkboxVadesizClick() {
        if (cbVadesiz.isChecked()) {
            checkBoxResult.add(cbVadesiz.getText().toString());
            accountTypeTxt.setTextColor(Color.BLACK);
        } else {
            checkBoxResult.remove(cbVadesiz.getText().toString());
        }
    }

    @OnClick(R.id.checkBoxYatirim)
    public void checkBoxYatirimClick() {
        if (cbYatirim.isChecked()) {
            checkBoxResult.add(cbYatirim.getText().toString());
            accountTypeTxt.setTextColor(Color.BLACK);
        } else {
            checkBoxResult.remove(cbYatirim.getText().toString());
        }
    }

    @OnClick(R.id.checkBoxPortfoy)
    public void checkBoxPortfoyClick() {
        if (cbPortfoy.isChecked()) {
            checkBoxResult.add(cbPortfoy.getText().toString());
            accountTypeTxt.setTextColor(Color.BLACK);
        } else {
            checkBoxResult.remove(cbPortfoy.getText().toString());
        }
    }

    @OnClick(R.id.buttonOpenAccountFragment)
    public void openFragmentClick() {
        accountSelectFragment();
    }

    private void saveData() {
        String tmpName = editTextName.getText().toString();
        String tmpSurname = editTextSurname.getText().toString();
        String tmpDate = editTextDate.getText().toString();
        String tmpPhoneNumber = editTextPhoneNumber.getText().toString();
        int tmpPhoneNumberLength = editTextPhoneNumber.getText().length();
        int tmpGenderId = radioGroup.getCheckedRadioButtonId();
        checkBoxResults();
        String tmpCheckBoxResult = cbInvisibleTxt.getText().toString();
        String accountInfo = accountInfoTxt.getText().toString();
        int tmpContractState = contractAccept;

        if (!tmpName.isEmpty() && !tmpSurname.isEmpty() && !tmpDate.isEmpty() && imgSelected
                && tmpGenderId != -1 && tmpPhoneNumberLength == 17 && !tmpCheckBoxResult.isEmpty()
                && !accountInfo.isEmpty() && tmpContractState == 1) {

            textViewPhoneNumber.setTextColor(Color.BLACK);

            insertItem(tmpName, tmpSurname, tmpDate, tmpPhoneNumber, genderId, tmpProfileImageUri, tmpCheckBoxResult, tmpContractState);
            myAdapter.notifyItemInserted(modelList.size());
            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(modelList);
            editor.putString("customer list", json);
            editor.apply();

            getActivity().getFragmentManager().popBackStackImmediate("formFragment", 0);
            getActivity().getFragmentManager().popBackStack();

            Toast.makeText(getContext(), "Kayıt Başarılı", Toast.LENGTH_SHORT).show();

        } else if (tmpName.isEmpty()) {

            editTextName.setHintTextColor(Color.RED);
            editTextName.requestFocus();
            Toast.makeText(getContext(), "Lütfen Ad bölümünü doldurunuz", Toast.LENGTH_LONG).show();
        } else if (tmpSurname.isEmpty()) {

            editTextSurname.setHintTextColor(Color.RED);
            editTextSurname.requestFocus();
            Toast.makeText(getContext(), "Lütfen Soyad bölümünü doldurunuz", Toast.LENGTH_LONG).show();
        } else if (tmpDate.isEmpty()) {

            editTextDate.setHintTextColor(Color.RED);
            editTextDate.requestFocus();
            Toast.makeText(getContext(), "Lütfen doğum tarihinizi seçiniz", Toast.LENGTH_LONG).show();
        } else if (!imgSelected) {

            textViewProfile.setTextColor(Color.RED);
            textViewProfile.requestFocus();
            Toast.makeText(getContext(), "Lütfen profil fotoğrafı seçiniz", Toast.LENGTH_LONG).show();
        } else if (tmpGenderId == -1) {

            textViewGender.setTextColor(Color.RED);
            textViewGender.requestFocus();
            Toast.makeText(getContext(), "Lütfen cinsiyetinizi seçiniz", Toast.LENGTH_LONG).show();
        } else if (tmpPhoneNumberLength != 17) {

            textViewPhoneNumber.setTextColor(Color.RED);
            textViewPhoneNumber.requestFocus();
            Toast.makeText(getContext(), "Lütfen telefon numaranızı giriniz", Toast.LENGTH_LONG).show();
        } else if (tmpCheckBoxResult.isEmpty()) {

            accountTypeTxt.setTextColor(Color.RED);
            accountTypeTxt.requestFocus();
            Toast.makeText(getContext(), "Lütfen hesap tipini seçiniz", Toast.LENGTH_LONG).show();
        } else if (accountInfo.isEmpty()) {

            accountInfoTxt.setHintTextColor(Color.RED);
            accountInfoTxt.requestFocus();
            Toast.makeText(getContext(), "Lütfen bir hesap seçiniz", Toast.LENGTH_LONG).show();
        } else if (tmpContractState == 0) {

            contractTxt.setTextColor(Color.RED);
            contractTxt.requestFocus();
            Toast.makeText(getContext(), "Lütfen sözleşmeyi kabul ediniz", Toast.LENGTH_LONG).show();
        }

    }

    private void loadData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("customer list", null);
        Type type = new TypeToken<ArrayList<ExampleItem>>() {
        }.getType();
        modelList = gson.fromJson(json, type);

        if (modelList == null) {
            modelList = new ArrayList<>();
        }
    }

    private void insertItem(String name, String surname, String date, String phoneNumber, int genderId, String imageProfile, String checkboxAccountResult, int contractState) {
        myAdapter = new ExampleAdapter(getContext(), modelList);
        modelList.add(new ExampleItem(name, surname, date, phoneNumber, genderId, imageProfile, checkboxAccountResult, contractState));
    }

    public void selectBirthday() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        int i3 = i1 + 1;
        String date = i2 + "." + i3 + "." + i;
        editTextDate.setText(date);
    }

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(getContext(), "Lütfen uygulama ayarlarından gerekli izinleri açınız", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, preqCode);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, preqCode);
            }
        } else
            openGallery();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == RESULT_OK && data != null) {

            uri = data.getData();
            imgProfile.setImageURI(uri);
            tmpProfileImageUri = uri.toString();
            imgSelected = true;
        }
    }

    public void checkBoxResults() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : checkBoxResult) {
            stringBuilder.append(s).append(" ");
            cbInvisibleTxt.setText(stringBuilder.toString());
        }
    }

    public void accountSelectFragment() {
        FragmentCustomAccountSelection nextFrag = new FragmentCustomAccountSelection();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, nextFrag, null)
                .addToBackStack(null)
                .commit();
    }

    public void contractAccept() {
        String contract = "Sözleşmeyi kabul etmek için tıklayınız";
        SpannableString spannableString = new SpannableString(contract);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                FragmentContract nextFrag = new FragmentContract();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, nextFrag, null)
                        .addToBackStack(null)
                        .commit();
            }
        };
        spannableString.setSpan(clickableSpan, 0, 38, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        contractTxt.setText(spannableString);
        contractTxt.setMovementMethod(LinkMovementMethod.getInstance());
    }

}