package com.ireport.apiService;

import com.ireport.constants.ApiConstants;
import com.ireport.model.responseModel.userModel.reportModel.ReportListMain;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Manoj on 12/7/2016.
 */

public interface GetOfficersRepotService {
    @FormUrlEncoded
    @POST(ApiConstants.GET_OFFICERS_REPORT_LIST)
    Call<ReportListMain> reportList(@Field("officer_id") String user_id);
}
