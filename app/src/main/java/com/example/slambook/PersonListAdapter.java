package com.example.slambook;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PersonListAdapter extends ArrayAdapter<Person> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;
    private OnClickListener listener;
    private OnClickListener listener2;


    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        ImageView profilePic;
        TextView name;
        TextView remark;
        Button deleteBtn;
        Button editBtn;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public PersonListAdapter(Context context, int resource, ArrayList<Person> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        int profilePic = getItem(position).getProfilePic();
        String name = getItem(position).getAccountName();
        String remark = getItem(position).getRemark();
        String birthday = getItem(position).getBirthday();
        String gender = getItem(position).getGender();
        String address = getItem(position).getAddress();
        String contact = getItem(position).getContact();
        String hobbies = getItem(position).getHobbies();
        String goals = getItem(position).getGoals();

        //Create the person object with the information
        Person person = new Person(profilePic,name,remark,birthday,gender,address,contact,hobbies, goals);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.profilePic = (ImageView) convertView.findViewById(R.id.profile_person);
            holder.name = (TextView) convertView.findViewById(R.id.indiviName);
            holder.remark = (TextView) convertView.findViewById(R.id.indiviRemark);
            holder.deleteBtn = (Button) convertView.findViewById(R.id.deleteBtn);
            holder.editBtn = (Button) convertView.findViewById(R.id.editBtn);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.profilePic.setImageResource(person.getProfilePic());
        holder.name.setText(person.getAccountName());
        holder.remark.setText(person.getRemark());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.OnClickListener(position);
                }
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener2 != null){
                    listener2.OnClickListener(position);
                }
            }
        });

        return convertView;
    }


    public interface OnClickListener {
        public void OnClickListener(int position);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }
    public void setOnClickListener2(OnClickListener listener2) {
        this.listener2 = listener2;
    }



}
