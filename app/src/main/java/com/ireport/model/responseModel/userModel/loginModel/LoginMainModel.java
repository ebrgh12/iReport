package com.ireport.model.responseModel.userModel.loginModel;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.ireport.model.responseModel.userModel.loginModel.lrModel.LoginResponse;

/**
 * Created by Manoj on 12/1/2016.
 */

@JsonObject
public class LoginMainModel {
    @JsonField(name = "status")
    private String status;
    @JsonField(name = "response")
    private LoginResponse response;
    @JsonField(name = "message")
    private String message;

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The response
     */
    public LoginResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(LoginResponse response) {
        this.response = response;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
