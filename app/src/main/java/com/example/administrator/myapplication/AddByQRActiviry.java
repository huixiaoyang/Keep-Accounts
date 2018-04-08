package com.example.administrator.myapplication;

import android.content.res.AssetManager;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddByQRActiviry extends AppCompatActivity {

    private ListView listView;
    private SimpleAdapter sim_aAdapter; // 1. 新建一个数据适配器
    private List<Map<String, Object>> dataList; // 数据源
    String data;
    DatabaseHelper databaseHelper;
    float total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_by_qractiviry);
        databaseHelper = new DatabaseHelper(this);

        listView = (ListView) findViewById(R.id.lv_order);
        dataList = new ArrayList<Map<String, Object>>();
        sim_aAdapter = new SimpleAdapter(this, getData(), R.layout.order_item,
                new String[]{"image_type", "text_type", "text_type_detail", "text_money"},
                new int[]{R.id.imageview_type, R.id.tv_type, R.id.tv_type_detail, R.id.tv_money});
        // 3. 视图(ListView)加载适配器
        listView.setAdapter(sim_aAdapter);
    }

    private List<Map<String, Object>> getData() {
        data = getIntent().getExtras().getString("data");
        if (data.indexOf("date") != -1) {

        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.instance);
            builder.setMessage("No data");
            final AlertDialog alert = builder.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alert.dismiss();
                }
            }, 1000);
            finish();
        }
        try {
            JSONObject root = new JSONObject(data);
            ((TextView) findViewById(R.id.tx_date)).setText(root.getString("date"));
            JSONArray book = root.getJSONArray("book");
            JSONObject b;
            for (int i = 0; i < book.length(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                b = book.getJSONObject(i);
                map.put("image_type", R.drawable.book_sel);
                map.put("text_type", "book");
                map.put("text_type_detail", b.getString("comment"));
                map.put("text_money", b.getString("money"));
                total += Float.valueOf(b.getString("money"));
                dataList.add(map);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ((TextView) findViewById(R.id.tx_total_money)).setText(String.valueOf(total));

        return dataList;
    }

    public void btnCancle(View view) {
        finish();
    }

    public void btnOk(View view) {
        //insert into database
        try {
            JSONObject root = new JSONObject(data);
            JSONArray book = root.getJSONArray("book");
            String time = root.getString("date");
            JSONObject b;
            for (int i = 0; i < book.length(); i++) {
                b = book.getJSONObject(i);
                String type = "book";
                String comment = b.getString("comment");
                String money = b.getString("money");
                boolean dataInserted = databaseHelper.insertData("acounts", type, time, comment, Float.valueOf(money));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainActivity.instance.recreate();
        finish();
    }
}
