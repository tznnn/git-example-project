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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FragmentForm extends Fragment implements DatePickerDialog.OnDateSetListener {


    private static final int RESULT_OK = -1;
    private static final int preqCode = 1;
    private static final int requestCode = 1;

    @BindView(R.id.buttonSave)
    Button btnSave;
    @BindView(R.id.buttonProfilePhoto)
    Button btnSelectPhoto;
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

    ExampleAdapter myAdapter;
    ArrayList<ExampleItem> modelList;
    public boolean imgSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        ButterKnife.bind(this, view);
        editTextDate.setEnabled(false);
        loadData();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBirthday();
            }
        });

        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermission();
                } else {
                    openGallery();
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedButtonId) {
                switch (checkedButtonId) {
                    case R.id.radioButtonFemale:
                        Toast.makeText(getContext(), "Kadın seçildi" + radioButtonFemale.getId() + radioButtonMale.getText(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButtonMale:
                        Toast.makeText(getContext(), "Erkek seçildi" + radioButtonMale.getId() + radioButtonMale.getText(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        return view;
    }

    private void saveData() {
        String tmpName = editTextName.getText().toString();
        String tmpSurname = editTextSurname.getText().toString();
        String tmpDate = editTextDate.getText().toString();

        if (!tmpName.isEmpty() && !tmpSurname.isEmpty() && !tmpDate.isEmpty() && imgSelected && radioGroup.getCheckedRadioButtonId() != -1) {

            insertItem(tmpName, tmpSurname);
            myAdapter.notifyItemInserted(modelList.size());
            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(modelList);
            editor.putString("customer list", json);
            editor.apply();
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

            Toast.makeText(getContext(), "Lütfen profil fotoğrafı seçiniz", Toast.LENGTH_LONG).show();
        } else if (radioGroup.getCheckedRadioButtonId() == -1) {

            Toast.makeText(getContext(), "Lütfen cinsiyetinizi seçiniz", Toast.LENGTH_LONG).show();
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

    private void insertItem(String name, String surname) {
        myAdapter = new ExampleAdapter(getContext(), modelList);
        modelList.add(new ExampleItem(name, surname));
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
            Uri uri = data.getData();
            imgProfile.setImageURI(uri);
            imgSelected = true;

        }

    }

}