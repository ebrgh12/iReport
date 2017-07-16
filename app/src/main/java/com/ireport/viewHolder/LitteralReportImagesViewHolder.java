package com.ireport.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ireport.R;

/**
 * Created by Manoj on 12/6/2016.
 */

public class LitteralReportImagesViewHolder extends RecyclerView.ViewHolder {

    public ImageView litteral_image;

    public LitteralReportImagesViewHolder(View itemView) {
        super(itemView);
        litteral_image = (ImageView) itemView.findViewById(R.id.litteral_image);
    }

}
