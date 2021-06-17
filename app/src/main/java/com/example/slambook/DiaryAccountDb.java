package com.example.slambook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DiaryAccountDb
{
    public static final String TAG = "DiaryAccountDb";

    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;

    private String[] mAllColumns = {
            DB_Conn.Diary_Data.COLUMN_DIARY_ID,
            DB_Conn.Diary_Data.COLUMN_DIARY_IMAGE,
            DB_Conn.Diary_Data.COLUMN_DIARY_SUBJECT,
            DB_Conn.Diary_Data.COLUMN_DIARY_MESSAGE,
            DB_Conn.Diary_Data.COLUMN_DIARY_DATE,
            DB_Conn.Diary_Data.COLUMN_DIARY_TIME,
            DB_Conn.Diary_Data.COLUMN_DIARY_ACCOUNT_ID
    };

    public DiaryAccountDb(Context context)
    {
        mDbHelper = new DBHelper(context);
        this.mContext = context;

        try
        {
            open();
        }catch (SQLException e)
        {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Diary createDiary(byte[] byteDiaryPic, String subject, String message, String date, String time,
                             long accountId)
    {
        ContentValues values = new ContentValues();
        values.put(DB_Conn.Diary_Data.COLUMN_DIARY_IMAGE, byteDiaryPic);
        values.put(DB_Conn.Diary_Data.COLUMN_DIARY_SUBJECT, subject);
        values.put(DB_Conn.Diary_Data.COLUMN_DIARY_MESSAGE, message);
        values.put(DB_Conn.Diary_Data.COLUMN_DIARY_DATE, date);
        values.put(DB_Conn.Diary_Data.COLUMN_DIARY_TIME, time);
        values.put(DB_Conn.Diary_Data.COLUMN_DIARY_ACCOUNT_ID, accountId);

        long insertId = mDatabase.insert(DB_Conn.Diary_Data.Diary_TB, null, values);

        Cursor cursor = mDatabase.query(DB_Conn.Diary_Data.Diary_TB, mAllColumns,
                DB_Conn.Diary_Data.COLUMN_DIARY_ID + " = " + insertId, null, null,
                null, null);

        cursor.moveToFirst();
        Diary newDiary = cursorToDiary(cursor);
        cursor.close();
        return newDiary;
    }

    public List<Diary> getAllDiary(long diaryID)
    {
        List<Diary> diaryArrayList = new ArrayList<Diary>();

        Cursor cursor = mDatabase.query(DB_Conn.Diary_Data.Diary_TB, mAllColumns,DB_Conn.Diary_Data.COLUMN_DIARY_ACCOUNT_ID + " = ?",
                                        new String[] { String.valueOf(diaryID)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Diary diary = cursorToDiary(cursor);
            diaryArrayList.add(diary);
            cursor.moveToNext();
        }

        cursor.close();
        return diaryArrayList;
    }

    private Diary cursorToDiary(Cursor cursor)
    {
        Diary diary = new Diary();
        diary.setIdDiary(cursor.getLong(0));
        diary.setbyteDiaryPic(cursor.getBlob(1));
        diary.setSubject(cursor.getString(2));
        diary.setMessage(cursor.getString(3));
        diary.setDate(cursor.getString(4));
        diary.setTime(cursor.getString(5));

        return diary;
    }

    public boolean DeleteDiary(long diaryID)
    {
        return mDatabase.delete(DB_Conn.Diary_Data.Diary_TB, DB_Conn.Diary_Data.COLUMN_DIARY_ID + "=" +
                                        diaryID, null) > 0;
    }

    public void UpdateDiary(long getId, byte[] byteDiaryPic, String subject, String message,
                            String date, String time)
    {
        ContentValues values = new ContentValues();
        values.put(DB_Conn.Diary_Data.COLUMN_DIARY_IMAGE, byteDiaryPic);
        values.put(DB_Conn.Diary_Data.COLUMN_DIARY_SUBJECT, subject);
        values.put(DB_Conn.Diary_Data.COLUMN_DIARY_MESSAGE, message);
        values.put(DB_Conn.Diary_Data.COLUMN_DIARY_DATE, date);
        values.put(DB_Conn.Diary_Data.COLUMN_DIARY_TIME, time);

        mDatabase.update(DB_Conn.Diary_Data.Diary_TB, values, DB_Conn.Diary_Data.COLUMN_DIARY_ID + " = ?",
                            new String[]{String.valueOf(getId)});
    }

    public long SenderDiary(String activeDiary)
    {
        long rv = -1;

        mDatabase = mDbHelper.getReadableDatabase();
        Cursor cursor = mDatabase.query(DB_Conn.Diary_Data.Diary_TB, mAllColumns,
                DB_Conn.Diary_Data.COLUMN_DIARY_SUBJECT + " = ?", new String[]{ activeDiary },null,null,null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                rv = cursor.getLong(cursor.getColumnIndex(DB_Conn.Diary_Data.COLUMN_DIARY_ID));
            }
        }
        finally {
            if(cursor != null) {
                cursor.close();
            }
        }
        return rv;
    }

}
