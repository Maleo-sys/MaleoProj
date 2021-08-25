package com.appsnipp.homedesigns;

import android.content.Intent;
import androidx.annotation.NonNull;

import com.appsnipp.homedesigns.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.appsnipp.homedesigns.AAChartCoreLib.AAChartCreator.AAChartView;
import com.appsnipp.homedesigns.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.appsnipp.homedesigns.AAChartCoreLib.AAChartEnum.AAChartType;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


public class FollowUpCenter extends AppCompatActivity {
private static final String TAG = "FollowUpCenter";
    private AAChartModel CurrChartModel;
    private AAChartView aaChartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_follow_up_center);
        aaChartView = findViewById(R.id.AAChartView);

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

                    case  R.id.navigationAppointments:
                        startActivity(new Intent(getApplicationContext(), AppointmentsCenter.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        // Initialize and assign variable
        BottomNavigationView followup_top_navigation = findViewById(R.id.followup_nav);
        // Set selected view
        followup_top_navigation.setSelectedItemId(R.id.head_measurement);
        CurrChartModel = configureHeadDiameterChartModel();
        aaChartView.aa_drawChartWithChartModel(CurrChartModel);
        //Perform ItemSelectedListener
        followup_top_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.head_measurement:
                        CurrChartModel = configureHeadDiameterChartModel();
                        aaChartView.aa_drawChartWithChartModel(CurrChartModel);
                        return true;

                    case R.id.weight_measurement:
                        CurrChartModel = configureWeightChartModel();
                        aaChartView.aa_drawChartWithChartModel(CurrChartModel);
                        return true;

                    case  R.id.height_measurement:
                        CurrChartModel =configureHeightChartModel();
                        aaChartView.aa_drawChartWithChartModel(CurrChartModel);

                        return true;
                }
                return false;
            }
        });
    }


    private AAChartModel configureWeightChartModel(){
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Line)
                .title(" אחוזון משקל")
                .subtitle("עפי מידע מאתר ")
                .backgroundColor("#9BF6FF")
                .dataLabelsEnabled(false)
                .yAxisGridLineWidth(0f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("100%")
                                .data(new Object[]{7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6})
                                .color("#000000"),

                        new AASeriesElement()
                                .name("75%")
                                .data(new Object[]{0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5})
                                .color("#000000"),
                        new AASeriesElement()
                                .name("My Baby")
                                .data(new Object[]{0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0})
                                .color("#376DF6"),
                        new AASeriesElement()
                                .name("25%")
                                .data(new Object[]{3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8})
                                .color("#000000"),

                });
        this.CurrChartModel = aaChartModel;

        return aaChartModel;
    }

    private AAChartModel configureHeightChartModel(){
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Line)
                .title("אחוזון גובה")
                .subtitle("עפי מידע מאתר ")
                .backgroundColor("#BDB2FF")
                .dataLabelsEnabled(false)
                .yAxisGridLineWidth(0f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("100%")
                                .data(new Object[]{55, 60, 65, 70, 72, 76, 80, 82, 87, 89, 92, 94}),
                        new AASeriesElement()
                                .name("75%")
                                .data(new Object[]{ 45, 42, 44, 47, 50, 55, 60, 65, 70, 72}),
                        new AASeriesElement()
                                .name("My Baby")
                                .data(new Object[]{42, 44, 44, 46, 52, 59.4, 64.6, 70, 74}),
                        new AASeriesElement()
                                .name("25%")
                                .data(new Object[]{3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8})
                });
        this.CurrChartModel = aaChartModel;

        return aaChartModel;
    }

    private AAChartModel configureHeadDiameterChartModel(){
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Line)
                .title("היקף הראש")
                .subtitle("היקף בסנטימטרים, עפי מידע מאתר")
                .backgroundColor("#CAFFBF")
                .dataLabelsEnabled(false)
                .yAxisGridLineWidth(0f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("100%")
                                .data(new Object[]{30,32,34,36,38,40,42,44,46,48,50}),
                        new AASeriesElement()
                                .name("75%")
                                .data(new Object[]{26,28,30,32,34,36,38,40,42,44}),
                        new AASeriesElement()
                                .name("My Baby")
                                .data(new Object[]{20,24,28,34,38.24,42,47,51.5}),
                        new AASeriesElement()
                                .name("25%")
                                .data(new Object[]{16,18,20,22,24,26,28,30,32,34})
                });
        this.CurrChartModel = aaChartModel;

        return aaChartModel;
    }

}