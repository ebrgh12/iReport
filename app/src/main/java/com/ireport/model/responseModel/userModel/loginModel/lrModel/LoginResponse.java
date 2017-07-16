package com.ireport.model.responseModel.userModel.loginModel.lrModel;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Manoj on 12/1/2016.
 */

@JsonObject
public class LoginResponse {
    @JsonField(name = "user_id")
    private String userId;
    @JsonField(name = "user_fname")
    private String userFname;
    @JsonField(name = "user_lname")
    private String userLname;
    @JsonField(name = "user_email")
    private String userEmail;
    @JsonField(name = "user_screen_name")
    private String userScreenName;
    @JsonField(name = "user_email_notification")
    private String userEmailNotification;
    @JsonField(name = "user_house_address")
    private String userHouseAddress;

    /**
     *
     * @return
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The userFname
     */
    public String getUserFname() {
        return userFname;
    }

    /**
     *
     * @param userFname
     * The user_fname
     */
    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    /**
     *
     * @return
     * The userLname
     */
    public String getUserLname() {
        return userLname;
    }

    /**
     *
     * @param userLname
     * The user_lname
     */
    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    /**
     *
     * @return
     * The userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     *
     * @param userEmail
     * The user_email
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     *
     * @return
     * The userScreenName
     */
    public String getUserScreenName() {
        return userScreenName;
    }

    /**
     *
     * @param userScreenName
     * The user_screen_name
     */
    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    /**
     *
     * @return
     * The userEmailNotification
     */
    public String getUserEmailNotification() {
        return userEmailNotification;
    }

    /**
     *
     * @param userEmailNotification
     * The user_email_notification
     */
    public void setUserEmailNotification(String userEmailNotification) {
        this.userEmailNotification = userEmailNotification;
    }

    /**
     *
     * @return
     * The userHouseAddress
     */
    public String getUserHouseAddress() {
        return userHouseAddress;
    }

    /**
     *
     * @param userHouseAddress
     * The user_house_address
     */
    public void setUserHouseAddress(String userHouseAddress) {
        this.userHouseAddress = userHouseAddress;
    }

}
