package com.ireport.activity.officer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ireport.R;
import com.ireport.activity.user.MainActivity;
import com.ireport.constants.CommonConstants;
import com.ireport.fragment.MyReportList;
import com.ireport.servicesAndGeneralInterface.IntentAndFragmentService;

/**
 * Created by Manoj on 12/5/2016.
 */

public class OfficerMain extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.officer_main_layout);

        /**
         * call the switch method initially
         * */
        FragmentSwitch();
    }

    private void FragmentSwitch() {
        switch (CommonConstants.FragmentSwitchCallMain){
            case CommonConstants.MainFragment:
                IntentAndFragmentService.fragmentdisplay(OfficerMain.this,R.id.officer_frame,new MyReportList(),null,false,false);
                break;
            default:

                break;
        }
    }

}
