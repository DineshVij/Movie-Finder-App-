package com.creativeDApps.movieFinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Vinay on 1/15/2016.
 */
public class SqlController {
    private DBHelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public SqlController(Context c) {
        ourcontext = c;
    }

    public SqlController open() throws SQLException {
        dbhelper = new DBHelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;

    }

    public void close() {
        dbhelper.close();
    }

    public void insertData(String movie_name, String movie_year) {
        ContentValues cv = new ContentValues();

        cv.put(DBHelper.NAME, movie_name);
        cv.put(DBHelper.YEAR,movie_year);

        database.insert(DBHelper.TABLE_NAME, null, cv);
    }

    public Cursor readData() {

        Cursor c = database.rawQuery("select rowid _id,* from movie_list",null);
           //     database.query(DBHelper.TABLE_NAME, allColumns, null,
             //   null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor readSingleCol(long id) {
        Cursor cursor = database.query(DBHelper.TABLE_NAME,new String[]{DBHelper.ID,DBHelper.NAME,DBHelper.YEAR}, DBHelper.ID+"="+id, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;


    }

    public int updateData(Long ID, String movieName,String movieYear) {
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(DBHelper.NAME, movieName);
        cvUpdate.put(DBHelper.YEAR,movieYear);
        int i = database.update(DBHelper.TABLE_NAME,
                cvUpdate,
                DBHelper.ID + " = " + ID, null);
        return i;
    }

    public void deleteData(long ID) {
        database.delete(DBHelper.TABLE_NAME, DBHelper.ID + "="
                + ID, null);
    }

    public void deleteAll() {
        database.execSQL("delete from "+ DBHelper.TABLE_NAME);

    }






}
