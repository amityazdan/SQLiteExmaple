package com.example.googlesqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.googlesqliteexample.FeedReaderContract.*;


import static android.provider.BaseColumns._ID;
import static com.example.googlesqliteexample.FeedReaderContract.FeedEntry.TABLE_NAME;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.NAME + " TEXT," +
                    FeedEntry.PHONE + " TEXT," +
                    FeedEntry.FRIEND + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contacts.db";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertData(String name, String phone, String friend) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.NAME, name);
        values.put(FeedEntry.PHONE, phone);
        values.put(FeedEntry.FRIEND, friend);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME, null, values);
        if (newRowId == -1)
            return false;
        else return true;

    }


    public String getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                FeedEntry.NAME
//                FeedEntry.PHONE
        };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = FeedEntry.NAME + " DESC";


        Cursor cursor = db.query(
                TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                _ID + " = ?",              // The columns for the WHERE clause
                new String[]{"1"},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        StringBuffer stringBuffer = new StringBuffer();

//        List itemIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            stringBuffer.append("ID :" + cursor.getString(0) + "\n");
            stringBuffer.append("Name :" + cursor.getString(1) + "\n");
            stringBuffer.append("Phone :" + cursor.getString(2) + "\n");
//            long itemId = cursor.getLong(
//                    cursor.getColumnIndexOrThrow(FeedEntry._ID));
//            itemIds.add(itemId);
        }
        cursor.close();

        return stringBuffer.toString();
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        return cursor;
    }


    public boolean deleteData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define 'where' part of query.
        String selection = _ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {id};
        // Issue SQL statement.
        int deletedRows = db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);

        if (deletedRows > 0)
            return true;
        else
            return false;
    }


    public boolean updateData(String id,String name, String phone, String friend){
        SQLiteDatabase db = this.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(FeedEntry.NAME, name);
        values.put(FeedEntry.PHONE, phone);
        values.put(FeedEntry.FRIEND, friend);

        // Which row to update, based on the title
        String selection = _ID + " LIKE ?";
        String[] selectionArgs = { id };

        int count = db.update(
                TABLE_NAME,
                values,
                selection,
                selectionArgs);


        if (count>0)
            return true;
        else
            return false;

    }
}
