package com.example.exampleproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentFormDetail extends Fragment {

    ExampleAdapter myAdapter;
    ArrayList<ExampleItem> modelList;
    @BindView(R.id.recyclerview)
    RecyclerView rcv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form_detail, container, false);
        ButterKnife.bind(this, view);
        loadData();
        buildRecyclerView();
        return view;


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

    private void buildRecyclerView() {
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter = new ExampleAdapter(getContext(), modelList);
        rcv.setAdapter(myAdapter);
    }
}