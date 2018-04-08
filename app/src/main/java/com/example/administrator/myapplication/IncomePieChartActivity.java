package com.example.administrator.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018\4\6 0006.
 */

public class IncomePieChartActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private PieChart mPieChart;
    private DatabaseHelper databaseHelper;
    public static String TABLE_NAME_INCOME = "income";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_pie_chart);

        databaseHelper = new DatabaseHelper(this);
        initChart();
    }

    public void btnReturnMain(View view) {
        //to do when the button is clicked
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void btnReturnSpending(View view) {
        //to do when the button is clicked
        startActivity(new Intent(this, SpendingPieChartActivity.class));
        finish();
    }

    private void initChart() {
        mPieChart = (PieChart) findViewById(R.id.mPieChart);
        mPieChart.setUsePercentValues(true);//设置value是否用显示百分数
        //mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);//设置图距离上下左右的偏移量
        mPieChart.setDragDecelerationFrictionCoef(0.95f);//设置阻尼系数,范围在[0,1]之间,越小饼状图转动越困难

        mPieChart.setDrawHoleEnabled(true);//是否绘制饼状图中间的圆
        mPieChart.setHoleColor(Color.WHITE);//饼状图中间的圆的绘制颜色
        mPieChart.setHoleRadius(58f);//饼状图中间的圆的半径大小

        mPieChart.setTransparentCircleColor(Color.WHITE);//设置圆环的颜色
        mPieChart.setTransparentCircleAlpha(110);//设置圆环的透明度[0,255]
        mPieChart.setTransparentCircleRadius(61f);//设置圆环的半径值

        mPieChart.setDrawCenterText(true);//是否绘制中间的文字
        mPieChart.setCenterText(generateCenterSpannableText());//设置中间文字
        mPieChart.setCenterTextSize(24);//中间的文字字体大小

        mPieChart.setRotationEnabled(true);//设置饼状图是否可以旋转(默认为true)
        mPieChart.setRotationAngle(0);//设置饼状图旋转的角度
        mPieChart.setHighlightPerTapEnabled(true);//设置旋转的时候点中的tab是否高亮(默认为true)

        //变化监听
        mPieChart.setOnChartValueSelectedListener(this);

        //从数据库中获取数据并使用数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        getData(entries);

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);//设置Y轴上的绘制动画

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);//设置tab之间Y轴方向上的空白间距值
        l.setYOffset(0f);

        // 设置每块扇形中文字标签样式
        mPieChart.setEntryLabelColor(Color.BLACK);
        mPieChart.setEntryLabelTextSize(12f);
    }

    //设置中间文字
    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("March Income");
        return s;
    }

    //从数据库中获取数据
    private void getData(ArrayList<PieEntry> entries) {
        Cursor cursor = databaseHelper.readData(TABLE_NAME_INCOME);
        if (cursor.getCount() != 0) {
            float[] intMoney = new float[50];
            String[] strType = new String[50];
            int flag = 1;

            if (cursor.getCount() != 0) {
                cursor.moveToLast();

                String tempType = cursor.getString(1);
                String tempMoney = cursor.getString(4);
                float tempIntMoney = Float.valueOf(tempMoney);
                strType[0] = tempType;
                intMoney[0] = tempIntMoney;

                flag:
                while (cursor.moveToPrevious()) {
                    tempType = cursor.getString(1);
                    tempMoney = cursor.getString(4);
                    tempIntMoney = Integer.valueOf(tempMoney).intValue();
                    for (int i = 0; i < flag; i++) {
                        if (strType[i].equals(tempType)) {
                            intMoney[i] = intMoney[i] + tempIntMoney;
                            continue flag;
                        }
                    }
                    strType[flag] = tempType;
                    intMoney[flag] = tempIntMoney;
                    ++flag;
                }
            }
            float totalmoney = 0;
            for (int i = 0; i < flag; i++)
                totalmoney = totalmoney + intMoney[i];
            for (int i = 0; i < flag; i++) {
                entries.add(new PieEntry(100 * intMoney[i] / totalmoney, strType[i]));
            }
            //使用数据
            setData(entries);
        }
    }

    //使用数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "Income");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
