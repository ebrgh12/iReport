package com.ireport.activity.user;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ireport.R;
import com.ireport.constants.CommonConstants;
import com.ireport.fragment.FileReport;
import com.ireport.fragment.MyReportList;
import com.ireport.fragment.MyProfile;
import com.ireport.servicesAndGeneralInterface.IntentAndFragmentService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * main frame layout id main_frame
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /**
         * call the switch method initially
         * */
        FragmentSwitch();
    }

    private void FragmentSwitch() {
        switch (CommonConstants.FragmentSwitchCallMain){
            case CommonConstants.MainFragment:
                IntentAndFragmentService.fragmentdisplay(MainActivity.this,R.id.main_frame,new MyReportList(),null,false,false);
                break;
            default:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_profile) {
            IntentAndFragmentService.fragmentdisplay(MainActivity.this,R.id.main_frame,new MyProfile(),null,false,false);
        } else if (id == R.id.file_report) {
            IntentAndFragmentService.fragmentdisplay(MainActivity.this,R.id.main_frame,new FileReport(),null,false,false);
        } else if (id == R.id.my_reports) {
            IntentAndFragmentService.fragmentdisplay(MainActivity.this,R.id.main_frame,new MyReportList(),null,false,false);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
