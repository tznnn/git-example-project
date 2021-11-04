package com.example.exampleproject;

import android.content.Context;
import android.content.ReceiverCallNotAllowedException;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FragmentForm extends Fragment {


    @BindView(R.id.buttonSave)
    Button btnSave;
    @BindView(R.id.editTextName)
    EditText editTextName;
    @BindView(R.id.editTextSurname)
    EditText editTextSurname;

    ExampleAdapter myAdapter;
    ArrayList<ExampleItem> modelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        ButterKnife.bind(this, view);
        loadData();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        return view;

    }

    private void saveData() {
        String tmpName = editTextName.getText().toString();
        String tmpSurname = editTextSurname.getText().toString();


        if (!tmpName.isEmpty() && !tmpSurname.isEmpty()) {

            insertItem(tmpName, tmpSurname);
            myAdapter.notifyItemInserted(modelList.size());
            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(modelList);
            editor.putString("task list", json);
            editor.apply();

        } else if (tmpName.isEmpty()) {

            editTextName.setHintTextColor(Color.RED);
            Toast.makeText(getContext(), "Fill in the name fields", Toast.LENGTH_LONG).show();

        } else if (tmpSurname.isEmpty()) {

            editTextSurname.setHintTextColor(Color.RED);
            Toast.makeText(getContext(), "Fill in the surname fields", Toast.LENGTH_LONG).show();

        }

    }

    private void loadData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
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
}