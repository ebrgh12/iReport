package com.ireport.model.responseModel.userModel.reportModel.reportResponse.reportStatusTrack;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Manoj on 12/5/2016.
 */

@JsonObject
public class ReportStatusTrack {
    @JsonField(name = "user_report_status_updated_by")
    private String userReportStatusUpdatedBy;
    @JsonField(name = "user_report_status")
    private String userReportStatus;
    @JsonField(name = "created_on")
    private String createdOn;

    /**
     *
     * @return
     * The userReportStatusUpdatedBy
     */
    public String getUserReportStatusUpdatedBy() {
        return userReportStatusUpdatedBy;
    }

    /**
     *
     * @param userReportStatusUpdatedBy
     * The user_report_status_updated_by
     */
    public void setUserReportStatusUpdatedBy(String userReportStatusUpdatedBy) {
        this.userReportStatusUpdatedBy = userReportStatusUpdatedBy;
    }

    /**
     *
     * @return
     * The userReportStatus
     */
    public String getUserReportStatus() {
        return userReportStatus;
    }

    /**
     *
     * @param userReportStatus
     * The user_report_status
     */
    public void setUserReportStatus(String userReportStatus) {
        this.userReportStatus = userReportStatus;
    }

    /**
     *
     * @return
     * The createdOn
     */
    public String getCreatedOn() {
        return createdOn;
    }

    /**
     *
     * @param createdOn
     * The created_on
     */
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

}
