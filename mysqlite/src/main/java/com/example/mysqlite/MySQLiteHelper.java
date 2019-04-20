package com.example.mysqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contactsDatabase.db";
    private static final String TABLE_NAME = "myContacts";
    private static final String _ID = "id";
    private static final String _NAME = "name";
    private static final String _NUMBER = "number";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (" + _ID + " integer primary key, " + _NAME + " text, " + _NUMBER + " text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public boolean insertData(String name, String number) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(_NAME, name);
        values.put(_NUMBER, number);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME, null, values);
        if (newRowId == -1)
            return false;
        else return true;
    }


    public String getAllData() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);


        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()) {
            stringBuffer.append("ID: " + cursor.getString(0) + "\n");
            stringBuffer.append("Name: " + cursor.getString(1) + "\n");
            stringBuffer.append("number: " + cursor.getString(2) + "\n");

        }
        cursor.close();

        return stringBuffer.toString();
    }



    public String showPart(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.

        // Filter results WHERE "title" = 'My Title'
        String selection = _NAME + " = ?";
        String[] selectionArgs = {name};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                _NAME + " DESC";

        Cursor cursor = db.query(
                TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()) {
            stringBuffer.append("ID: " + cursor.getString(0) + "\n");
            stringBuffer.append("Name: " + cursor.getString(1) + "\n");
            stringBuffer.append("number: " + cursor.getString(2) + "\n");

        }
        cursor.close();

        return stringBuffer.toString();
    }

    public boolean deleteData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Define 'where' part of query.
        String selection = _NAME + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {name};
        // Issue SQL statement.
        int deletedRows = db.delete(TABLE_NAME, selection, selectionArgs);

        if (deletedRows>0)
            return true;
        else return false;
    }

    public boolean updateData(String name,String number){

        SQLiteDatabase db = this.getWritableDatabase();

        // New value for one column
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NUMBER, number);

        // Which row to update, based on the title

        /**
         *
         * LIKE ?
         * = ?
         *
         * */
        String selection = _NAME + " LIKE ?";
        String[] selectionArgs = { name };

        int count = db.update(TABLE_NAME, contentValues, selection, selectionArgs);

        if (count>0)
            return true;
        else return false;

    }
}
