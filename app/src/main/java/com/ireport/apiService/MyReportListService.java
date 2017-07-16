package com.ireport.apiService;

import com.ireport.constants.ApiConstants;
import com.ireport.model.responseModel.userModel.reportModel.ReportListMain;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Manoj on 12/5/2016.
 */

public interface MyReportListService {
    @FormUrlEncoded
    @POST(ApiConstants.GET_USER_REPORT_LIST)
    Call<ReportListMain> reportList(@Field("user_id") String user_id);
}
