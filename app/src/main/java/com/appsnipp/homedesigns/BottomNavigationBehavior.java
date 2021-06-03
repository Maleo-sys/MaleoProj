package com.appsnipp.homedesigns;



import android.content.Context;
//import android.support.design.widget.BottomNavigationView;
//import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {

    public BottomNavigationBehavior() {
        super();
    }

    public BottomNavigationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, BottomNavigationView child, View dependency) {
        boolean dependsOn = dependency instanceof FrameLayout;
        return dependsOn;
    }


}