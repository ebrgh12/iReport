package com.ireport.apiService;

import com.ireport.constants.ApiConstants;
import com.ireport.model.responseModel.userModel.updateUserProfile.UpdateUserMain;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Manoj on 12/4/2016.
 */

public interface UpdateProfile {

    @FormUrlEncoded
    @POST(ApiConstants.UPDATE_USER_PROFILE)
    Call<UpdateUserMain> updateUserProfile(@Field("user_id") String user_id,
                                           @Field("fname") String fname,
                                           @Field("lname") String lname,
                                           @Field("email") String email,
                                           @Field("screen_name") String screenName,
                                           @Field("house_address") String houseAddress,
                                           @Field("contact_no") String contactNo,
                                           @Field("email_notification_littering_repost") String email_notification_littering_repost,
                                           @Field("email_notification_status_change") String email_notification_status_change,
                                           @Field("report_anonymously") String report_anonymously);
}
