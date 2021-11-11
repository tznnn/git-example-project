package com.example.exampleproject;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.MyHolder> {
    Context context;
    ArrayList<ExampleItem> mExampleList;

    public ExampleAdapter(Context context, ArrayList<ExampleItem> mExampleList) {
        this.context = context;
        this.mExampleList = mExampleList;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleAdapter.MyHolder holder, int position) {

        String name = mExampleList.get(position).getName();
        String surname = mExampleList.get(position).getSurname();
        String date = mExampleList.get(position).getDate();
        String phoneNumber = mExampleList.get(position).getPhoneNumber();
        int genderId = mExampleList.get(position).getGenderId();
        String profileImage = mExampleList.get(position).getImageProfile();

        if (genderId == 0) {

            holder.relativeLayout.setBackgroundColor(Color.RED);
        } else {
            holder.relativeLayout.setBackgroundColor(Color.BLUE);
        }

        String nameSurname = name + " " + surname;

        holder.nameSurname.setText(nameSurname);
        Picasso.get().load(profileImage).into(holder.profileImg);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView nameSurname;
        ImageView profileImg;
        RelativeLayout relativeLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            nameSurname = itemView.findViewById(R.id.nameTxt);
            profileImg = itemView.findViewById(R.id.itemImage);
            relativeLayout = itemView.findViewById(R.id.rowItemLayout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}