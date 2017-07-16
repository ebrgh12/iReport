package com.ireport.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ireport.constants.CommonConstants;

/**
 * Created by Manoj on 11/25/2016.
 */

public class SharedPreferencesUtil {

    private static SharedPreferencesUtil sInstance;
    private static SharedPreferences mPrefs;

    public static SharedPreferencesUtil getInstance(Context context) {
        if (sInstance == null) {
            sInstance =  new SharedPreferencesUtil(context);
        }
        return sInstance;
    }

    private SharedPreferencesUtil(Context context) {
        mPrefs = context.getSharedPreferences(CommonConstants.sharedPreference, 0);
    }

    /**
     * set user preference data
     * */
    public void setUserPreferenceData(String userId,String userName,String loginType,
                                      String deviceId,String emailId,String screenName,
                                      String houseAddress){

        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(CommonConstants.userId,userId);
        editor.putString(CommonConstants.userName,userName);
        editor.putString(CommonConstants.loginType,loginType);
        editor.putString(CommonConstants.deviceId,deviceId);
        editor.putString(CommonConstants.emailId,emailId);
        editor.putString(CommonConstants.screenName,screenName);
        editor.putString(CommonConstants.homeAddress,houseAddress);
        editor.commit();
    }

    public void setUserSettings(String emailConfirmation,String emailNotification,
                                String reportAnonymously){

        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(CommonConstants.emailConfirmation,emailConfirmation);
        editor.putString(CommonConstants.emailNotification,emailNotification);
        editor.putString(CommonConstants.reportAnonymous,reportAnonymously);
        editor.commit();
    }

    public String getUserId(){
        return mPrefs.getString(CommonConstants.userId,null);
    }

    public String getUserName(){
        return mPrefs.getString(CommonConstants.userName,null);
    }

    public String getloginType(){
        return mPrefs.getString(CommonConstants.loginType,null);
    }

    public String getdeviceId(){
        return mPrefs.getString(CommonConstants.deviceId,null);
    }

    public String getemailId(){
        return mPrefs.getString(CommonConstants.emailId,null);
    }

    public String getscreenName(){
        return mPrefs.getString(CommonConstants.screenName,null);
    }

    public String gethomeAddress(){
        return mPrefs.getString(CommonConstants.homeAddress,null);
    }

    public String getemailConfirmation(){
        return mPrefs.getString(CommonConstants.emailConfirmation,null);
    }

    public String getemailNotification(){
        return mPrefs.getString(CommonConstants.emailNotification,null);
    }

    public String getreportAnonymous(){
        return mPrefs.getString(CommonConstants.reportAnonymous,null);
    }

}
