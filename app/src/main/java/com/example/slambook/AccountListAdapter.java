package com.example.slambook;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHolder> {

    private ArrayList<Accounts> accounts;
    private Context mContext;
    private PersonListAdapter.OnClickListener listener;

    /**
     * Holds variables in a View
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPic;
        TextView accountName;
        Button deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewPic = (ImageView) itemView.findViewById(R.id.user_profile_pic);
            this.accountName = (TextView) itemView.findViewById(R.id.indi_account_name);
            this.deleteBtn = (Button) itemView.findViewById(R.id.logout_user);
            this.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.OnClickListener(position);
                    }
                }
            });
        }
    }

    public AccountListAdapter(Context context, ArrayList<Accounts> objects) {
        this.mContext = context;
        this.accounts = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_user_2, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Accounts accounts = this.accounts.get(position);

        if (accounts.getBitmapImageProfile() == null) {
            holder.imageViewPic.setImageResource(accounts.getIntImageProfile());
        } else {
            holder.imageViewPic.setImageBitmap(accounts.getBitmapImageProfile());
        }

        holder.accountName.setText(accounts.getAccountName());
    }

    @Override
    public int getItemCount() {
        return this.accounts.size();
    }

    public void setOnClickListener(PersonListAdapter.OnClickListener listener) {
        this.listener = listener;
    }

}
