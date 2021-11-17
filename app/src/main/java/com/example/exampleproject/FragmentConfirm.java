package com.example.exampleproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentConfirm extends Fragment {

    @BindView(R.id.textViewName)
    TextView txtName;
    @BindView(R.id.textViewSurname)
    TextView txtsurname;
    @BindView(R.id.textViewBirthday)
    TextView txtBirthday;
    @BindView(R.id.textViewAccountInfo)
    TextView txtAccountInfo;
    @BindView(R.id.textViewGender)
    TextView txtGender;
    @BindView(R.id.textViewPhoneNumber)
    TextView txtPhoneNumber;
    @BindView(R.id.textViewAccountType)
    TextView txtAccountType;
    @BindView(R.id.imageViewProfilePhoto)
    ImageView imageProfile;

    ExampleAdapter myAdapter;
    ArrayList<ExampleItem> modelList;
    String name;
    String surname;
    String birthday;
    String accountInfo;
    String phoneNumber;
    String accountType;
    String imagePhoto;
    int contractState;
    int genderId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);
        ButterKnife.bind(this, view);
        loadData();
        if (this.getArguments() != null) {
            Bundle bundle = this.getArguments();

            name = bundle.getString("name");
            surname = bundle.getString("surname");
            birthday = bundle.getString("birthday");
            accountInfo = bundle.getString("accountInfo");
            phoneNumber = bundle.getString("phoneNumber");
            accountType = bundle.getString("accountType");
            contractState = bundle.getInt("contractState");

            genderId = bundle.getInt("gender");
            if (genderId == 0) {
                txtGender.setText("Kadın");
            } else if (genderId == 1) {
                txtGender.setText("Erkek");
            }

            imagePhoto = bundle.getString("profileImage");
            byte[] decodedString = Base64.decode(imagePhoto, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageProfile.setImageBitmap(decodedByte);


            txtName.setText(name);
            txtsurname.setText(surname);
            txtBirthday.setText(birthday);
            txtAccountInfo.setText(accountInfo);
            txtPhoneNumber.setText(phoneNumber);
            txtAccountType.setText(accountType);


        }
        return view;
    }

    public void saveData() {
        insertItem(name, surname, birthday, phoneNumber, genderId, imagePhoto, accountType, accountInfo, contractState);
        myAdapter.notifyItemInserted(modelList.size());

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(modelList);
        editor.putString("customer list", json);
        editor.apply();

        FragmentResult fragmentResult = new FragmentResult();
        Bundle bundle = new Bundle();
        bundle.putString("nameSurname", name + " " + surname);
        fragmentResult.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragmentResult, null)
                .addToBackStack(null)
                .commit();
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

    private void insertItem(String name, String surname, String date, String phoneNumber, int genderId, String imageProfile, String checkboxAccountResult, String accountInfo, int contractState) {
        myAdapter = new ExampleAdapter(getContext(), modelList);
        modelList.add(new ExampleItem(name, surname, date, phoneNumber, genderId, imageProfile, checkboxAccountResult, accountInfo, contractState));
    }

    @OnClick(R.id.buttonConfirm)
    public void confirmCLick() {
        saveData();
    }

    @OnClick(R.id.buttonBack)
    public void backClick() {
        Bundle bundle =new Bundle();
        bundle.putInt("back",1);

        FragmentForm fragmentForm = new FragmentForm();
        fragmentForm.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragmentForm, null)
                .commit();
    }
}