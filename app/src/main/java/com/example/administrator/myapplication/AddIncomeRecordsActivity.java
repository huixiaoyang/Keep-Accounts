package com.example.administrator.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2018\3\30 0030.
 */

public class AddIncomeRecordsActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income_records);
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
        AddIncomeRecordsActivity.this.finish();
    }
}
