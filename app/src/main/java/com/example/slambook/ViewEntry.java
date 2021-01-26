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
        Intent intent = getIntent();
        int positionOfPeopleList = intent.getIntExtra("position", 0);

        Person person = intent.getParcelableExtra("ViewList");

        int profilePic = person.getProfilePic();
        String name = person.getAccountName();

        ImageView profilePicImageView = findViewById(R.id.profile_pic);
        profilePicImageView.setImageResource(profilePic);

        TextView textView = findViewById(R.id.edt_name);
        textView.setText(name);

        Button btnUpdate = findViewById(R.id.btn_addEntry);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qwe = new Intent();
                qwe.putExtra("editTextValue", positionOfPeopleList);
                setResult(RESULT_OK, qwe);
                finish();
            }
        });

    }

}
