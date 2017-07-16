package com.ireport.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manoj on 11/25/2016.
 */

public class CommonConstants {

    /**
     * shared preference constants
     * */
    public static final String sharedPreference = "MyPrefs";

    /**
     * Shared preference user info values
     * */
    public static final String userId = "user_id";
    public static final String userName = "user_name";
    public static final String loginType = "login_type";
    public static final String deviceId = "device_id";
    public static final String emailId = "email_id";
    public static final String screenName = "screen_name";
    public static final String homeAddress = "homeAddress";

    /**
     * Shared preference user settings
     * */
    public static final String emailConfirmation = "email_confirmation";
    public static final String emailNotification = "email_notification";
    public static final String reportAnonymous = "report_anonymous";

    public static String FCMTokenID;

    /**
     * switch value
     * */
    public static String FragmentSwitchCallMain = null;
    public static final String MainFragment = "main_fragment";

    /**
     * get data from latitude and longitude
     * */
    public static String USER_AREA = null;
    public static String USER_LATITUDE = null;
    public static String USER_LONGITUDE = null;
    public static String USER_ADDRESS = null;

    public static List<String> viewPagerImages = new ArrayList<String>();
    public static List<Double> litteringLatitude = new ArrayList<Double>();
    public static List<Double> litteringLongitude = new ArrayList<Double>();
    public static List<String> litteringReportId = new ArrayList<String>();
    public static List<String> litteringStatus = new ArrayList<String>();

    public static String litteringCurrentStatus = null;
    public static String litteringCurrentId = null;
    public static String selectedLitteringLatitude = null;
    public static String selectedLitteringLongitude = null;

}
