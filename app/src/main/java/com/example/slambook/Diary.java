package com.example.slambook;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Diary implements Parcelable {

    private long idDiary;
    private byte[] byteDiaryPic;
    private int profileDiaryPic;
    private String subject;
    private String message;
    private String date;
    private String time;
    private Accounts accounts;
    private Bitmap bitmapImageDiary;

    public Diary(){}

    public Diary(byte[] byteDiaryPic, int profileDiaryPic, String subject, String message,
                 String date, String time)
    {
        this.byteDiaryPic = byteDiaryPic;
        this.profileDiaryPic = profileDiaryPic;
        this.subject = subject;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    //==================GET===============================================
    public long getIdDiary()
    {
        return idDiary;
    }
    public byte[] getbyteDiaryPic()
    {
        return  byteDiaryPic;
    }
    public int getprofileDiaryPic() { return profileDiaryPic; }
    public String getSubject()
    {
        return subject;
    }
    public String getMessage() { return message; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public Accounts getAccounts() { return accounts; }

    //==================SET===============================================
    public void setIdDiary(long idDiary)
    {
        this.idDiary = idDiary;
    }
    public void setbyteDiaryPic(byte[] byteDiaryPic) { this.byteDiaryPic = byteDiaryPic; }
    public void setprofileDiaryPic(int profileDiaryPic)
    {
        this.profileDiaryPic = profileDiaryPic;
    }
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
    public void setMessage(String message) { this.message = message; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setAccounts(Accounts accounts) { this.accounts = accounts; }

    public void setBitmapImageDiary(Bitmap bitmapImageDiary){ this.bitmapImageDiary = bitmapImageDiary; }

    public Diary(Parcel in)
    {
        this.idDiary = in.readLong();
        this.byteDiaryPic = in.createByteArray();
        this.subject = in.readString();
        this.message = in.readString();
        this.date = in.readString();
        this.time = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeLong(this.idDiary);
        parcel.writeByteArray(this.byteDiaryPic);
        parcel.writeString(this.subject);
        parcel.writeString(this.message);
        parcel.writeString(this.date);
        parcel.writeString(this.time);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Diary createFromParcel(Parcel in) {
            return new Diary(in);
        }

        public Diary[] newArray(int size) {
            return new Diary[size];
        }
    };
}
