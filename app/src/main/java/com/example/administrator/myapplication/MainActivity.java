package com.example.administrator.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.administrator.myapplication.R.id.listView_main;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private SimpleAdapter sim_aAdapter; // 1. 新建一个数据适配器
    private List<Map<String, Object>>dataList; // 数据源
    public static MainActivity instance = null;
    private DatabaseHelper databaseHelper;
    public static String TABLE_NAME_SPENDING = "acounts";
    public static String TABLE_NAME_INCOME = "income";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        instance = this;

        listView = (ListView)findViewById(R.id.listView_main);
        // 2. 适配器加载数据源
        dataList = new ArrayList<Map<String, Object>>();
        sim_aAdapter = new SimpleAdapter(this, getData(TABLE_NAME_SPENDING), R.layout.activity_main_item,
                new String[]{"text_date","image_type", "text_type","text_type_detail","text_money"},
                new int[]{R.id.tv_date,R.id.imageview_type, R.id.tv_type,R.id.tv_type_detail,R.id.tv_money});
        // 3. 视图(ListView)加载适配器
        listView.setAdapter(sim_aAdapter);
    }
    private List<Map<String, Object>> getData(String table){
        Cursor cursor = databaseHelper.readData(table);
        if(cursor.getCount()!=0){
            cursor.moveToLast();
            do{
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("text_date", cursor.getString(2));
                map.put("image_type", getIdSpending(cursor.getString(1)));
                map.put("text_type", cursor.getString(1));
                map.put("text_type_detail", cursor.getString(3));
                map.put("text_money", cursor.getString(4));
                dataList.add(map);
            }while(cursor.moveToPrevious());
        }

        cursor = databaseHelper.readData(TABLE_NAME_INCOME);
        if(cursor.getCount()!=0){
            cursor.moveToLast();
            do{
                Map<String, Object>map = new HashMap<String, Object>();
                map.put("text_date", cursor.getString(2));
                map.put("image_type", getIdIncome(cursor.getString(1)));
                map.put("text_type", cursor.getString(1));
                map.put("text_type_detail", cursor.getString(3));
                map.put("text_money", cursor.getString(4));
                dataList.add(map);
            }while(cursor.moveToPrevious());
        }

        Collections.sort(dataList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String time1 = o1.get("text_date").toString();
                String time2 = o2.get("text_date").toString();
                SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
                Date bt = null;
                Date et = null;
                try{
                    bt = sdf.parse(time1);
                    et = sdf.parse(time2);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if( bt.before(et))
                    return 1;
                else
                    return -1;
            }
        });
        return dataList;
    }
    public void btnAddSpendingRecords(View view) {
        //to do when the button is clicked
        startActivity(new Intent(MainActivity.this, AddSpendingRecordsActivity.class));
    }

    public int getIdSpending(String type){
        int id = R.drawable.food_sel;

        switch (type){
            case "food": id = R.drawable.food_sel; break;
            case "traffic": id = R.drawable.traffic_sel; break;
            case "drink": id = R.drawable.drink_sel; break;
            case "daily necessities": id = R.drawable.daily_sel; break;
            case "cloth": id = R.drawable.cloth_sel; break;
            case "red envelope": id = R.drawable.red_envelope_sel; break;
            case "phone top up": id = R.drawable.top_up_sel; break;
            case "recreation": id = R.drawable.recreation_sel; break;
            case "treatment": id = R.drawable.treatment_sel; break;
            case "stationery": id = R.drawable.stationery_sel; break;
            case "book": id = R.drawable.book_sel; break;
            case "tution": id = R.drawable.tuition_sel; break;
        }
        return id;
    }

    public int getIdIncome(String type){
        int id = R.drawable.salary_sel;

        switch (type){
            case "salary": id = R.drawable.salary_sel; break;
            case "lend": id = R.drawable.lend_money_sel; break;
            case "pocket money": id = R.drawable.pocket_money_sel; break;
            case "lottery ticket": id = R.drawable.lottery_ticker_sel; break;
            case "red envelope": id = R.drawable.red_envelope_sel; break;
        }
        return id;
    }

}
