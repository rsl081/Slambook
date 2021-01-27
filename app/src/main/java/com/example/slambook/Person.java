package com.example.slambook;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

public class Person implements Parcelable {
    private int profilePic;
    private Bitmap bitmapImage;
    private String name;
    private String remark;
    private String birthday;
    private String gender;
    private String address;
    private String contact;
    private String hobbies;
    private String goals;

    public Person(int profilePic, String name, String remark, String birthday, String gender, String address, String contact, String hobbies, String goals) {
        this.profilePic = profilePic;
        this.name = name;
        this.remark = remark;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
        this.hobbies = hobbies;
        this.goals = goals;
    }

    public Person(Bitmap bitmapImage, String name, String remark, String birthday, String gender, String address, String contact, String hobbies, String goals) {
        this.bitmapImage = bitmapImage;
        this.name = name;
        this.remark = remark;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.contact = contact;
        this.hobbies = hobbies;
        this.goals = goals;
    }

    // Parcelable
    public Person(Parcel in){
        this.profilePic = in.readInt();
        this.bitmapImage = (Bitmap) in.readValue(Bitmap.class.getClassLoader());

        String[] data = new String[8];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.name = data[0];
        this.remark = data[1];
        this.birthday = data[2];
        this.gender = data[3];
        this.address = data[4];
        this.contact = data[5];
        this.hobbies = data[6];
        this.goals = data[7];
    }

    public void setBitmapImage(Bitmap bitmapImage) { this.bitmapImage = bitmapImage; }

    public void setProfilePic(int profilePic) { this.profilePic = profilePic;}

    public void setAccountName(String accountName) {
        this.name = accountName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public void setGoals(String goals) { this.goals = goals; }

    public Bitmap getBitmapImage() { return bitmapImage; }

    public int getProfilePic() {
        return profilePic;
    }

    public String getAccountName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getHobbies() {
        return hobbies;
    }

    public String getGoals() { return goals; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.profilePic);
        parcel.writeValue(this.bitmapImage);
        parcel.writeStringArray(new String[] {  this.name,
                                                this.remark,
                                                this.birthday,
                                                this.gender,
                                                this.address,
                                                this.contact,
                                                this.hobbies,
                                                this.goals});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
