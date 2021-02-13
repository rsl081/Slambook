package com.example.slambook;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    final private static String DATABASE_NAME = "Mydatabase.db";
    final private static int VERSION = 1;
    Context context;

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;

        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_USER_TABLE = "CREATE TABLE '"+DB_Conn.User.USER_TB+"' (" + " '"+DB_Conn.User.User_id+"' INTEGER PRIMARY KEY, " +
                "'"+DB_Conn.User.USER_username+"'TEXT NOT NULL," +
                "'"+DB_Conn.User.USER_password+"'TEXT NOT NULL," +
                "'"+DB_Conn.User.USER_email+"'TEXT NOT NULL," +
                "'"+DB_Conn.User.USER_bday+"'DATE NOT NULL," +
                "'"+DB_Conn.User.USER_fullname+"'TEXT NOT NULL," +
                "'"+DB_Conn.User.USER_gender+"'TEXT NOT NULL," +
                "'"+DB_Conn.User.USER_address+"'TEXT NOT NULL," +
                "'"+DB_Conn.User.USER_contact+"'INTEGER NOT NULL," +
                "'"+DB_Conn.User.USER_hobbies+"'TEXT NOT NULL," +
                "'"+DB_Conn.User.USER_secques1+"'TEXT NOT NULL," +
                "'"+DB_Conn.User.USER_secques2+"'TEXT NOT NULL," +
                "'"+DB_Conn.User.USER_secques3+"'TEXT NOT NULL," +
                "'"+DB_Conn.User.USER_Image+"'BLOB NOT NULL)";

        final  String CREATE_ENTRYLIST_TABLE = "CREATE TABLE '"+DB_Conn.EntryList_Data.Entry_TB+"' (" + " '"+DB_Conn.EntryList_Data.Entry_id+"' INTEGER PRIMARY KEY," +
                "'"+DB_Conn.EntryList_Data.User_id+"'INTEGER NOT NULL," +
                "'"+DB_Conn.EntryList_Data.Entry_name+"'TEXT NOT NULL, "+
                "'"+DB_Conn.EntryList_Data.Entry_remark+"'TEXT NOT NULL, "+
                "'"+DB_Conn.EntryList_Data.Entry_bday+"'DATE NOT NULL," +
                "'"+DB_Conn.EntryList_Data.Entry_id+"'TEXT NOT NULL, "+
                "'"+DB_Conn.EntryList_Data.Entry_Address+"'TEXT, "+
                "'"+DB_Conn.EntryList_Data.Entry_contact+"'INTEGER, "+
                "'"+DB_Conn.EntryList_Data.Entry_hobbies+"'TEXT NOT NULL, "+"" +
                "'"+DB_Conn.EntryList_Data.Entry_goals+"'TEXT, "+
                "'"+DB_Conn.EntryList_Data.Entry_image+"'BLOB,"+
                "FOREIGN KEY (`"+DB_Conn.EntryList_Data.User_id+"`) REFERENCES " +
                " `"+DB_Conn.User.USER_TB+"`(`"+DB_Conn.User.User_id+"`)," +
                " UNIQUE (`"+DB_Conn.EntryList_Data.Entry_id+"`) ON CONFLICT ABORT);";

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
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
