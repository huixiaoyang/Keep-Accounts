package com.example.administrator.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private SimpleAdapter sim_aAdapter; // 1. 新建一个数据适配器
    private List<Map<String, Object>> dataList; // 数据源
    public static MainActivity instance = null;
    private DatabaseHelper databaseHelper;
    public static String TABLE_NAME_SPENDING = "acounts";
    public static String TABLE_NAME_INCOME = "income";
    float spend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        instance = this;

        listView = (ListView) findViewById(R.id.listView_main);
        // 2. 适配器加载数据源
        dataList = new ArrayList<Map<String, Object>>();
        sim_aAdapter = new SimpleAdapter(this, getData(TABLE_NAME_SPENDING), R.layout.activity_main_item,
                new String[]{"text_date", "image_type", "text_type", "text_type_detail", "text_money"},
                new int[]{R.id.tv_date, R.id.imageview_type, R.id.tv_type, R.id.tv_type_detail, R.id.tv_money});
        // 3. 视图(ListView)加载适配器
        listView.setAdapter(sim_aAdapter);

        Cursor budget = databaseHelper.readData("budget");
        budget.moveToLast();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        spend = getSpend(time);
        float income = getIncome(time);
        if (budget.getCount() != 0) {

            if (budget.getString(1).substring(0, 7).equals(time)) {
                float left = Float.valueOf(budget.getString(2)) - spend;
                ((TextView) findViewById(R.id.btn_total_money)).setText(String.valueOf(left));
            }
        }
        ((TextView) findViewById(R.id.tv_month_income)).setText(String.valueOf(income));
        ((TextView) findViewById(R.id.tv_month_spend)).setText(String.valueOf(spend));
    }

    private float getSpend(String month) {
        //get all money spent this month
        Cursor cursor = databaseHelper.readData("acounts");
        float spend = 0;
        if (cursor.getCount() == 0)
            return spend;
        while (cursor.moveToNext()) {
            String date = cursor.getString(2).substring(0, 7);
            if (date.equals(month))
                spend += Float.valueOf(cursor.getString(4));
        }
        return spend;
    }

    private float getIncome(String month) {
        //get all money spent this month
        Cursor cursor = databaseHelper.readData("income");
        float income = 0;
        if (cursor.getCount() == 0)
            return income;
        while (cursor.moveToNext()) {
            String date = cursor.getString(2).substring(0, 7);
            if (date.equals(month))
                income += Float.valueOf(cursor.getString(4));
        }
        return income;
    }

    private List<Map<String, Object>> getData(String table) {
        Cursor cursor = databaseHelper.readData(table);
        if (cursor.getCount() != 0) {
            cursor.moveToLast();
            do {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("text_date", cursor.getString(2));
                map.put("image_type", getIdSpending(cursor.getString(1)));
                map.put("text_type", cursor.getString(1));
                map.put("text_type_detail", cursor.getString(3));
                map.put("text_money", cursor.getString(4));
                dataList.add(map);
            } while (cursor.moveToPrevious());
        }

        cursor = databaseHelper.readData(TABLE_NAME_INCOME);
        if (cursor.getCount() != 0) {
            cursor.moveToLast();
            do {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("text_date", cursor.getString(2));
                map.put("image_type", getIdIncome(cursor.getString(1)));
                map.put("text_type", cursor.getString(1));
                map.put("text_type_detail", cursor.getString(3));
                map.put("text_money", cursor.getString(4));
                dataList.add(map);
            } while (cursor.moveToPrevious());

        }

        //sort by time
        Collections.sort(dataList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String time1 = o1.get("text_date").toString();
                String time2 = o2.get("text_date").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM");
                Date bt = null;
                Date et = null;
                try {
                    bt = sdf.parse(time1);
                    et = sdf.parse(time2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (bt.before(et))
                    return -1;
                else
                    return 1;
            }
        });
        return dataList;
    }

    public void btnAddSpendingRecords(View view) {
        //to do when the button is clicked
        startActivity(new Intent(this, AddSpendingRecordsActivity.class));
    }

    public int getIdSpending(String type) {

        int id = R.drawable.food_sel;

        switch (type) {
            case "food":
                id = R.drawable.food_sel;
                break;
            case "traffic":
                id = R.drawable.traffic_sel;
                break;
            case "drink":
                id = R.drawable.drink_sel;
                break;
            case "daily necessities":
                id = R.drawable.daily_sel;
                break;
            case "cloth":
                id = R.drawable.cloth_sel;
                break;
            case "red envelope":
                id = R.drawable.red_envelope_sel;
                break;
            case "phone top up":
                id = R.drawable.top_up_sel;
                break;
            case "recreation":
                id = R.drawable.recreation_sel;
                break;
            case "treatment":
                id = R.drawable.treatment_sel;
                break;
            case "stationery":
                id = R.drawable.stationery_sel;
                break;
            case "book":
                id = R.drawable.book_sel;
                break;
            case "tution":
                id = R.drawable.tuition_sel;
                break;
        }
        return id;
    }

    public int getIdIncome(String type) {
        int id = R.drawable.salary_sel;

        switch (type) {
            case "salary":
                id = R.drawable.salary_sel;
                break;
            case "lend":
                id = R.drawable.lend_money_sel;
                break;
            case "pocket money":
                id = R.drawable.pocket_money_sel;
                break;
            case "lottery ticket":
                id = R.drawable.lottery_ticker_sel;
                break;
            case "red envelope":
                id = R.drawable.red_envelope_sel;
                break;
        }
        return id;
    }

    public void btnSpendingPieChart(View view) {
        //to do when the button is clicked
        startActivity(new Intent(this, SpendingPieChartActivity.class));
    }

    public void btnAddByQR(View view) {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        } else {
            Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
            startActivityForResult(intent, 111);
        }
    }

    public void setBudget(View view) {
        Intent intent = new Intent(this, BudgetActivity.class);
        intent.putExtra("spending", String.valueOf(spend));
        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Intent intent = new Intent(this, AddByQRActiviry.class);
                intent.putExtra("data", content);
                startActivity(intent);
            }
        }
    }

    public void clk_income(View view) {
        //close this activity and open the AddIncomeRecordsActivity
        startActivity(new Intent(this, AddIncomeRecordsActivity.class));
    }
}
