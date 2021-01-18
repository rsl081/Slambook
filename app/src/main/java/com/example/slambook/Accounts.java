package com.example.slambook;

import java.util.ArrayList;

public class Accounts {
    private String username;
    private String password;
    private String name;

    public Accounts(String username, String password, String name){
        this.setUsername(username);
        this.setPassword(password);
        this.setName(name);
    }

    public void setUsername(String username){ this.username = username;}
    public void setPassword(String password){ this.password = password;}
    public void setName(String name){ this.name = name;}

    public String getUsername(){
        return  username;
    }
    public String getPassword(){
        return  password;
    }
    public String getName(){
        return  name;
    }
}
