package com.example.slambook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_entry_list);
        Init();
    }

    private void Init (){
        Intent intent = getIntent();

        Person person = intent.getParcelableExtra("ViewList");

        int profilePic = person.getProfilePic();
        String name = person.getAccountName();
        String remark = person.getRemark();
        String birthday = person.getBirthday();
        String gender = person.getGender();
        String address = person.getAddress();
        String contact = person.getContact();
        String hobbies = person.getHobbies();
        String goals = person.getGoals();

        ImageView profilePicImageView = findViewById(R.id.profile_pic);
        TextView textViewName = findViewById(R.id.txt_name);
        TextView textViewRemark = findViewById(R.id.txt_remark);
        TextView textViewBday = findViewById(R.id.txt_bday);
        TextView textViewGender = findViewById(R.id.txt_gender);
        TextView textViewAddress = findViewById(R.id.txt_address);
        TextView textViewContact = findViewById(R.id.txt_contact);
        TextView textViewHobbies = findViewById(R.id.txt_hobbies);
        TextView textViewGoals = findViewById(R.id.txt_goals);

        profilePicImageView.setImageResource(profilePic);
        textViewName.setText(name);
        textViewRemark.setText(remark);
        textViewBday.setText(birthday);
        textViewGender.setText(gender);
        textViewAddress.setText(address);
        textViewContact.setText(contact);
        textViewHobbies.setText(hobbies);
        textViewGoals.setText(goals);

        Button btnUpdate = findViewById(R.id.btn_addEntry);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
