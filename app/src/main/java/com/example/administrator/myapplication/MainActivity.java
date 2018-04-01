package com.example.administrator.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.administrator.myapplication.R.id.listView_main;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private SimpleAdapter sim_aAdapter; // 1. 新建一个数据适配器
    private List<Map<String, Object>>dataList; // 数据源

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        listView = (ListView)findViewById(R.id.listView_main);
        // 2. 适配器加载数据源
        dataList = new ArrayList<Map<String, Object>>();
        sim_aAdapter = new SimpleAdapter(this, getData(), R.layout.activity_main_item,
                new String[]{"text_date","image_type", "text_type","text_type_detail","text_money"},
                new int[]{R.id.tv_date,R.id.imageview_type, R.id.tv_type,R.id.tv_type_detail,R.id.tv_money});
        // 3. 视图(ListView)加载适配器
        listView.setAdapter(sim_aAdapter);
    }
    private List<Map<String, Object>> getData(){
        Cursor cursor = databaseHelper.readData();
        while(cursor.moveToNext()){
            Map<String, Object>map = new HashMap<String, Object>();
            map.put("text_date",cursor.getString(2));
            map.put("image_type",R.id.btn_food);
            map.put("text_type",cursor.getString(1));
            map.put("text_type_datail",cursor.getString(3));
            map.put("text_money",cursor.getString(4));
        }
        for (int i = 0; i < 20; i++){
            Map<String, Object>map = new HashMap<String, Object>();
            map.put("text_date", "2018\3\29 Thurday");
            map.put("image_type", R.drawable.food_sel);
            map.put("text_type", "food"+i);
            map.put("text_type_detail", "detail"+i);
            map.put("text_money", "10"+i);
            dataList.add(map);
        }
        return dataList;
    }
    public void btnAddSpendingRecords(View view) {
        //to do when the button is clicked
        startActivity(new Intent(MainActivity.this, AddSpendingRecordsActivity.class));
    }


}
