package com.ireport.model.responseModel.userModel.reportModel;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.ireport.model.responseModel.userModel.reportModel.reportResponse.ReportListResponse;

import java.util.List;

/**
 * Created by Manoj on 12/5/2016.
 */

@JsonObject
public class ReportListMain {
    @JsonField(name = "status")
    private String status;
    @JsonField(name = "image_path")
    private String imagePath;
    @JsonField(name = "response")
    private List<ReportListResponse> response = null;

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
     * The imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     *
     * @param imagePath
     * The image_path
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     *
     * @return
     * The response
     */
    public List<ReportListResponse> getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(List<ReportListResponse> response) {
        this.response = response;
    }

}
