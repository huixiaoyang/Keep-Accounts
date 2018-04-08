package com.example.administrator.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class AddSpendingRecordsActivity extends AppCompatActivity {
    private int[] imageIdUseBlackAndWhite;
    private int[] imageIdUseMulticolor;
    private int[] imageButtonId;
    private DatabaseHelper databaseHelper;

    public static String TABLE_NAME_SPENDING = "acounts";
    public static String TABLE_NAME_INCOME = "income";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spending_records);

        databaseHelper = new DatabaseHelper(this);

        imageIdUseMulticolor = new int[]{R.drawable.food_sel, R.drawable.traffic_sel, R.drawable.drink_sel, R.drawable.daily_sel,
                R.drawable.cloth_sel, R.drawable.red_envelope_sel, R.drawable.top_up_sel, R.drawable.recreation_sel,
                R.drawable.treatment_sel, R.drawable.stationery_sel, R.drawable.book_sel, R.drawable.tuition_sel,};

        imageIdUseBlackAndWhite = new int[]{R.drawable.food_unsel, R.drawable.traffic_unsel, R.drawable.drink_unsel, R.drawable.daily_unsel,
                R.drawable.cloth_unsel, R.drawable.red_envelope_unsel, R.drawable.top_up_unsel, R.drawable.recreation_unsel,
                R.drawable.treatment_unsel, R.drawable.stationery_unsel, R.drawable.book_unsel, R.drawable.tuition_unsel,};

        imageButtonId = new int[]{R.id.btn_food, R.id.btn_traffic, R.id.btn_drink, R.id.btn_daily_necessities,
                R.id.btn_cloth, R.id.btn_red_envelope, R.id.btn_phone_top_up, R.id.btn_recreation,
                R.id.btn_treatment, R.id.btn_stationery, R.id.btn_book, R.id.btn_tution,};
    }

    public void clk_income(View view) {
        //close this activity and open the AddIncomeRecordsActivity
        startActivity(new Intent(this, AddIncomeRecordsActivity.class));
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

        boolean dataInserted = databaseHelper.insertData(TABLE_NAME_SPENDING, type, time, comment, money);

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

        for (int i = 0; i < 12; i++) {
            ImageButton imageButtonNotSelect = (ImageButton) findViewById(imageButtonId[i]);
            if (Integer.valueOf(view.getTag().toString()) != i)
                imageButtonNotSelect.setImageResource(imageIdUseBlackAndWhite[i]);
        }
    }
}
