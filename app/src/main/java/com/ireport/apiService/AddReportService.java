package com.ireport.apiService;

import com.ireport.constants.ApiConstants;
import com.ireport.model.responseModel.userModel.addReport.AddReportMain;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Manoj on 12/5/2016.
 */

public interface AddReportService {
    @Multipart
    @POST(ApiConstants.ADD_REPORT)
    Call<ResponseBody> addReport(@Part("user_id")RequestBody user_is,
                                  @Part("area") RequestBody area,
                                  @Part("longitude") RequestBody longitude,
                                  @Part("latitude") RequestBody latitude,
                                  @Part("address")RequestBody address,
                                  @Part("description") RequestBody description,
                                  @Part("report_date") RequestBody report_date,
                                  @Part("report_time") RequestBody report_time,
                                  @Part("report_size") RequestBody report_size,
                                  @Part("report_severity_level") RequestBody report_level,
                                  @Part("ip_address") RequestBody ip_address,
                                  @Part("report_image1") MultipartBody.Part file1,
                                  @Part("report_image2") MultipartBody.Part file2,
                                  @Part("report_image3") MultipartBody.Part file3,
                                  @Part("report_image4") MultipartBody.Part file4,
                                  @Part("report_image5") MultipartBody.Part file5);
}
