package com.appsnipp.maleo_proj;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.appsnipp.maleo_proj.R;

import com.appsnipp.maleo_proj.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.appsnipp.maleo_proj.AAChartCoreLib.AAChartCreator.AAChartView;
import com.appsnipp.maleo_proj.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.appsnipp.maleo_proj.AAChartCoreLib.AAChartEnum.AAChartSymbolType;
import com.appsnipp.maleo_proj.AAChartCoreLib.AAChartEnum.AAChartType;
import com.appsnipp.maleo_proj.AAChartCoreLib.AAOptionsModel.AAMarker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;


public class FollowUpCenter extends AppCompatActivity {
    private static final String TAG = "FollowUpCenter";
    private AAChartModel CurrChartModel;
    private AAChartView aaChartView;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseUsers;

    ArrayList<Scale> stat_list = new ArrayList<Scale>();
    ;
    Object[] weight_list = new Object[25];
    Object[] headc_list = new Object[25];
    Object[] length_list = new Object[25];
    String name;
    String gender;
//    int fixed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_follow_up_center);
        aaChartView = findViewById(R.id.AAChartView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("baby_name");
            gender = extras.getString("baby_gender");
        }

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser == null) {
                return;
            }
            String currentId = currentUser.getUid();

            DatabaseReference ref0 = FirebaseDatabase.getInstance().getReference("users");
            ref0.child(currentId).child("children").orderByChild("name").limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.getValue(Baby.class) == null){
                        Toast.makeText(FollowUpCenter.this, "נדרש להוסיף תינוק", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        return;
                    }
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (name == null) {
                            name = (String) ds.getValue(Baby.class).getName();
                        }
                        if (gender == null) {
                            gender = (String) ds.getValue(Baby.class).getGender();
                        }
                    }


                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                    ref.child(currentId).child("children").orderByChild("name").equalTo(name).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            stat_list.clear();
                            for (DataSnapshot ds : snapshot.child(name).child("scales").getChildren()) {
                                Scale s = ds.getValue(Scale.class);
                                if (s != null)
                                    stat_list.add(s);
                            }

                            int i = 0;
                            for (Scale s : stat_list) {
                                if (s != null) {
                                    length_list[i] = s.getHeight();
                                    weight_list[i] = s.getWeight();
                                    headc_list[i] = s.getHead();
                                    i++;
                                }
                            }

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
                                            overridePendingTransition(0, 0);
                                            return true;

                                        case R.id.navigationPersonalSpace:
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            overridePendingTransition(0, 0);
                                            return true;

                                        case R.id.navigationDataCenter:
                                            startActivity(new Intent(getApplicationContext(), DataCenter.class));
                                            overridePendingTransition(0, 0);
                                            return true;

                                        case R.id.navigationAppointments:
                                            startActivity(new Intent(getApplicationContext(), AppointmentsCenter.class));
                                            overridePendingTransition(0, 0);
                                            return true;
                                    }
                                    return false;
                                }
                            });

                            // Initialize and assign variable
                            BottomNavigationView followup_top_navigation = findViewById(R.id.followup_nav);
                            // Set selected view
                            followup_top_navigation.setSelectedItemId(R.id.head_measurement);
                            if (gender.equals("m")) {
                                CurrChartModel = configureHeadDiameterMaleChartModel();
                            } else if (gender.equals("f")) {
                                CurrChartModel = configureHeadDiameterFemaleChartModel();
                            }
                            aaChartView.aa_drawChartWithChartModel(CurrChartModel);
                            //Perform ItemSelectedListener
                            followup_top_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                                    switch (menuItem.getItemId()) {
                                        case R.id.head_measurement:
                                            if (gender.equals("m")) {
                                                CurrChartModel = configureHeadDiameterMaleChartModel();
                                            } else if (gender.equals("f")) {
                                                CurrChartModel = configureHeadDiameterFemaleChartModel();
                                            }
                                            aaChartView.aa_drawChartWithChartModel(CurrChartModel);
                                            return true;

                                        case R.id.weight_measurement:
                                            if (gender.equals("m")) {
                                                CurrChartModel = configureWeightMaleChartModel();
                                            } else if (gender.equals("f")) {
                                                CurrChartModel = configureWeightFemaleChartModel();
                                            }
                                            aaChartView.aa_drawChartWithChartModel(CurrChartModel);
                                            return true;

                                        case R.id.height_measurement:
                                            if (gender.equals("m")) {
                                                CurrChartModel = configureHeightMaleChartModel();
                                            } else if (gender.equals("f")) {
                                                CurrChartModel = configureHeightFemaleChartModel();
                                            }
                                            aaChartView.aa_drawChartWithChartModel(CurrChartModel);

                                            return true;
                                    }
                                    return false;
                                }
                            });


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            Toast.makeText(FollowUpCenter.this, "נדרש להתחבר", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
//        Toast.makeText(this, "gender " + gender,
//                Toast.LENGTH_LONG).show(); test


    }




    /*
    represent the growth chart of male's weight
    */

    private AAChartModel configureWeightMaleChartModel() {
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Line)
                .title("אחוזון משקל")
                .subtitle("משקל בגרמים, עפי מידע מארגון הבריאות העולמי")
                .categories(new String[]{"שבוע 0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"})
                .backgroundColor("#CDDAFD")

                .dataLabelsEnabled(false)
                .yAxisGridLineWidth(0f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("My baby")
                                .color("#000067")
                                .size(0.2)
                                .marker(new AAMarker().symbol(AAChartSymbolType.Diamond))
                                .data(weight_list),
                        new AASeriesElement()
                                .name("98%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
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
                                .color("#fffe00")
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

    private AAChartModel configureWeightFemaleChartModel() {
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Line)
                .title("אחוזון משקל")
                .subtitle("משקל בגרמים, עפי מידע מארגון הבריאות העולמי")
                .categories(new String[]{"שבוע 0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"})
                .backgroundColor("#CDDAFD")

                .dataLabelsEnabled(false)
                .yAxisGridLineWidth(0f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("התינוק שלי")
                                .color("#000067")
                                .size(0.2)
                                .marker(new AAMarker().symbol(AAChartSymbolType.Diamond))
                                .data(weight_list),
                        new AASeriesElement()
                                .name("98%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                4.2304,
                                5.4755,
                                6.6297,
                                7.5145,
                                8.2333,
                                8.8294,
                                9.3355,
                                9.7804,
                                10.1811,
                                10.5466,
                                10.8851,
                                11.2039,
                                11.5087,
                                11.8028,
                                12.0898,
                                12.3707,
                                12.6484,
                                12.9237,
                                13.1972,
                                13.4699,
                                13.7422,
                                14.0155,
                                14.2902,
                                14.5669,
                                14.8453

                        }),
                        new AASeriesElement()
                                .name("75%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                3.5504,
                                4.5901,
                                5.5961,
                                6.3642,
                                6.9843,
                                7.495,
                                7.9251,
                                8.2994,
                                8.6331,
                                8.9354,
                                9.2141,
                                9.4761,
                                9.7268,
                                9.9694,
                                10.2067,
                                10.4399,
                                10.6706,
                                10.8998,
                                11.1275,
                                11.3542,
                                11.5803,
                                11.8067,
                                12.0338,
                                12.2618,
                                12.4909

                        }),
                        new AASeriesElement()
                                .name("50%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                3.2322,
                                4.1873,
                                5.1282,
                                5.8458,
                                6.4237,
                                6.8985,
                                7.297,
                                7.6422,
                                7.9487,
                                8.2254,
                                8.48,
                                8.7192,
                                8.9481,
                                9.1699,
                                9.387,
                                9.6008,
                                9.8124,
                                10.0226,
                                10.2315,
                                10.4393,
                                10.6464,
                                10.8534,
                                11.0608,
                                11.2688,
                                11.4775

                        }),
                        new AASeriesElement()
                                .name("25%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                2.9323,
                                3.8143,
                                4.6959,
                                5.368,
                                5.9083,
                                6.3513,
                                6.7221,
                                7.042,
                                7.3249,
                                7.5795,
                                7.8134,
                                8.033,
                                8.2431,
                                8.447,
                                8.6467,
                                8.8437,
                                9.0386,
                                9.2323,
                                9.4248,
                                9.616,
                                9.8065,
                                9.9965,
                                10.1867,
                                10.3771,
                                10.568

                        }),
                        new AASeriesElement()
                                .name("2%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                2.3947,
                                3.1611,
                                3.9411,
                                4.536,
                                5.0134,
                                5.4038,
                                5.7294,
                                6.0084,
                                6.2534,
                                6.4729,
                                6.6738,
                                6.8623,
                                7.0426,
                                7.2178,
                                7.3897,
                                7.5595,
                                7.7276,
                                7.8945,
                                8.0603,
                                8.2246,
                                8.3879,
                                8.5503,
                                8.7124,
                                8.8741,
                                9.0359

                        })
                });
        this.CurrChartModel = aaChartModel;
        return aaChartModel;
    }

    /*
    represent the growth chart of male's height
    */

    private AAChartModel configureHeightMaleChartModel() {
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Line)
                .title("אחוזון גובה")
                .subtitle("גובה בסנטימטרים, עפי מידע מארגון הבריאות העולמי")
                .categories(new String[]{"שבוע 0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"})
                .backgroundColor("#CDDAFD")
                .dataLabelsEnabled(false)
                .yAxisGridLineWidth(0f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("my baby")
                                .color("#000067")
                                .marker(new AAMarker().symbol(AAChartSymbolType.Diamond))
                                .data(length_list),
                        new AASeriesElement()
                                .name("98%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
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
                                .color("#fffe00")
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

    /*
    represent the growth chart of female's height
    */

    private AAChartModel configureHeightFemaleChartModel() {
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Line)
                .title("אחוזון גובה")
                .subtitle("גובה בסנטימטרים, עפי מידע מארגון הבריאות העולמי")
                .categories(new String[]{"שבוע 0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"})
                .backgroundColor("#CDDAFD")
                .dataLabelsEnabled(false)
                .yAxisGridLineWidth(0f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("my baby")
                                .color("#000067")
                                .marker(new AAMarker().symbol(AAChartSymbolType.Diamond))
                                .data(length_list),
                        new AASeriesElement()
                                .name("98%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                52.8731,
                                57.5956,
                                61.1396,
                                64.013,
                                66.4188,
                                68.4648,
                                70.2639,
                                71.918,
                                73.4798,
                                74.975,
                                76.4169,
                                77.8126,
                                79.165,
                                80.4768,
                                81.7498,
                                82.9883,
                                84.1944,
                                85.3691,
                                86.5156,
                                87.6346,
                                88.7294,
                                89.7997,
                                90.8444,
                                91.8675,
                                92.8688

                        }),
                        new AASeriesElement()
                                .name("75%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                50.4041,
                                55.0053,
                                58.4407,
                                61.2227,
                                63.5498,
                                65.5257,
                                67.2598,
                                68.849,
                                70.345,
                                71.7729,
                                73.1461,
                                74.4713,
                                75.7518,
                                76.9912,
                                78.1921,
                                79.3575,
                                80.4903,
                                81.5926,
                                82.6665,
                                83.7135,
                                84.7358,
                                85.7342,
                                86.7085,
                                87.6608,
                                88.5917

                        }),
                        new AASeriesElement()
                                .name("50%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                49.1477,
                                53.6872,
                                57.0673,
                                59.8029,
                                62.0899,
                                64.0301,
                                65.7311,
                                67.2873,
                                68.7498,
                                70.1435,
                                71.4818,
                                72.771,
                                74.015,
                                75.2176,
                                76.3817,
                                77.5099,
                                78.6055,
                                79.671,
                                80.7079,
                                81.7182,
                                82.7036,
                                83.6654,
                                84.604,
                                85.5202,
                                86.4153

                        }),
                        new AASeriesElement()
                                .name("25%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                47.8913,
                                52.3691,
                                55.6939,
                                58.3831,
                                60.63,
                                62.5345,
                                64.2024,
                                65.7256,
                                67.1546,
                                68.5141,
                                69.8175,
                                71.0708,
                                72.2782,
                                73.444,
                                74.5713,
                                75.6623,
                                76.7207,
                                77.7494,
                                78.7493,
                                79.7229,
                                80.6714,
                                81.5966,
                                82.4995,
                                83.3796,
                                84.2389

                        }),
                        new AASeriesElement()
                                .name("2%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                45.4223,
                                49.7788,
                                52.995,
                                55.5928,
                                57.761,
                                59.5954,
                                61.1983,
                                62.6566,
                                64.0198,
                                65.312,
                                66.5467,
                                67.7294,
                                68.865,
                                69.9584,
                                71.0136,
                                72.0315,
                                73.0166,
                                73.9729,
                                74.9002,
                                75.8018,
                                76.6778,
                                77.5311,
                                78.3636,
                                79.1729,
                                79.9618

                        })
                });
        this.CurrChartModel = aaChartModel;

        return aaChartModel;
    }

    /*
    represent the growth chart of male's head diameter
    */

    private AAChartModel configureHeadDiameterMaleChartModel() {

        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Line)
                .title("היקף הראש")
                .subtitle("היקף בסנטימטרים, עפי מידע מארגון הבריאות העולמי")
                .backgroundColor("#CDDAFD")
                .dataLabelsEnabled(false)
                .categories(new String[]{"שבוע 0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"})
                .yAxisGridLineWidth(0f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("my baby")
                                .color("#000067")
                                .marker(new AAMarker().symbol(AAChartSymbolType.Diamond))
                                .data(headc_list),
                        new AASeriesElement()
                                .name("98%")
                                .size(0.2)
                                .color("#fffe00")
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
                                .color("#fffe00")
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

    /*
    represent the growth chart of fe56male's head diameter
    */

    private AAChartModel configureHeadDiameterFemaleChartModel() {

        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Line)
                .title("היקף הראש")
                .subtitle("היקף בסנטימטרים, עפי מידע מארגון הבריאות העולמי")
                .backgroundColor("#CDDAFD")
                .dataLabelsEnabled(false)
                .categories(new String[]{"שבוע 0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"})
                .yAxisGridLineWidth(0f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("my baby")
                                .color("#000067")
                                .marker(new AAMarker().symbol(AAChartSymbolType.Diamond))
                                .data(headc_list),
                        new AASeriesElement()
                                .name("98%")
                                .size(0.2)
                                .color("#fffe00")
                                .marker(new AAMarker().radius(0f))
                                .data(new Object[]{
                                36.2475,
                                38.8926,
                                40.6758,
                                42.0155,
                                43.1132,
                                44.0311,
                                44.8049,
                                45.463,
                                46.0238,
                                46.5063,
                                46.9247,
                                47.2907,
                                47.6145,
                                47.9029,
                                48.163,
                                48.3999,
                                48.616,
                                48.8169,
                                49.0049,
                                49.1834,
                                49.3535,
                                49.5165,
                                49.6738,
                                49.8257,
                                49.9726

                        }),
                        new AASeriesElement()
                                .name("75%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                34.6776,
                                37.3376,
                                39.0695,
                                40.3701,
                                41.4354,
                                42.3264,
                                43.0782,
                                43.7173,
                                44.2631,
                                44.7326,
                                45.1401,
                                45.4971,
                                45.8131,
                                46.0951,
                                46.3494,
                                46.5808,
                                46.7928,
                                46.9896,
                                47.1741,
                                47.3488,
                                47.5154,
                                47.6753,
                                47.8297,
                                47.9789,
                                48.1232

                        }),
                        new AASeriesElement()
                                .name("50%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                33.8787,
                                36.5463,
                                38.2521,
                                39.5328,
                                40.5817,
                                41.459,
                                42.1995,
                                42.829,
                                43.3671,
                                43.83,
                                44.2319,
                                44.5844,
                                44.8965,
                                45.1752,
                                45.4265,
                                45.6551,
                                45.865,
                                46.0598,
                                46.2424,
                                46.4152,
                                46.5801,
                                46.7384,
                                46.8913,
                                47.0391,
                                47.1822

                        }),
                        new AASeriesElement()
                                .name("25%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                33.0798,
                                35.755,
                                37.4347,
                                38.6955,
                                39.728,
                                40.5916,
                                41.3208,
                                41.9407,
                                42.4712,
                                42.9275,
                                43.3238,
                                43.6717,
                                43.9799,
                                44.2553,
                                44.5036,
                                44.7294,
                                44.9373,
                                45.13,
                                45.3108,
                                45.4816,
                                45.6448,
                                45.8015,
                                45.9529,
                                46.0993,
                                46.2412
                        }),
                        new AASeriesElement()
                                .name("2%")
                                .size(0.2)
                                .marker(new AAMarker().radius(0f))
                                .color("#fffe00")
                                .data(new Object[]{
                                31.5099,
                                34.2,
                                35.8285,
                                37.0501,
                                38.0502,
                                38.8869,
                                39.5941,
                                40.195,
                                40.7104,
                                41.1537,
                                41.5391,
                                41.8781,
                                42.1785,
                                42.4475,
                                42.69,
                                42.9103,
                                43.114,
                                43.3027,
                                43.4799,
                                43.647,
                                43.8067,
                                43.9603,
                                44.1088,
                                44.2525,
                                44.3918

                        })
                });
        this.CurrChartModel = aaChartModel;

        return aaChartModel;
    }

    private String log(String gen) {
        Log.v("score", String.valueOf(gen));
        return gen;
    }

}