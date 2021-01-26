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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class AccountListAdapter extends ArrayAdapter<String> {

    private static final String TAG = "AccountListAdapter";

    private Context mContext;
    private int mResource;
    private PersonListAdapter.OnClickListener listener;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView accountName;
        Button deleteBtn;
    }

    /**
     * Default constructor for the AccountListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public AccountListAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the account information
        String username = getItem(position);

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.accountName = (TextView) convertView.findViewById(R.id.indi_account_name);
            holder.deleteBtn = (Button) convertView.findViewById(R.id.logout_user);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.accountName.setText(username);
        //Listener
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.OnClickListener(position);
                }
            }
        });

        return convertView;
    }

    public void setOnClickListener(PersonListAdapter.OnClickListener listener) {
        this.listener = listener;
    }


}
