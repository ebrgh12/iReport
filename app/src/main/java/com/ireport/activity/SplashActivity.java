package com.ireport.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ireport.R;
import com.ireport.activity.officer.OfficerMain;
import com.ireport.activity.user.MainActivity;
import com.ireport.constants.CommonConstants;
import com.ireport.servicesAndGeneralInterface.IntentAndFragmentService;
import com.ireport.utils.SharedPreferencesUtil;

/**
 * Created by Manoj on 11/26/2016.
 */

public class SplashActivity extends AppCompatActivity {

    String[] perms = {
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"
    };

    int permsRequestCode = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        /**
         * call permission check method
         * */
        CheckPermission();

    }

    @SuppressLint("NewApi")
    private void CheckPermission() {
        /**
         *  check the runtime permission once accepted,
         *  then only allow the user to enter the next screen
         *  */
        if (ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.CAMERA) !=
                        PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED )  {
            requestPermissions(perms, permsRequestCode);
        }else {
            NextAction();
        }
    }

    /**
     * navigate to next activity based on login type
     *
     * 1. google login officer ( 2 officer )
     *
     * 2. facebook login ( 1 user )
     * */

    private void NextAction() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SharedPreferencesUtil.getInstance(SplashActivity.this).getUserId() != null){

                    if(SharedPreferencesUtil.getInstance(SplashActivity.this).getloginType().equals("1")){
                        /**
                         * move to main activity
                         * */
                        CommonConstants.FragmentSwitchCallMain = CommonConstants.MainFragment;
                        IntentAndFragmentService.intentDisplay(SplashActivity.this,MainActivity.class,null);
                        finish();
                    }else {
                        /**
                         * move to main activity
                         * */
                        CommonConstants.FragmentSwitchCallMain = CommonConstants.MainFragment;
                        IntentAndFragmentService.intentDisplay(SplashActivity.this,OfficerMain.class,null);
                        finish();
                    }
                }else {
                    /**
                     * move to login activity
                     * */
                    IntentAndFragmentService.intentDisplay(SplashActivity.this,LoginActivity.class,null);
                    finish();
                }
            }
        },3000);
    }

    /* runtime permission */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(permsRequestCode){
            case 200:
                boolean access_fine_location = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean access_core_location = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                boolean camera = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                boolean storage_write = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                boolean storage_read = grantResults[4] == PackageManager.PERMISSION_GRANTED;

                /**
                 * check the permission check result if all permission granted,
                 * then only allow the user to navigate to next screen */
                if(access_fine_location == true && access_core_location == true && camera == true &&
                        storage_write == true && storage_read ==  true){

                    NextAction();

                }else {
                    if(access_fine_location == false){
                        //Toast.makeText(SplashActivity.this,"Please enable the fine location",Toast.LENGTH_LONG).show();
                        /**
                         * call permission check method
                         * */
                        CheckPermission();
                    }else if(access_core_location == false){
                        //Toast.makeText(SplashActivity.this,"Please enable the core location", Toast.LENGTH_LONG).show();
                        /**
                         * call permission check method
                         * */
                        CheckPermission();
                    }else if(camera == false){
                        //Toast.makeText(SplashActivity.this,"Please enable camera", Toast.LENGTH_LONG).show();
                        /**
                         * call permission check method
                         * */
                        CheckPermission();
                    }else if(storage_write == false){
                        //Toast.makeText(SplashActivity.this,"Please enable the Storage permission to proceed",Toast.LENGTH_LONG).show();
                        /**
                         * call permission check method
                         * */
                        CheckPermission();
                    }else if(storage_read == false){
                        //Toast.makeText(SplashActivity.this,"Please enable the Storage permission to proceed", Toast.LENGTH_LONG).show();
                        /**
                         * call permission check method
                         * */
                        CheckPermission();
                    }
                }
                break;
        }
    }
    private boolean canMakeSmores(){
        return(Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    private boolean hasPermission(String permission){
        if(canMakeSmores()){
            return(checkSelfPermission(permission)==PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }
    public int checkSelfPermission(String permission) {
        return 1;
    }

}
