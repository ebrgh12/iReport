package com.ireport.apiService;

import com.ireport.constants.ApiConstants;
import com.ireport.model.responseModel.userModel.updateUserProfile.UpdateUserMain;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Manoj on 12/8/2016.
 */

public interface UpdateReportStatus {
    @FormUrlEncoded
    @POST(ApiConstants.UPDATE_USER_STATUS)
    Call<UpdateUserMain> updateStatus(@Field("user_id") String user_id,
                                      @Field("report_id") String report_id,
                                      @Field("user_report_status") String user_report_status,
                                      @Field("report_updated_by") String report_updated_by);
}
