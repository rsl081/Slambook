package com.example.slambook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.slambook.Accounts;
import com.example.slambook.MainActivity;
import com.example.slambook.R;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameEdit;
    private EditText passwordEdit;
    private Button loginBtn;
    private Button registerBtn;
    private Toast popUp;
    private String name, pass;
    Accounts accounts;
    private ArrayList<Accounts> accountsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        init();
    }

    private void init() {
        accountsArrayList.add(new Accounts(
                "Anna",
                "13579abcdeA",
                "Anna Lisa"));
        accountsArrayList.add(new Accounts(
                "Lorna",
                "Th3Q41ckBr0wnF0x",
                "Lorna Dee"));
        accountsArrayList.add(new Accounts(
                "_Fe_",
                "p@zzW0rd",
                "Fe Rari"));

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

}
