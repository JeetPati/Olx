package com.example.hackathon1.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by Hackathon 1 on 26-09-2015.
 */
public class ProcessDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_DESRCPTION,MySQLiteHelper.COLUMN_PRICE, MySQLiteHelper.COLUMN_CATEG, MySQLiteHelper.COLUMN_LOC  };

    public ProcessDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {

        dbHelper.close();
    }

    public void  insertData(ContentValues values) {
        long insertId = database.insert(MySQLiteHelper.TABLE_NAME, null,
                values);
    }
}
