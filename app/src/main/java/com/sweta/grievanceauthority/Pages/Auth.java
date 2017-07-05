package com.sweta.grievanceauthority.Pages;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.sweta.grievanceauthority.R;
import com.sweta.grievanceauthority.adapter.AuthScreenViewPagerAdapter;

/**
 * Created by 1406074 on 02-06-2017.
 */

public class Auth extends FragmentActivity {

    public static ViewPager viewPager;
    private AuthScreenViewPagerAdapter adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        adaptor = new AuthScreenViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adaptor);
    }
}