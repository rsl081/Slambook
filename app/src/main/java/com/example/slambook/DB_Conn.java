package com.example.slambook;

import android.provider.BaseColumns;

public class DB_Conn {
    public static class User implements BaseColumns {
        //registration table
        public static String USER_TB = "table_user";
        //Column name
        public static String User_id = "user_id";
        public static String USER_username = "user_username";
        public static String USER_password = "user_password";
        public static String USER_email = "user_email";
        public static String USER_bday = "user_bday";
        public static String USER_fullname = "user_fullname";
        public static String USER_gender = "user_gender";
        public static String USER_address = "user_address";
        public static String USER_contact = "user_contact";
        public static String USER_hobbies = "user_hobbies";
        public static String USER_secques1 = "user_security_question_1";
        public static String USER_secques2 = "user_security_question_2";
        public static String USER_secques3 = "user_security_question_3";
        public static String USER_Image = "user_image";
    }
    public static class EntryList_Data implements BaseColumns{
        //entrylist table
        public static String Entry_TB = "table_entry";
        //column names
        public static String Entry_id = "entry_id";
        public static String User_id = "user_id";
        public static String Entry_name = "entry_name";
        public static String Entry_remark = "entry_remark";
        public static String Entry_bday = "entry_bday";
        public static String Entry_gender = "entry_gender";
        public static String Entry_Address = "entry_address";
        public static String Entry_contact = "entry_contact";
        public static String Entry_hobbies = "entry_hobbies";
        public static String Entry_goals = "entry_goals";
        public static String Entry_image = "entry_image";
    }
    public static class Post implements BaseColumns {
        //di ko alam ang ipopost mo pwd naman ata na non applicable na to ahahha
    }
}
