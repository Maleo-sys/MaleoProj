package com.appsnipp.homedesigns;

import android.content.Intent;
import androidx.annotation.NonNull;

import com.appsnipp.homedesigns.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.appsnipp.homedesigns.AAChartCoreLib.AAChartCreator.AAChartView;
import com.appsnipp.homedesigns.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.appsnipp.homedesigns.AAChartCoreLib.AAChartEnum.AAChartSymbolType;
import com.appsnipp.homedesigns.AAChartCoreLib.AAChartEnum.AAChartType;
import com.appsnipp.homedesigns.AAChartCoreLib.AAOptionsModel.AAMarker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


public class FollowUpCenter extends AppCompatActivity {
private static final String TAG = "FollowUpCenter";
    private AAChartModel CurrChartModel;
    private AAChartView aaChartView;
    private Object[] weight;
    private Object[] head_c;
    private Object[] length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

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
                .title("אחוזון משקל")
                .subtitle("משקל בגרמים, עפי מידע מארגון הבריאות העולמי")
                .categories(new String[]{"month","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"})
                .backgroundColor("#00ccff")

                .dataLabelsEnabled(false)
                .yAxisGridLineWidth(0f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("my baby")
                                .color("#000067")
                                .size(0.2)
                                .marker(new AAMarker().symbol(AAChartSymbolType.Diamond))
                                .data(new Object[]{
                                        3.027282,
                                        4.080792,
                                        5.117754,
                                        5.888058,
                                        6.484777,
                                        6.966941,
                                        7.366195,
                                        7.706413,
                                        8.003205,
                                        8.26946,
                                        8.5139,
                                        8.742959,
                                        8.960956,
                                        9.170505,
                                        9.373665,
                                        9.571948,
                                        9.7667,
                                        9.958406,
                                        10.14755,
                                        10.33431,
                                        10.51961,
                                        10.70383,
                                        10.88716,
                                        11.06946,
                                        11.25065,
                                }),
                        new AASeriesElement()
                                .name("98%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#ff0000")
                                .data(new Object[]{
                                4.419354,
                                5.798331,
                                7.090758,
                                8.024169,
                                8.746662,
                                9.342238,
                                9.848832,
                                10.29113,
                                10.68428,
                                11.04177,
                                11.37341,
                                11.6862,
                                11.98574,
                                12.27589,
                                12.55884,
                                12.83707,
                                13.11206,
                                13.38491,
                                13.65558,
                                13.92552,
                                14.19492,
                                14.46469,
                                14.7352,
                                15.0059,
                                15.27674,
                        }),
                        new AASeriesElement()
                                .name("75%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                3.686659,
                                4.889123,
                                6.048448,
                                6.897306,
                                7.554286,
                                8.090161,
                                8.539707,
                                8.927371,
                                9.268678,
                                9.5769,
                                9.861313,
                                10.12867,
                                10.38387,
                                10.63014,
                                10.86959,
                                11.10416,
                                11.33528,
                                11.5637,
                                11.7897,
                                12.01396,
                                12.23713,
                                12.45983,
                                12.6823,
                                12.90424,
                                13.12555,
                        }),
                        new AASeriesElement()
                                .name("50%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                3.3464,
                                4.4709,
                                5.5675,
                                6.3762,
                                7.0023,
                                7.5105,
                                7.934,
                                8.297,
                                8.6151,
                                8.9014,
                                9.1649,
                                9.4122,
                                9.6479,
                                9.8749,
                                10.0953,
                                10.3108,
                                10.5228,
                                10.7319,
                                10.9385,
                                11.143,
                                11.3462,
                                11.5486,
                                11.7504,
                                11.9514,
                                12.1515,
                        }),
                        new AASeriesElement()
                                .name("25%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                3.027282,
                                4.080792,
                                5.117754,
                                5.888058,
                                6.484777,
                                6.966941,
                                7.366195,
                                7.706413,
                                8.003205,
                                8.26946,
                                8.5139,
                                8.742959,
                                8.960956,
                                9.170505,
                                9.373665,
                                9.571948,
                                9.7667,
                                9.958406,
                                10.14755,
                                10.33431,
                                10.51961,
                                10.70383,
                                10.88716,
                                11.06946,
                                11.25065,
                        }),
                        new AASeriesElement()
                                .name("2%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#ff0000")
                                .data(new Object[]{
                                2.459312,
                                3.39089,
                                4.31889,
                                5.018434,
                                5.561377,
                                5.996672,
                                6.352967,
                                6.653301,
                                6.913126,
                                7.144822,
                                7.356558,
                                7.55441,
                                7.742219,
                                7.922091,
                                8.095984,
                                8.265127,
                                8.430734,
                                8.593128,
                                8.752902,
                                8.909889,
                                9.065209,
                                9.219037,
                                9.371554,
                                9.522741,
                                9.672527,
                        })
                });
        this.CurrChartModel = aaChartModel;

        return aaChartModel;
    }

    private AAChartModel configureHeightChartModel(){
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Line)
                .title("אחוזון גובה")
                .subtitle("גובה בסנטימטרים, עפי מידע מארגון הבריאות העולמי")
                .categories(new String[]{"month","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"})
                .backgroundColor("#00ccff")
                .dataLabelsEnabled(false)
                .yAxisGridLineWidth(0f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("my baby")
                                .color("#000067")
                                .marker(new AAMarker().symbol(AAChartSymbolType.Diamond))
                                .data(new Object[]{48.60732,
                                53.41147,
                                57.0756,
                                60.0503,
                                62.48254,
                                64.4784,
                                66.18,
                                67.70013,
                                69.1118,
                                70.45564,
                                71.74005,
                                72.96769,
                                74.14605,
                                75.28228,
                                76.37879,
                                77.43914,
                                78.46814,
                                79.46765,
                                80.43942,
                                81.38338,
                                82.30162,
                                83.19621,
                                84.06859,
                                84.92082,
                                85.75545,}),
                        new AASeriesElement()
                                .name("98%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#ff0000")
                                .data(new Object[]{
                                53.67041,
                                58.61749,
                                62.42584,
                                65.51793,
                                68.04753,
                                70.12564,
                                71.90417,
                                73.50665,
                                75.01045,
                                76.45523,
                                77.85102,
                                79.19748,
                                80.50128,
                                81.77063,
                                83.0043,
                                84.20638,
                                85.38012,
                                86.52987,
                                87.65323,
                                88.75241,
                                89.8275,
                                90.8831,
                                91.91615,
                                92.93123,
                                93.92634,
                        }),
                        new AASeriesElement()
                                .name("75%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                51.16108,
                                56.03733,
                                59.7742,
                                62.8081,
                                65.28946,
                                67.3268,
                                69.0672,
                                70.62887,
                                72.087,
                                73.48176,
                                74.82235,
                                76.10991,
                                77.35155,
                                78.55492,
                                79.72061,
                                80.85246,
                                81.95446,
                                83.02975,
                                84.07798,
                                85.10022,
                                86.09758,
                                87.07339,
                                88.02681,
                                88.96118,
                                89.87675,
                        }),
                        new AASeriesElement()
                                .name("50%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                49.8842,
                                54.7244,
                                58.4249,
                                61.4292,
                                63.886,
                                65.9026,
                                67.6236,
                                69.1645,
                                70.5994,
                                71.9687,
                                73.2812,
                                74.5388,
                                75.7488,
                                76.9186,
                                78.0497,
                                79.1458,
                                80.2113,
                                81.2487,
                                82.2587,
                                83.2418,
                                84.1996,
                                85.1348,
                                86.0477,
                                86.941,
                                87.8161,
                        }),
                        new AASeriesElement()
                                .name("25%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                48.60732,
                                53.41147,
                                57.0756,
                                60.0503,
                                62.48254,
                                64.4784,
                                66.18,
                                67.70013,
                                69.1118,
                                70.45564,
                                71.74005,
                                72.96769,
                                74.14605,
                                75.28228,
                                76.37879,
                                77.43914,
                                78.46814,
                                79.46765,
                                80.43942,
                                81.38338,
                                82.30162,
                                83.19621,
                                84.06859,
                                84.92082,
                                85.75545,
                        }),
                        new AASeriesElement()
                                .name("2%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#ff0000")
                                .data(new Object[]{
                                46.09799,
                                50.83131,
                                54.42396,
                                57.34047,
                                59.72447,
                                61.67956,
                                63.34303,
                                64.82235,
                                66.18835,
                                67.48217,
                                68.71138,
                                69.88013,
                                70.99632,
                                72.06657,
                                73.09511,
                                74.08522,
                                75.04248,
                                75.96753,
                                76.86417,
                                77.73119,
                                78.5717,
                                79.3865,
                                80.17925,
                                80.95077,
                                81.70586,
                        })
                });
        this.CurrChartModel = aaChartModel;

        return aaChartModel;
    }

    private AAChartModel configureHeadDiameterChartModel(){

        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Line)
                .title("היקף הראש")
                .subtitle("היקף בסנטימטרים, עפי מידע מארגון הבריאות העולמי")
                .backgroundColor("#00ccff")
                .dataLabelsEnabled(false)
                .categories(new String[]{"month","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"})
                .yAxisGridLineWidth(0f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("my baby")
                                .color("#000067")
                                .marker(new AAMarker().symbol(AAChartSymbolType.Diamond))
                                .data(new Object[]{33.60502,
                                36.48819,
                                38.33754,
                                39.71613,
                                40.82636,
                                41.74325,
                                42.5073,
                                43.14851,
                                43.69022,
                                44.15237,
                                44.55065,
                                44.89654,
                                45.19953,
                                45.46778,
                                45.70745,
                                45.92456,
                                46.12259,
                                46.30582,
                                46.47646,
                                46.63699,
                                46.78927,
                                46.93407,
                                47.07289,
                                47.2058,
                                47.3334,}),
                        new AASeriesElement()
                                .name("98%")
                                .size(0.2)
                                .color("#ff0000")
                                .marker(new AAMarker().radius(0f))
                                .data(new Object[]{
                                37.0023239,
                                39.6116079,
                                41.4738623,
                                42.8778679,
                                44.0196943,
                                44.9723182,
                                45.771846,
                                46.4467152,
                                47.0201176,
                                47.5125888,
                                47.9387046,
                                48.3096422,
                                48.6356671,
                                48.9243173,
                                49.1847412,
                                49.419647,
                                49.6365919,
                                49.8363554,
                                50.0238816,
                                50.2005513,
                                50.3683694,
                                50.529453,
                                50.6828405,
                                50.8311864,
                                50.9738496,
                        }),
                        new AASeriesElement()
                                .name("75%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                35.31858,
                                38.06361,
                                39.91946,
                                41.31087,
                                42.43704,
                                43.37195,
                                44.1539,
                                44.81209,
                                45.36978,
                                45.84723,
                                46.25955,
                                46.61806,
                                46.93267,
                                47.21122,
                                47.46135,
                                47.68744,
                                47.89501,
                                48.08658,
                                48.26574,
                                48.43441,
                                48.59453,
                                48.74753,
                                48.89371,
                                49.0344,
                                49.1696,
                        }),
                        new AASeriesElement()
                                .name("50%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                34.4618,
                                37.2759,
                                39.1285,
                                40.5135,
                                41.6317,
                                42.5576,
                                43.3306,
                                43.9803,
                                44.53,
                                44.9998,
                                45.4051,
                                45.7573,
                                46.0661,
                                46.3395,
                                46.5844,
                                46.806,
                                47.0088,
                                47.1962,
                                47.3711,
                                47.5357,
                                47.6919,
                                47.8408,
                                47.9833,
                                48.1201,
                                48.2515,
                        }),
                        new AASeriesElement()
                                .name("25%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                33.60502,
                                36.48819,
                                38.33754,
                                39.71613,
                                40.82636,
                                41.74325,
                                42.5073,
                                43.14851,
                                43.69022,
                                44.15237,
                                44.55065,
                                44.89654,
                                45.19953,
                                45.46778,
                                45.70745,
                                45.92456,
                                46.12259,
                                46.30582,
                                46.47646,
                                46.63699,
                                46.78927,
                                46.93407,
                                47.07289,
                                47.2058,
                                47.3334,
                        }),
                        new AASeriesElement()
                                .name("2%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#ff0000")
                                .data(new Object[]{
                                31.92128,
                                34.94019,
                                36.78314,
                                38.14913,
                                39.24371,
                                40.14288,
                                40.88935,
                                41.51388,
                                42.03988,
                                42.48701,
                                42.8715,
                                43.20496,
                                43.49653,
                                43.75468,
                                43.98406,
                                44.19235,
                                44.38101,
                                44.55604,
                                44.71832,
                                44.87085,
                                45.01543,
                                45.15215,
                                45.28376,
                                45.40901,
                                45.52915,
                        })
                });
        this.CurrChartModel = aaChartModel;

        return aaChartModel;
    }

}