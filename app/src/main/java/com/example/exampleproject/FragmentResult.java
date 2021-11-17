package com.example.exampleproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FragmentResult extends Fragment {

    @BindView(R.id.textViewResult)
    TextView txtResult;


    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = this.getArguments();
        String name = bundle.getString("nameSurname") + " Kişisinin Kayıt işlemi Gerçekleştirilmiştir";
        txtResult.setText(name);
        Log.i("TAG", "onCreateView: " + name);
        return view;
    }

    @OnClick(R.id.buttonBackHome)
    public void backHome() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentHome()).commit();

    }
}