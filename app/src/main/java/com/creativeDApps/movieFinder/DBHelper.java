package com.creativeDApps.movieFinder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vinay on 1/15/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public  static final String TABLE_NAME="movie_list";
    public static final String ID="_id";
    public static final String NAME="movie_name";
    public static final String YEAR="movie_year";

    static final String DB_NAME="database";
    static final int DB_VERSION=1;

    private static final String CREATE_TABLE="create table movie_list"+"(_id integer primary key, movie_name text, movie_year text)";


    public DBHelper(Context context) {
        super(context, DB_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     //   String CREATE_TABLE="create table Contacts_List"+"(_Contacts_ID integer primary key, First_Name text)";
        db.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
}
