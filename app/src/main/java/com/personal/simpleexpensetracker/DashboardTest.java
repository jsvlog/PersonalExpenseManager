package com.personal.simpleexpensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DashboardTest extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    private String[] titles = new String[]{"FUND","EXPENSES","BALANCES"};
    private Integer[] icon = {R.drawable.ic_fund,R.drawable.ic_expenses,R.drawable.ic_balance};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_test);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(DashboardTest.this);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout,viewPager2,(tab, position) -> tab.setText(titles[position]).setIcon(icon[position])).attach();




    }
}