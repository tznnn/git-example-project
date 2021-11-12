package com.example.exampleproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAccountAdapter extends RecyclerView.Adapter<CustomAccountAdapter.MyHolder> {
    Context context;
    ArrayList<CustomAccountModel> accountList;

    public CustomAccountAdapter(Context context, ArrayList<CustomAccountModel> accountList) {
        this.context = context;
        this.accountList = accountList;
    }


    @NonNull
    @Override
    public CustomAccountAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_account_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAccountAdapter.MyHolder holder, int position) {
        String accountType = accountList.get(position).getAccountType();
        int accountNo = accountList.get(position).getAccountNo();
        int branchNo = accountList.get(position).getBranchNo();
        String branchName = accountList.get(position).getBranchName();
        int balance = accountList.get(position).getAccountBalance();

        holder.accountType.setText(accountType);
        holder.accountNo.setText(String.valueOf(accountNo));
        holder.branchNo.setText(String.valueOf(branchNo));
        holder.branchName.setText(branchName);
        holder.accountBalance.setText(String.valueOf(balance));
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView accountType, accountNo, branchNo, branchName, accountBalance;

        public MyHolder(@NonNull View itemView) {

            super(itemView);
            accountType = itemView.findViewById(R.id.textViewAccountType);
            accountNo = itemView.findViewById(R.id.textViewAccountNo);
            branchNo = itemView.findViewById(R.id.textViewBrancNo);
            branchName = itemView.findViewById(R.id.textViewBrancName);
            accountBalance = itemView.findViewById(R.id.textViewBalance);

        }
    }
}
