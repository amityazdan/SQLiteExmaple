package com.example.googlesqliteexample;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteHelper dbHelper;
    private Button insertBTN, showAllBTN,deleteBTN,updateBTN;
    private EditText nameET, phoneET,idET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new SQLiteHelper(this);


        setView();

        insertBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = dbHelper.insertData(nameET.getText().toString(), phoneET.getText().toString(), "false");
                if (isInserted)
                    Toast.makeText(MainActivity.this, "succeed", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "not succeed", Toast.LENGTH_SHORT).show();

            }
        });

        showAllBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Cursor cursor = dbHelper.getAllData();
//                if (cursor.getCount() == 0) {
//                    showDialog("Error", "No Message found");
//                    return;
//                }
//
//                StringBuffer stringBuffer = new StringBuffer();
//                while (cursor.moveToNext()) {
//                    stringBuffer.append("ID :" + cursor.getString(0) + "\n");
//                    stringBuffer.append("Name :" + cursor.getString(1) + "\n");
//                    stringBuffer.append("Surname :" + cursor.getString(2) + "\n");
//                    stringBuffer.append("Marks :" + cursor.getString(3) + "\n\n");
//                }

                showDialog("DATA", dbHelper.getData());
            }
        });

        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleted=dbHelper.deleteData(idET.getText().toString());
                if (isDeleted)
                    Toast.makeText(MainActivity.this, "deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "not deleted", Toast.LENGTH_SHORT).show();
            }
        });

        updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated=dbHelper.updateData(idET.getText().toString(),
                        nameET.getText().toString(),
                        phoneET.getText().toString(),
                        "false");
                if (isUpdated)
                    Toast.makeText(MainActivity.this, "updated", Toast.LENGTH_SHORT).show();
                else Toast.makeText(MainActivity.this, "not updated", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void showDialog(String title, String data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(data);
        builder.show();
    }

    private void setView() {
        insertBTN = (Button) findViewById(R.id.insertBTN);
        showAllBTN = (Button) findViewById(R.id.showAllBTN);
        deleteBTN = (Button) findViewById(R.id.deleteBTN);
        updateBTN = (Button) findViewById(R.id.updateBTN);
        idET = (EditText) findViewById(R.id.idET);
        nameET = (EditText) findViewById(R.id.nameET);
        phoneET = (EditText) findViewById(R.id.phoneET);
    }
}
