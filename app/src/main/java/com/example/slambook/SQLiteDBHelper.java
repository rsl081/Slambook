package com.example.slambook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.Toast;

import java.sql.Blob;
import java.util.List;

public class SQLiteDBHelper extends SQLiteOpenHelper {

    final private static String DATABASE_NAME = "MyDatabase.db";
    final private static int VERSION = 1;
    Context context;

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;

        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_USER_TABLE = "CREATE TABLE '"+DB_Conn.UserDatabase.USER_TB+
                "' (" + " '"+DB_Conn.UserDatabase.COLUMN_USER_ID+"' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'"+DB_Conn.UserDatabase.COLUMN_USER_IMAGE+"'BLOB NOT NULL,"+
                "'"+DB_Conn.UserDatabase.COLUMN_USER_USERNAME+"'TEXT NOT NULL,"+
                "'"+DB_Conn.UserDatabase.COLUMN_USER_PASSWORD+"'TEXT NOT NULL," +
                "'"+DB_Conn.UserDatabase.COLUMN_USER_EMAIL+"'TEXT NOT NULL," +
                "'"+DB_Conn.UserDatabase.COLUMN_USER_BDAY+"'DATE NOT NULL," +
                "'"+DB_Conn.UserDatabase.COLUMN_USER_FULLNAME+"'TEXT NOT NULL," +
                "'"+DB_Conn.UserDatabase.COLUMN_USER_GENDER+"'TEXT NOT NULL," +
                "'"+DB_Conn.UserDatabase.COLUMN_USER_ADDRESS+"'TEXT NOT NULL," +
                "'"+DB_Conn.UserDatabase.COLUMN_USER_CONTACT+"'INTEGER NOT NULL,"+
                "'"+DB_Conn.UserDatabase.COLUMN_USER_HOBBIES+"'TEXT NOT NULL,"+
                "'"+DB_Conn.UserDatabase.COLUMN_USER_SECQUES1+"'TEXT NOT NULL," +
                "'"+DB_Conn.UserDatabase.COLUMN_USER_SECQUES2+"'TEXT NOT NULL," +
                "'"+DB_Conn.UserDatabase.COLUMN_USER_SECQUES3+"'TEXT NOT NULL)";

        final  String CREATE_ENTRYLIST_TABLE = "CREATE TABLE '"+DB_Conn.EntryList_Data.Entry_TB+
                "' (" + " '"+DB_Conn.EntryList_Data.COLUMN_ENTRY_ID+"'INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'"+DB_Conn.EntryList_Data.COLUMN_ENTRY_FN+"'TEXT NOT NULL, "+
                "'"+DB_Conn.EntryList_Data.COLUMN_ENTRY_MN+"'TEXT NOT NULL, "+
                "'"+DB_Conn.EntryList_Data.COLUMN_ENTRY_LN+"'TEXT NOT NULL, "+
                "'"+DB_Conn.EntryList_Data.COLUMN_ENTRY_REMARK+"'TEXT NOT NULL, "+
                "'"+DB_Conn.EntryList_Data.COLUMN_ENTRY_BDAY+"'DATE NOT NULL," +
                "'"+DB_Conn.EntryList_Data.COLUMN_ENTRY_GENDER+"'TEXT, "+
                "'"+DB_Conn.EntryList_Data.COLUMN_ENTRY_ADDRESS+"'TEXT, "+
                "'"+DB_Conn.EntryList_Data.COLUMN_ENTRY_CONTACT+"'INTEGER, "+
                "'"+DB_Conn.EntryList_Data.COLUMN_ENTRY_HOBBIES+"'TEXT NOT NULL, "+"" +
                "'"+DB_Conn.EntryList_Data.COLUMN_ENTRY_GOALS+"'TEXT, "+
                "'"+DB_Conn.EntryList_Data.COLUMN_ENTRY_IMAGE+"'BLOB NOT NULL)";

        try {
            db.execSQL(CREATE_USER_TABLE);
            Toast.makeText(context, "Database Created", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            db.execSQL(CREATE_ENTRYLIST_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}


    public boolean InsertUser(byte[] profilePic, String username, String password, String email,
                              String bday, String fullname, String gender,
                              String address, String contact, StringBuilder hobbies,
                              String seques1, String seques2, String seques3)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DB_Conn.UserDatabase.COLUMN_USER_IMAGE, profilePic);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_USERNAME,username);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_PASSWORD,password);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_EMAIL,email);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_BDAY,bday);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_FULLNAME,fullname);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_GENDER,gender);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_ADDRESS,address);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_CONTACT,contact);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_HOBBIES, String.valueOf(hobbies));
        values.put(DB_Conn.UserDatabase.COLUMN_USER_SECQUES1,seques1);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_SECQUES2,seques2);
        values.put(DB_Conn.UserDatabase.COLUMN_USER_SECQUES3,seques3);

        long result = db.insert(DB_Conn.UserDatabase.USER_TB,null,values);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }// end of Insert User Curly Braces

    public Cursor SelectAllUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.query(DB_Conn.UserDatabase.USER_TB,null,null,null,
                null,null,null);
        return result;
    }// end of SelectAllUser Curly Braces

    public Cursor SelectUserByID(String ID){
        SQLiteDatabase db = this.getReadableDatabase();
        //Column
        String[] columns = {
                DB_Conn.UserDatabase.COLUMN_USER_ID,
                DB_Conn.UserDatabase.COLUMN_USER_USERNAME
        };

        //Filters Selection and SelectionARgs: WHERE `ID` = 1 AND `name` LIKE `%anna%`
        String selection = DB_Conn.UserDatabase.COLUMN_USER_ID + " = ? ";
        String[] selectionArgs = {ID};

        //Sort
        String orderBy = DB_Conn.UserDatabase.COLUMN_USER_ID + " DESC";

        Cursor result = db.query(DB_Conn.UserDatabase.USER_TB,columns,selection,selectionArgs,
                null,null,null);
        return result;
    }//End of SelectUserByID CurlyBraces




}
