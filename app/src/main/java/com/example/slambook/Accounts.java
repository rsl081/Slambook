package com.example.slambook;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Accounts implements Parcelable {
    private Bitmap bitmapImageProfile;
    private int intImageProfile;
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

    public Accounts(String accountName, int intImageProfile){
        this.accountName = accountName;
        this.intImageProfile = intImageProfile;
    }

    public Accounts(String accountName, Bitmap profilePic){
        this.accountName = accountName;
        this.bitmapImageProfile = profilePic;
    }


    public void setBitmapImageProfile(Bitmap bitmapImageProfile) { this.bitmapImageProfile = bitmapImageProfile;}
    public void setIntImageProfile(int intImageProfile) {
        this.intImageProfile = intImageProfile;
    }
    public void setUsername(String username){ this.username = username;}
    public void setPassword(String password){ this.password = password;}
    public void setAccountName(String accountName){ this.accountName = accountName;}

    public Bitmap getBitmapImageProfile() { return bitmapImageProfile; }

    public int getIntImageProfile() {
        return intImageProfile;
    }
    public String getUsername(){
        return  username;
    }
    public String getPassword(){
        return  password;
    }
    public String getAccountName(){
        return accountName;
    }

    protected Accounts(Parcel in) {
        this.bitmapImageProfile = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        this.intImageProfile = in.readInt();
        this.username = in.readString();
        this.password = in.readString();
        this.accountName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(bitmapImageProfile);
        dest.writeInt(intImageProfile);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(accountName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Accounts> CREATOR = new Parcelable.Creator<Accounts>() {
        @Override
        public Accounts createFromParcel(Parcel in) {
            return new Accounts(in);
        }

        @Override
        public Accounts[] newArray(int size) {
            return new Accounts[size];
        }
    };
}
