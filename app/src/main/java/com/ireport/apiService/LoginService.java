package com.ireport.apiService;

import com.ireport.constants.ApiConstants;
import com.ireport.model.responseModel.userModel.loginModel.LoginMainModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Girish on 12/1/2016.
 */

public interface LoginService {

    @FormUrlEncoded
    @POST(ApiConstants.LOGIN_ACTIVITY)
    Call<LoginMainModel> loginActivity(@Field("fname") String firstName,
                                       @Field("lname") String lastName,
                                       @Field("email") String email,
                                       @Field("screen_name") String screen_name,
                                       @Field("account_type") String account_type,
                                       @Field("social_account") String social_account,
                                       @Field("house_address") String house_address,
                                       @Field("device_id") String device_id,
                                       @Field("device_type") String device_type);
}
