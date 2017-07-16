package com.ireport.model.responseModel.userModel.reportModel.reportResponse;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.ireport.model.responseModel.userModel.reportModel.reportResponse.reportImage.ReportImage;
import com.ireport.model.responseModel.userModel.reportModel.reportResponse.reportStatusTrack.ReportStatusTrack;

import java.util.List;

/**
 * Created by Manoj on 12/5/2016.
 */

@JsonObject
public class ReportListResponse {
    @JsonField(name = "distance")
    private Integer distance;
    @JsonField(name = "report_id")
    private String reportId;
    @JsonField(name = "report_date")
    private String reportDate;
    @JsonField(name = "report_area")
    private String reportArea;
    @JsonField(name = "report_longitude")
    private String reportLongitude;
    @JsonField(name = "report_latitude")
    private String reportLatitude;
    @JsonField(name = "report_address")
    private String reportAddress;
    @JsonField(name = "report_description")
    private String reportDescription;
    @JsonField(name = "report_size")
    private String reportSize;
    @JsonField(name = "report_severity_level")
    private String reportSeverityLevel;
    @JsonField(name = "user_report_status")
    private String userReportStatus;
    @JsonField(name = "user_report_status_msg")
    private String userReportStatusMsg;
    @JsonField(name = "report_created_on")
    private String reportCreatedOn;
    @JsonField(name = "report_images")
    private List<ReportImage> reportImages = null;
    @JsonField(name = "report_status_track")
    private List<ReportStatusTrack> reportStatusTrack = null;

    /**
     *
     * @return
     * The distance
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     *
     * @param distance
     * The distance
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    /**
     *
     * @return
     * The reportId
     */
    public String getReportId() {
        return reportId;
    }

    /**
     *
     * @param reportId
     * The report_id
     */
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    /**
     *
     * @return
     * The reportDate
     */
    public String getReportDate() {
        return reportDate;
    }

    /**
     *
     * @param reportDate
     * The report_date
     */
    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    /**
     *
     * @return
     * The reportArea
     */
    public String getReportArea() {
        return reportArea;
    }

    /**
     *
     * @param reportArea
     * The report_area
     */
    public void setReportArea(String reportArea) {
        this.reportArea = reportArea;
    }

    /**
     *
     * @return
     * The reportLongitude
     */
    public String getReportLongitude() {
        return reportLongitude;
    }

    /**
     *
     * @param reportLongitude
     * The report_longitude
     */
    public void setReportLongitude(String reportLongitude) {
        this.reportLongitude = reportLongitude;
    }

    /**
     *
     * @return
     * The reportLatitude
     */
    public String getReportLatitude() {
        return reportLatitude;
    }

    /**
     *
     * @param reportLatitude
     * The report_latitude
     */
    public void setReportLatitude(String reportLatitude) {
        this.reportLatitude = reportLatitude;
    }

    /**
     *
     * @return
     * The reportAddress
     */
    public String getReportAddress() {
        return reportAddress;
    }

    /**
     *
     * @param reportAddress
     * The report_address
     */
    public void setReportAddress(String reportAddress) {
        this.reportAddress = reportAddress;
    }

    /**
     *
     * @return
     * The reportDescription
     */
    public String getReportDescription() {
        return reportDescription;
    }

    /**
     *
     * @param reportDescription
     * The report_description
     */
    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    /**
     *
     * @return
     * The reportSize
     */
    public String getReportSize() {
        return reportSize;
    }

    /**
     *
     * @param reportSize
     * The report_size
     */
    public void setReportSize(String reportSize) {
        this.reportSize = reportSize;
    }

    /**
     *
     * @return
     * The reportSeverityLevel
     */
    public String getReportSeverityLevel() {
        return reportSeverityLevel;
    }

    /**
     *
     * @param reportSeverityLevel
     * The report_severity_level
     */
    public void setReportSeverityLevel(String reportSeverityLevel) {
        this.reportSeverityLevel = reportSeverityLevel;
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
     * The userReportStatusMsg
     */
    public String getUserReportStatusMsg() {
        return userReportStatusMsg;
    }

    /**
     *
     * @param userReportStatusMsg
     * The user_report_status_msg
     */
    public void setUserReportStatusMsg(String userReportStatusMsg) {
        this.userReportStatusMsg = userReportStatusMsg;
    }

    /**
     *
     * @return
     * The reportCreatedOn
     */
    public String getReportCreatedOn() {
        return reportCreatedOn;
    }

    /**
     *
     * @param reportCreatedOn
     * The report_created_on
     */
    public void setReportCreatedOn(String reportCreatedOn) {
        this.reportCreatedOn = reportCreatedOn;
    }

    /**
     *
     * @return
     * The reportImages
     */
    public List<ReportImage> getReportImages() {
        return reportImages;
    }

    /**
     *
     * @param reportImages
     * The report_images
     */
    public void setReportImages(List<ReportImage> reportImages) {
        this.reportImages = reportImages;
    }

    /**
     *
     * @return
     * The reportStatusTrack
     */
    public List<ReportStatusTrack> getReportStatusTrack() {
        return reportStatusTrack;
    }

    /**
     *
     * @param reportStatusTrack
     * The report_status_track
     */
    public void setReportStatusTrack(List<ReportStatusTrack> reportStatusTrack) {
        this.reportStatusTrack = reportStatusTrack;
    }

}
