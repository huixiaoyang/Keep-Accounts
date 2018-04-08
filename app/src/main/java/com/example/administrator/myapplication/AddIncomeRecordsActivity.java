package com.example.administrator.myapplication;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018\3\30 0030.
 */

public class AddIncomeRecordsActivity extends AppCompatActivity {
    private int[] imageIdUseBlackAndWhite;
    private int[] imageIdUseMulticolor;
    private int[] imageButtonId;
    private DatabaseHelper databaseHelper;

    public static String TABLE_NAME_INCOME = "income";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income_records);

        databaseHelper = new DatabaseHelper(this);

        imageIdUseMulticolor = new int[]{R.drawable.salary_sel, R.drawable.lend_money_sel, R.drawable.red_envelope_sel, R.drawable.pocket_money_sel,
                R.drawable.lottery_ticker_sel};

        imageIdUseBlackAndWhite = new int[]{R.drawable.salary_unsel, R.drawable.lend_money_unsel, R.drawable.red_envelope_unsel, R.drawable.pocket_money_unsel,
                R.drawable.lottery_ticket_unsel};

        imageButtonId = new int[]{R.id.btn_salary, R.id.btn_lend, R.id.btn_red, R.id.btn_pocket_money,
                R.id.btn_lottery_ticket};
    }

    public void clk_spending(View view) {
        //to do when the button is clicked
        startActivity(new Intent(this, AddSpendingRecordsActivity.class));
        finish();
    }

    public void clk_cancel(View view) {
        finish();
    }

    public void clk_ok(View view) {
        //add to the database, then close this activity
        if (TextUtils.isEmpty(((EditText) findViewById(R.id.et_money)).getText())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please input money");
            final AlertDialog alert = builder.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alert.dismiss();
                }
            }, 1000);
            return;
        }
        String type = ((ImageButton) findViewById(R.id.btn_selected)).getContentDescription().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        String comment = ((EditText) findViewById(R.id.et_comment)).getText().toString();
        float money = Float.valueOf(((EditText) findViewById(R.id.et_money)).getText().toString());
        boolean dataInserted = databaseHelper.insertData(TABLE_NAME_INCOME, type, time, comment, money);
        if (dataInserted = true) {
            Log.d(getPackageName(), "Data inserted");
        } else {
            Log.e(getPackageName(), "Failed inserted data");
        }

        //close this activity
        MainActivity.instance.recreate();
        finish();
    }

    public void clk_select(View view) {
        ImageButton imageButtonSelect = (ImageButton) view;
        imageButtonSelect.setImageResource(imageIdUseMulticolor[(Integer.valueOf(view.getTag().toString()))]);

        ImageButton imageButtonShow = (ImageButton) findViewById(R.id.btn_selected);
        imageButtonShow.setImageResource(imageIdUseMulticolor[(Integer.valueOf(view.getTag().toString()))]);
        imageButtonShow.setContentDescription(imageButtonSelect.getContentDescription());

        for (int i = 0; i < 5; i++) {
            ImageButton imageButtonNotSelect = (ImageButton) findViewById(imageButtonId[i]);
            if (Integer.valueOf(view.getTag().toString()) != i)
                imageButtonNotSelect.setImageResource(imageIdUseBlackAndWhite[i]);
        }
    }
}
