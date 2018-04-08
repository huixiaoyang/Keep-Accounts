package com.example.administrator.myapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BudgetActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        databaseHelper = new DatabaseHelper(this);
        et = (EditText)findViewById(R.id.et_budget);
        et.setCursorVisible(false);
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setCursorVisible(true);
            }
        });

        Cursor cursor = databaseHelper.readData("budget");
        cursor.moveToLast();
        if(cursor.getCount()!=0)
            ((EditText)findViewById(R.id.et_budget)).setText(cursor.getString(2));

        ProgressBar pb = (ProgressBar)findViewById(R.id.pb_left);
        float budget = Float.valueOf(et.getText().toString());
        if (budget == 0)
            pb.setProgress(0);
        else{
            float spending=Float.valueOf(getIntent().getExtras().getString("spending"));
            pb.setProgress((int) ((spending/budget)*100));
        }
    }

    public void btnReturn(View view) {
        finish();
    }

    public void btnEdit(View view) {
        //insert into database
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        databaseHelper.setBudget(time, Float.valueOf(((EditText)findViewById(R.id.et_budget)).getText().toString()));
        MainActivity.instance.recreate();
        finish();
    }


}
