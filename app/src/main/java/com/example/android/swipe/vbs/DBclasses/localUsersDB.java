package com.example.android.swipe.vbs.DBclasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by android on 10/1/2015.
 */
public class localUsersDB extends SQLiteOpenHelper {

    public static final String DataBase_Name = "localVBS.db";
    public static final String TABLE_NAME = "activeUsersList";
    public static final String name = "name";
    public static final String surname = "surname";
    public static final String username = "username";
    public static final String userNo = "userno";
    public static final String password = "password";
    public static final String sube = "sube";
    public static final String sezon = "sezon";
    public static final String userType = "usertype";
    public static final String server = "server";
    public static final String isActive = "isActive";
    public static final int DATABASE_VERSION = 1;

    public localUsersDB(Context context) {
        super(context, DataBase_Name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dbObject) {
        String sqlQuerry = "CREATE TABLE " + TABLE_NAME+ " ( " +username + " TEXT PRIMARY KEY, "
                + name + " TEXT NOT NULL, " + surname + " TEXT NOT NULL, "
                + password + " TEXT NOT NULL, "  + userNo + " INTEGER NOT NULL, "
                + sube + " INTEGER, " + sezon + " INTEGER, " + userType + " TEXT NOT NULL, "
                + server + " TEXT, " + isActive + " TEXT NOT NULL )";
        dbObject.execSQL(sqlQuerry);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)
            return;

        String sql = null;
        if (oldVersion == 1)
            sql = "alter table " + TABLE_NAME + " add note text;";
        if (oldVersion == 2)
            sql = "";

        Log.d("EventsData", "onUpgrade	: " + sql);
        if (sql != null)
            db.execSQL(sql);
    }

}
