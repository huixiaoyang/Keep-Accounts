package com.example.administrator.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2018\3\30 0030.
 */

public class AddIncomeRecordsActivity extends AppCompatActivity{
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income_records);

        databaseHelper = new DatabaseHelper(this);
    }
    public void btnAddSpendingRecords(View view) {
        //to do when the button is clicked
        Intent itent=new Intent();
        itent.setClass(AddIncomeRecordsActivity.this, AddSpendingRecordsActivity.class);
        startActivity(itent);
        AddIncomeRecordsActivity.this.finish();
    }

    public void clk_cancel(View view){
        AddIncomeRecordsActivity.this.finish();
    }
    public void clk_ok(View view){
        //add to the database, then close this activity
        //add to the database, then close this activity
        String type = ((ImageButton)findViewById(R.id.btn_selected)).getContentDescription().toString();
        DateFormat formatter = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        String time = formatter.format(calendar.getTime());
        String comment = "dinner";
        float money = Float.valueOf(((EditText)findViewById(R.id.et_money)).getText().toString());
        boolean dataInserted = databaseHelper.insertData(type, time, comment, money);
        if(dataInserted = true){
            Log.d(getPackageName(), "Data inserted");
        }else{
            Log.e(getPackageName(), "Failed inserted data");
        }

        //close this activity
        AddIncomeRecordsActivity.this.finish();
    }
}
