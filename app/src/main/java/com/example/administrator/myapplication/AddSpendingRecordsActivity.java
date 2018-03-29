package com.example.administrator.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2018\3\30 0030.
 */

public class AddSpendingRecordsActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spending_records);
    }
    public void btnAddIncomeRecords(View view) {
        //to do when the button is clicked
        Intent itent=new Intent();
        itent.setClass(AddSpendingRecordsActivity.this, AddIncomeRecordsActivity.class);
        startActivity(itent);
        AddSpendingRecordsActivity.this.finish();
    }
    public void clk_cancel(View view){
        AddSpendingRecordsActivity.this.finish();
    }
    public void clk_ok(View view){
        //add to the database, then close this activity
        AddSpendingRecordsActivity.this.finish();
    }
}
