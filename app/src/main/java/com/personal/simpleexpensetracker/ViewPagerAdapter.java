package com.personal.simpleexpensetracker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private String[] titles = new String[]{"FUND","EXPENSES","BALANCES"};

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FundFragment();
            case 1:
                return new ExpensesFragment();
            case 3:
                return new BalanceFragment();
        }
        return new FundFragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
