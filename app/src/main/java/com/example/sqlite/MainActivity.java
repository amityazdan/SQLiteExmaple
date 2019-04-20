package com.example.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    private EditText editTextName, editTextSurname, marks, editTextId;
    private Button btnAdd,btnViewAll,updateBtn,deleteBtn,singleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);


        setViews();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddData();
            }
        });

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCursor();

            }
        });


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });

        singleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSingle();
            }
        });
    }

    private void getSingle() {
        myDb.getSingleValue(editTextId.getText().toString());

    }

    private void deleteData() {
        Integer deletedRows =myDb.deleteData(editTextId.getText().toString());
        if (deletedRows>0)
            Toast.makeText(this, "Data deleted", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Data not deleted", Toast.LENGTH_SHORT).show();

    }

    private void updateData() {
        boolean isUpdate=myDb.updateData(
                editTextId.getText().toString(),
                editTextName.getText().toString(),
                editTextSurname.getText().toString(),
                marks.getText().toString());
        if (isUpdate)
            Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_SHORT).show();
    }

    public void showMessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    private void setViews() {
        editTextName = (EditText) findViewById(R.id.nameET);
        editTextId = (EditText) findViewById(R.id.idET);
        editTextSurname = (EditText) findViewById(R.id.surET);
        marks = (EditText) findViewById(R.id.marksET);
        btnAdd = (Button) findViewById(R.id.btn);
        btnViewAll = (Button) findViewById(R.id.btnViewAll);
        updateBtn = (Button) findViewById(R.id.updateBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        singleBtn = (Button) findViewById(R.id.singleBtn);
    }


    public void AddData() {
        boolean isInserted = myDb.insertData(editTextName.getText().toString(), editTextSurname.getText().toString(), marks.getText().toString());
        if (isInserted)
            Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Data is not inserted", Toast.LENGTH_SHORT).show();
    }

    public void showCursor(){
        Cursor cursor=myDb.getAllData();
        if (cursor.getCount()==0){
            showMessage("Error","No Message found");
            return;
        }

        StringBuffer stringBuffer=new StringBuffer();
        while(cursor.moveToNext()){
            stringBuffer.append("ID :"+cursor.getString(0)+"\n");
            stringBuffer.append("Name :"+cursor.getString(1)+"\n");
            stringBuffer.append("Surname :"+cursor.getString(2)+"\n");
            stringBuffer.append("Marks :"+cursor.getString(3)+"\n\n");
        }

        showMessage("Data",stringBuffer.toString());
    }


}
