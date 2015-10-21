package com.ucg.desk;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ucg.desk.fragment.ChooseDeskFragment;
import com.ucg.desk.fragment.ChooseHeroFragment;

/**
 * Created by sone on 26.02.14.
 */
public class ChooseDeskActivity extends Activity implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) View.inflate(this, R.layout.ac_choice, null);
        setContentView(mPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(this);

        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab = getActionBar().newTab();
        tab.setText("Новая колода").setTabListener(this);
        getActionBar().addTab(tab);
        tab = getActionBar().newTab();
        tab.setText("Мои колоды").setTabListener(this);
        getActionBar().addTab(tab);
    }

    @Override
    public void onTabSelected(final ActionBar.Tab tab, final FragmentTransaction ft) {
        mPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(final ActionBar.Tab tab, final FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(final ActionBar.Tab tab, final FragmentTransaction ft) {

    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(final int position) {
        getActionBar().getTabAt(position).select();
    }

    @Override
    public void onPageScrollStateChanged(final int state) {

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private String[] mFragments = new String[]{
                ChooseHeroFragment.class.getName(),
                ChooseDeskFragment.class.getName()
        };

        public ScreenSlidePagerAdapter(final FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(final int position) {
            return Fragment.instantiate(ChooseDeskActivity.this, mFragments[position]);
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }
    }

}
