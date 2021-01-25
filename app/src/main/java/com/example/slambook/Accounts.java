package com.example.slambook;

import android.content.Intent;
import android.os.Parcel;

import java.util.ArrayList;

public class Accounts {
    private String username;
    private String password;
    private String accountName;
    Person[] person;

    public Accounts(String username, String password, String accountName, Person[] person){
        this.setUsername(username);
        this.setPassword(password);
        this.setAccountName(accountName);
        this.person = person;
    }

    public Accounts(String username, String password, String accountName){
        this.setUsername(username);
        this.setPassword(password);
        this.setAccountName(accountName);
    }

    public void setUsername(String username){ this.username = username;}
    public void setPassword(String password){ this.password = password;}
    public void setAccountName(String accountName){ this.accountName = accountName;}

    public String getUsername(){
        return  username;
    }
    public String getPassword(){
        return  password;
    }
    public String getAccountName(){
        return accountName;
    }
}
