package com.ireport.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ireport.R;

/**
 * Created by Manoj on 12/5/2016.
 */

public class ReportListViewHolder extends RecyclerView.ViewHolder {
    public TextView reportDate,reportStatus;
    public ImageView reportImage;

    public ReportListViewHolder(View itemView) {
        super(itemView);
        reportDate = (TextView) itemView.findViewById(R.id.report_date);
        reportStatus = (TextView) itemView.findViewById(R.id.report_status);
        reportImage = (ImageView) itemView.findViewById(R.id.report_image);
    }
}
