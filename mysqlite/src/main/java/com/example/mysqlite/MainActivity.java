package com.example.mysqlite;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MySQLiteHelper myDb;
    private Button deleteBTN, showAllBTN, updateBTN, singleBTN, insertBTN;
    private EditText idET, nameET, numberET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new MySQLiteHelper(this);


        setViews();

        insertBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(nameET.getText().toString(), numberET.getText().toString());
                if (isInserted)
                    Toast.makeText(MainActivity.this, "inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "not inserted", Toast.LENGTH_SHORT).show();
            }
        });


        updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change the number on that name
                boolean isUpdated = myDb.updateData(nameET.getText().toString(), numberET.getText().toString());
                if (isUpdated)
                    Toast.makeText(MainActivity.this, "updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "not updated", Toast.LENGTH_SHORT).show();
            }
        });

        showAllBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String allData = myDb.getAllData();

                showD("DATA",allData);
            }
        });
        singleBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String part = myDb.showPart(nameET.getText().toString());

                showD("DATA",part);
            }
        });
        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleted = myDb.deleteData(nameET.getText().toString());
                if (isDeleted)
                    Toast.makeText(MainActivity.this, "deleted", Toast.LENGTH_SHORT).show();
                else Toast.makeText(MainActivity.this, "not deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void showD(String title,String data){
        AlertDialog.Builder builder
                =new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(data);
        builder.show();

    }

    private void setViews() {
        idET = (EditText) findViewById(R.id.idET);
        nameET = (EditText) findViewById(R.id.nameET);
        numberET = (EditText) findViewById(R.id.numberET);

        insertBTN = (Button) findViewById(R.id.insertBTN);
        deleteBTN = (Button) findViewById(R.id.deleteBTN);
        showAllBTN = (Button) findViewById(R.id.showAllBTN);
        singleBTN = (Button) findViewById(R.id.singleBTN);
        updateBTN = (Button) findViewById(R.id.updateBTN);
    }


    @Override
    protected void onDestroy() {
        myDb.close();
        super.onDestroy();
    }
}
