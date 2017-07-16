package com.ireport.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ireport.R;
import com.ireport.model.responseModel.userModel.reportModel.ReportListMain;
import com.ireport.utils.CustomItemClickListener;
import com.ireport.viewHolder.ReportListViewHolder;
import com.squareup.picasso.Picasso;

import retrofit2.Response;

/**
 * Created by Manoj on 12/5/2016.
 */

public class ReportListUserAdapter extends RecyclerView.Adapter<ReportListViewHolder> {
    CustomItemClickListener customItemClickListener;
    Activity activity;
    Response<ReportListMain> response;

    public ReportListUserAdapter(Activity activity,
                                 Response<ReportListMain> response,
                                 CustomItemClickListener customItemClickListener) {
        this.activity = activity;
        this.response = response;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public ReportListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_list_item, parent, false);
        final ReportListViewHolder reportListViewHolder = new ReportListViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customItemClickListener.onItemClick(v, reportListViewHolder.getPosition());
            }
        });
        return reportListViewHolder;
    }

    @Override
    public void onBindViewHolder(ReportListViewHolder holder, int position) {
        try {
            Picasso.with(activity)
                    .load(response.body().getImagePath()+response.body().getResponse().get(position).getReportImages().get(0).getImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.reportImage);

        }catch (Exception e){

        }

        holder.reportDate.setText(response.body().getResponse().get(position).getReportCreatedOn().substring(0,11));
        if(response.body().getResponse().get(position).getReportStatusTrack().get(0).getUserReportStatus().equals("0")){
            holder.reportStatus.setText("Still There");
        }else if(response.body().getResponse().get(position).getReportStatusTrack().get(0).getUserReportStatus().equals("1")){
            holder.reportStatus.setText("Removal Confirmaed");
        }else {
            holder.reportStatus.setText("Removal Claimed");
        }
    }

    @Override
    public int getItemCount() {
        return response.body().getResponse().size();
    }
}
