package com.appsnipp.homedesigns;

import android.content.Intent;
import android.graphics.Color;
//import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class FollowUpCenter extends AppCompatActivity {
private static final String TAG = "FollowUpCenter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_follow_up_center);
     // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
     // Set selected view
        bottomNavigationView.setSelectedItemId(R.id.navigationFollowUp);
    //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigationFollowUp:
                        startActivity(new Intent(getApplicationContext(), FollowUpCenter.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.navigationPersonalSpace:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.navigationDataCenter:
                        startActivity(new Intent(getApplicationContext(), DataCenter.class));
                        overridePendingTransition(0,0);
                        return true;

                    case  R.id.navigationContact:
                        return true;
                }
                return false;
            }
        });

        Create_Chart();

    }

    private void Create_Chart() {
        // using MPAndroidChart
        LineChart WeightChart = (LineChart) findViewById(R.id.line_chart);
        WeightChart.setScaleEnabled(false);
        WeightChart.setDragEnabled(true);

        ArrayList<Entry> y_weight_values = new ArrayList<>();
        y_weight_values.add(new Entry(0,700f));
        y_weight_values.add(new Entry(2,1200f));
        y_weight_values.add(new Entry(4,1600f));
        y_weight_values.add(new Entry(6,2000f));
        y_weight_values.add(new Entry(8,2200f));

        LineDataSet weight_values = new LineDataSet(y_weight_values, "משקל(גרם)");

        weight_values.setFillAlpha(110);
        weight_values.setColor(Color.MAGENTA);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(weight_values);
        LineData data = new LineData(dataSets);
        WeightChart.setData(data);
        WeightChart.setOnChartGestureListener(new OnChartGestureListener()
        {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {
                Toast.makeText(FollowUpCenter.this, "Chart clicked multiple times, oh stop!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
                Toast.makeText(FollowUpCenter.this, "Chart clicked one time! it's tickling ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
                Toast.makeText(FollowUpCenter.this, "Chart scaled", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });

    }
}