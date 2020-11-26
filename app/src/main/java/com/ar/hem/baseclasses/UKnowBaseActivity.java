package com.ar.hem.baseclasses;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;

import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by U47736 on 6/6/2017.
 */

public abstract class UKnowBaseActivity extends AppCompatActivity {

    private ExpandableListView expandableListViewLeftDrawer;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private MenuItem mMenuItem;

    private FrameLayout frameLayoutContent;

    private String mActivityTitle;
    private int actionBarHeight;
    private boolean isDrawerNeeded;
    private boolean showActionBar;

    private Handler mHandler;
    private Runnable mRunnable;

    private Object data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initViews();
        initListeners();
        initData();

        addDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tasksOnResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tasksOnDestroy();
    }

    public abstract void init();
    public abstract void initViews();
    public abstract void initListeners();
    public abstract void initData();
    public abstract void tasksOnResume();
    public abstract void tasksOnDestroy();
    public abstract void onDrawerItemClick(int pos);

    /**
     * Should be called only from the inherited class's init method.
     * Else chance of null pointer exception
     *
     * @param isNeeded if drawer is needed
     */
    public void setDrawerNeeded(boolean isNeeded) {
        isDrawerNeeded                      = isNeeded;
    }

    public void addDrawer() {
        if (isDrawerNeeded) {
            initializeContentFrame();
            initDrawer();
            setupDrawer();

            if (showActionBar)
                syncDrawerToggle();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (showActionBar)
            syncDrawerToggle();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerToggle != null)
            mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_content, menu);
        //mMenuItem                                   = menu.findItem(R.id.action_profile);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //int id                              = item.getItemId();

        // Activate the navigation drawer toggle
        return mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item);
        //return false;//super.onOptionsItemSelected(item);
    }
    public void setShowActionBar(boolean showActionBar) {
        this.showActionBar                          = showActionBar;
    }

    public void hideActionBar() {
        Log.d("skt", "Hiding action bar--" + new Date());
        if (mToolbar != null) {
            mToolbar.setVisibility(View.GONE);
            if (mDrawerLayout != null) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        }

        if (null != getSupportActionBar()) {
            getSupportActionBar().hide();
        }
    }

    public void showActionBar() {
        Log.d("skt", "Showing action bar--" + new Date());
        mRunnable                               = new Runnable() {
            @Override
            public void run() {
                if (mToolbar != null) {
                    Log.d("skt", "Tool bar not null");
                    if (null == mDrawerToggle) {
                        initDrawer();
                        setupDrawer();
                    }

                    if (null != getSupportActionBar()) {
                        Log.d("skt", "Support action bar setting");
                        setSupportActionBar(mToolbar);
                        getSupportActionBar().show();
                        getSupportActionBar().setDisplayShowHomeEnabled(true);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }

                    mToolbar.setVisibility(View.VISIBLE);
                    if (mDrawerLayout != null) {
                        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    }

                    Log.d("skt","Visibility of action bar = " + getmToolbar().getVisibility());
                }
            }
        };

        if (mHandler == null)
            mHandler                            = new Handler();

        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, 500);
    }

    public void syncDrawerToggle() {
        if (mDrawerToggle != null)
            mDrawerToggle.syncState();
    }

    public void setmToolbar(Toolbar mToolbar) {
        this.mToolbar = mToolbar;
    }

    public Toolbar getmToolbar() {
        return mToolbar;
    }


    public void goToFragment(Fragment fragment, String tag, boolean addToBackStack,
                             boolean clearBackStack, boolean replace) {
        FragmentManager fm                          = getSupportFragmentManager();
        FragmentTransaction ft                      = fm.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }

        if (replace) {
            //ft.replace(R.id.frameLayout_main_content, fragment, tag);
        } else {
            //ft.add(R.id.frameLayout_main_content, fragment, tag);
        }

        if (clearBackStack) {
            for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
        }

        ft.commit();
    }

    public void setData(Object data){
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    private void initializeContentFrame() {

    }

    private void initDrawer() {

    }

    private void setupDrawer() {
        setSupportActionBar(mToolbar);

    }

    private void setUserDetailsInDrawer() {

    }

    private void addDrawerItems() {

    }
}
