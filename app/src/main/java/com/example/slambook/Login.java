
package com.example.slambook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText usernameEdit;
    private EditText passwordEdit;
    private Button loginBtn;
    private Button registerBtn;
    private Toast popUp;
    private String name, pass;
    Accounts[] accountsArray = new Accounts[3];
    private ArrayList<Accounts> accountsArrayList = new ArrayList<>();
    Intent myIntent;
    Accounts accounts = new Accounts("", R.drawable.woman);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
    }

    private void init() {

        //Anna
    accountsArray[0] = new Accounts(
    "Anna",
    "123",
//                "13579abcdeA",
    "Anna Lisa", new Person[]{
            new Person(R.drawable.woman,"Bino Santos", "BestFriend", "March 12 2020",
            "Lesbian", "Baliwag Bulacan", "09323216432", "Eating", "Cars")});
    accountsArrayList.add(accountsArray[0]);

    //Lorna
    accountsArray[1] = new Accounts(
            "Lorna",
            "Th3Q41ckBr0wnF0x",
            "Lorna Dee", new Person[]{
            new Person(R.drawable.woman, "Lorna Santos", "BestFriend", "March 12 2020",
                    "Female", "Baliwag Bulacan", "09323216432", "Eating", "Cars"),
            new Person(R.drawable.woman, "Russel Pogi", "Handsome", "March 12 2020",
                    "Female", "Baliwag Bulacan", "09323216432", "Eating", "Cars")});
    accountsArrayList.add(accountsArray[1]);

    //_Fe_
    accountsArray[2] = new Accounts(
            "_Fe_",
            "p@zzW0rd",
            "Fe Rari", new Person[]{
            new Person(R.drawable.woman, "Fe Santos", "BestFriend", "March 12 2020",
                    "Female", "Baliwag Bulacan", "09323216432", "Eating", "Cars"),
            new Person(R.drawable.woman, "Russel Pogi", "Handsome", "March 12 2020",
                    "Female", "Baliwag Bulacan", "09323216432", "Eating", "Cars"),
            new Person(R.drawable.woman, "Gg Kayo Pogi", "Handsome", "March 12 2020",
                    "Female", "Baliwag Bulacan", "09323216432", "Eating", "Cars")});
    accountsArrayList.add(accountsArray[2]);
        //Button
        loginBtn = findViewById(R.id.id_LoginButton);
        registerBtn = findViewById(R.id.id_RegisterButton);

        //EditText
        usernameEdit = findViewById(R.id.edt_username);
        passwordEdit = findViewById(R.id.edt_pass);

        //Listener
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        name = usernameEdit.getText().toString().trim();
        pass = passwordEdit.getText().toString().trim();
        switch (v.getId()) {
            case R.id.id_LoginButton:
                showToast();
                break;
            case R.id.id_RegisterButton:
                showRegistration();
                break;
        }
    }

    public void showToast() {
        if (name.matches("") || pass.matches("")) {
            if (name.matches("")) {
                Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
                usernameEdit.setHint("Put your username here!");
            }

            if (pass.matches("")) {
                Toast.makeText(this, "You did not enter a password", Toast.LENGTH_SHORT).show();
                passwordEdit.setHint("Put your password here!");
            }
        } else {
            if (CredentialVerification()) {
                CustomToast();
                myIntent = new Intent(this, HomeActivity.class);
                Intent();
                this.startActivity(myIntent);
            } else {
                AlertDialag();
            }
        }
    }//end of showToast CURLY BRACES

    public void CustomToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));
        popUp = new Toast(getApplicationContext());
        popUp.setGravity(Gravity.BOTTOM, 0, 50);
        popUp.setDuration(Toast.LENGTH_LONG);
        popUp.setView(layout);
        popUp.show();
    }//end of CustomToast CURLY BRACES

    public void AlertDialag() {
        AlertDialog.Builder bldg = new AlertDialog.Builder(this);
        bldg.setTitle("Credential does not match");
        bldg.setMessage("Username or Password is Incorrect!");
        bldg.setPositiveButton("Okay!", null);
        bldg.show();
    }//end of AlertDialag CURLY BRACES

    public void showRegistration() {
        AlertDialog.Builder bldg = new AlertDialog.Builder(this);
        bldg.setTitle("Does not have an Account yet?");
        bldg.setMessage("Do you want to go to Registration Form");
        bldg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Login.this, "Going to Registration Form:", Toast.LENGTH_LONG).show();
                Intent loginIntent = new Intent(Login.this, Registration.class);
                startActivity(loginIntent);
            }
        });
        bldg.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Login.this, "Cancelled so sad!", Toast.LENGTH_LONG).show();
            }
        });
        bldg.show();
    }//end of showRegistration CURLY BRACES

    boolean CredentialVerification() {
        for(int i = 0; i < accountsArrayList.size(); i++){
            if (accountsArrayList.get(i).getUsername().equals(name) && accountsArrayList.get(i).getPassword().equals(pass)) {
                return true;
            }
        }
        return false;
    }//end of CredentialVerification CURLY BRACES


    public void Intent(){

        myIntent.putExtra("username", name);
        myIntent.putExtra("password", pass);

        if(name.equals("Anna") && pass.equals("123")){
            accounts.setAccountName(accountsArray[0].getAccountName());
            myIntent.putExtra("anna", accounts);
            myIntent.putExtra("anna_1", accountsArray[0].person[0]);
        }else if(name.equals("Lorna") && pass.equals("Th3Q41ckBr0wnF0x")){
            accounts.setAccountName(accountsArray[1].getAccountName());
            myIntent.putExtra("lorna", accounts);
            myIntent.putExtra("lorna_1", accountsArray[1].person[0]);
            myIntent.putExtra("lorna_2", accountsArray[1].person[1]);
        }else if(name.equals("_Fe_") && pass.equals("p@zzW0rd")){
            accounts.setAccountName(accountsArray[2].getAccountName());
            myIntent.putExtra("fe", accounts);
            myIntent.putExtra("fe_1", accountsArray[2].person[0]);
            myIntent.putExtra("fe_2", accountsArray[2].person[1]);
            myIntent.putExtra("fe_3", accountsArray[2].person[2]);
        }







    }


}
