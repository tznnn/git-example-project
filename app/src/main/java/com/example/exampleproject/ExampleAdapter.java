package com.example.exampleproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        String name = mExampleList.get(position).getmLine1();
        String messages = mExampleList.get(position).getmLine2();

        holder.name.setText(name);
        holder.message.setText(messages);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView name, message;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textview_line1);
            message = itemView.findViewById(R.id.textview_line_2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}