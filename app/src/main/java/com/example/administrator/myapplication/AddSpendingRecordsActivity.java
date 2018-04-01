package com.example.administrator.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018\3\30 0030.
 */

public class AddSpendingRecordsActivity extends AppCompatActivity{

    private int[] imageIdUseBlackAndWhite;
    private int[] imageIdUseMulticolor;
    private int[] imageButtonId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spending_records);

        imageIdUseMulticolor = new int[]{R.drawable.food_sel, R.drawable.traffic_sel, R.drawable.drink_sel, R.drawable.daily_sel,
                R.drawable.cloth_sel, R.drawable.red_envelope_sel, R.drawable.top_up_sel, R.drawable.recreation_sel,
                R.drawable.treatment_sel, R.drawable.stationery_sel, R.drawable.book_sel, R.drawable.tuition_sel,};

        imageIdUseBlackAndWhite = new int[]{R.drawable.food_unsel, R.drawable.traffic_unsel, R.drawable.drink_unsel, R.drawable.daily_unsel,
                R.drawable.cloth_unsel, R.drawable.red_envelope_unsel, R.drawable.top_up_unsel, R.drawable.recreation_unsel,
                R.drawable.treatment_unsel, R.drawable.stationery_unsel, R.drawable.book_unsel, R.drawable.tuition_unsel,};

        imageButtonId = new int[]{R.id.btn_food,R.id.btn_traffic,R.id.btn_drink,R.id.btn_daily_necessities,
                R.id.btn_cloth,R.id.btn_red_envelope,R.id.btn_phone_top_up,R.id.btn_recreation,
                R.id.btn_treatment,R.id.btn_stationery,R.id.btn_book,R.id.btn_tution,};
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

    public void clk_select(View view){
        ImageButton imageButtonSelect = (ImageButton) view;
        imageButtonSelect.setImageResource(imageIdUseMulticolor[(Integer.valueOf(view.getTag().toString()))]);

        ImageButton imageButtonShow =  (ImageButton)findViewById(R.id.btn_selected);
        imageButtonShow.setImageResource(imageIdUseMulticolor[(Integer.valueOf(view.getTag().toString()))]);

        for(int i=0;i<12;i++){
            ImageButton imageButtonNotSelect = (ImageButton)findViewById(imageButtonId[i]);
            if(Integer.valueOf(view.getTag().toString())!=i)
                imageButtonNotSelect.setImageResource(imageIdUseBlackAndWhite[i]);
        }
    }
}
