package com.example.slambook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {

    Button slambookBtn;
    Button diaryBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        Init();
    }

    void Init()
    {
        slambookBtn = findViewById(R.id.id_SlambookButton);
        diaryBtn = findViewById(R.id.id_DiaryButton);

        //Listeners
        slambookBtn.setOnClickListener(this);
        diaryBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_SlambookButton:
                SlambookPage();
            break;
            case R.id.id_DiaryButton:
                DiaryPage();
            break;
        }
    }

    void SlambookPage()
    {
        Intent loginIntent = new Intent(SelectionActivity.this, Login.class);
        startActivity(loginIntent);
        Toast.makeText(this, "Going to Slambook Page", Toast.LENGTH_SHORT).show();
    }

    void DiaryPage()
    {
        Intent loginIntent = new Intent(SelectionActivity.this, DiaryLoginActivity.class);
        startActivity(loginIntent);
        Toast.makeText(this, "Going to Diary Page", Toast.LENGTH_SHORT).show();
    }
}