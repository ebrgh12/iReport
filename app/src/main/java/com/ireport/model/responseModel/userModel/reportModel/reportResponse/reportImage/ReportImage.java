package com.ireport.model.responseModel.userModel.reportModel.reportResponse.reportImage;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Manoj on 12/5/2016.
 */

@JsonObject
public class ReportImage {
    @JsonField(name = "image_id")
    private String imageId;
    @JsonField(name = "image")
    private String image;

    /**
     *
     * @return
     * The imageId
     */
    public String getImageId() {
        return imageId;
    }

    /**
     *
     * @param imageId
     * The image_id
     */
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

}
