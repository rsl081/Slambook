package com.example.slambook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DiaryLoginActivity extends Login {

    EditText userDiaryEdt;
    EditText passDiaryEdt;
    Button loginDiaryBtn;

//    private String nameDiary;
//    private String passDiary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_login);


        userDiaryEdt = findViewById(R.id.diary_edt_username);
        passDiaryEdt = findViewById(R.id.diary_edt_pass);
        //Button Of Diary
        loginDiaryBtn = findViewById(R.id.id_DiaryLoginButton);
        //Listeners Btn
        loginDiaryBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        name = userDiaryEdt.getText().toString().trim();
        pass = passDiaryEdt.getText().toString().trim();
        switch (v.getId()) {
            case R.id.id_DiaryLoginButton:
                showToast();
                break;
        }
    }

    @Override
    public void showToast() {
        if (name.matches("") || pass.matches("")) {
            if (name.matches("")) {
                Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
                userDiaryEdt.setHint("Put your username here!");
            }

            if (pass.matches("")) {
                Toast.makeText(this, "You did not enter a password", Toast.LENGTH_SHORT).show();
                passDiaryEdt.setHint("Put your password here!");
            }
        } else {
            if (accountDb.CheckUser(name,pass)) {
                CustomToast();
                myIntent = new Intent(this, DiaryListActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Intent();
                startActivity(myIntent);
                finish();
            } else {
                AlertDialag();
            }
        }
    }//end of showToast CURLY BRACES

    /*public void Intent(){
        super.Intent();
    }*/

    @Override
    public void Intent(){
        myIntent.putExtra("username", name);
        myIntent.putExtra("password", pass);
        long sendUsername = accountDb.Sender(name);
        myIntent.putExtra(DiaryListActivity.EXTRA_ADDED_ACCOUNT, (Parcelable) accountDb.getAccountId(sendUsername));
        myIntent.putExtra(DiaryListActivity.EXTRA_ADDED_DIARY, sendUsername);
    }

}