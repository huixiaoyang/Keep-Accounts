package com.example.administrator.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2018\3\30 0030.
 */

public class AddIncomeRecordsActivity extends AppCompatActivity{

    private int[] imageIdUseBlackAndWhite;
    private int[] imageIdUseMulticolor;
    private int[] imageButtonId;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income_records);

        databaseHelper = new DatabaseHelper(this);

        imageIdUseMulticolor = new int[]{R.drawable.salary_sel, R.drawable.lend_money_sel, R.drawable.red_envelope_sel, R.drawable.pocket_money_sel,
                R.drawable.lottery_ticker_sel};

        imageIdUseBlackAndWhite = new int[]{R.drawable.salary_unsel, R.drawable.lend_money_unsel, R.drawable.red_envelope_unsel, R.drawable.pocket_money_unsel,
                R.drawable.lottery_ticket_unsel};

        imageButtonId = new int[]{R.id.btn_salary,R.id.btn_lend,R.id.btn_red,R.id.btn_pocket_money,
                R.id.btn_lottery_ticket};
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
    public void clk_select(View view){
        ImageButton imageButtonSelect = (ImageButton) view;
        imageButtonSelect.setImageResource(imageIdUseMulticolor[(Integer.valueOf(view.getTag().toString()))]);

        ImageButton imageButtonShow =  (ImageButton)findViewById(R.id.btn_selected);
        imageButtonShow.setImageResource(imageIdUseMulticolor[(Integer.valueOf(view.getTag().toString()))]);

        for(int i=0;i<5;i++){
            ImageButton imageButtonNotSelect = (ImageButton)findViewById(imageButtonId[i]);
            if(Integer.valueOf(view.getTag().toString())!=i)
                imageButtonNotSelect.setImageResource(imageIdUseBlackAndWhite[i]);
        }
       // Toast.makeText(this, "Toast text, normal", Toast.LENGTH_SHORT).show();
    }
}
