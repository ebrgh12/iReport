package com.ireport.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.ireport.R;
import com.ireport.activity.DetailScreenActivity;
import com.ireport.activity.user.MainActivity;
import com.ireport.adapter.ReportListUserAdapter;
import com.ireport.apiService.GetOfficersRepotService;
import com.ireport.apiService.MyReportListService;
import com.ireport.constants.ApiConstants;
import com.ireport.constants.CommonConstants;
import com.ireport.model.responseModel.userModel.reportModel.ReportListMain;
import com.ireport.servicesAndGeneralInterface.IntentAndFragmentService;
import com.ireport.utils.CheckInternet;
import com.ireport.utils.CustomItemClickListener;
import com.ireport.utils.ProgressLoader;
import com.ireport.utils.SharedPreferencesUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.ireport.constants.CommonConstants.litteringCurrentId;
import static com.ireport.constants.CommonConstants.litteringCurrentStatus;
import static com.ireport.constants.CommonConstants.litteringLatitude;
import static com.ireport.constants.CommonConstants.litteringLongitude;
import static com.ireport.constants.CommonConstants.litteringReportId;
import static com.ireport.constants.CommonConstants.litteringStatus;
import static com.ireport.constants.CommonConstants.selectedLitteringLatitude;
import static com.ireport.constants.CommonConstants.selectedLitteringLongitude;


/**
 * Created by Manoj on 11/23/2016.
 */

public class MyReportList extends Fragment implements View.OnClickListener{
    View view;
    LinearLayout no_data_layout;
    Button add_report;
    RecyclerView my_report_list;

    MyReportListService myReportListService;
    GetOfficersRepotService getOfficersRepotService;

    ProgressLoader progressLoader;
    CheckInternet checkInternet;

    ReportListUserAdapter reportListUserAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * retrofit initialization and also the service
         * interface of retrofit
         * */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .build();
        myReportListService = retrofit.create(MyReportListService.class);
        getOfficersRepotService = retrofit.create(GetOfficersRepotService.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.my_filings_activity,container,false);

        progressLoader = new ProgressLoader();
        checkInternet = new CheckInternet(getActivity());

        no_data_layout = (LinearLayout) view.findViewById(R.id.add_report_layout);
        add_report = (Button) view.findViewById(R.id.add_report);
        add_report.setOnClickListener(this);

        my_report_list = (RecyclerView) view.findViewById(R.id.my_filings);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        my_report_list.setLayoutManager(linearLayoutManager);

        if(checkInternet.isConnectingToInternet()){
            if(SharedPreferencesUtil.getInstance(getActivity()).getloginType().equals("1")){
                GetMyReport();
            }else {
                GetOfficersReport();
            }
        }else {
            Toast.makeText(getActivity(), "Please connecto to the internet to proceed", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void GetOfficersReport() {
        progressLoader.showProgress(getActivity());

        Call<ReportListMain> getOfficerList = getOfficersRepotService.reportList(SharedPreferencesUtil.getInstance(getActivity()).getUserId());
        getOfficerList.enqueue(new Callback<ReportListMain>() {
            @Override
            public void onResponse(Call<ReportListMain> call, final Response<ReportListMain> response) {
                progressLoader.DismissProgress();
                if(response.isSuccess()){
                    if(response.body().getStatus().equals("success")){
                        if(response.body().getResponse().size() != 0){
                            my_report_list.setVisibility(View.VISIBLE);
                            no_data_layout.setVisibility(View.GONE);

                            reportListUserAdapter = new ReportListUserAdapter(getActivity(),response, new CustomItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    progressLoader.showProgress(getActivity());
                                    /**
                                     * send list of images to load images to view pager
                                     * */
                                    for(int i=0;i<response.body().getResponse().get(position).getReportImages().size();i++){
                                        CommonConstants.viewPagerImages.add(response.body().getImagePath()
                                                +response.body().getResponse().get(position).getReportImages().get(i).getImage());
                                    }

                                    litteringCurrentStatus = response.body().getResponse().get(position).getUserReportStatus();
                                    selectedLitteringLatitude = response.body().getResponse().get(position).getReportLatitude();
                                    selectedLitteringLongitude = response.body().getResponse().get(position).getReportLongitude();
                                    litteringCurrentId = response.body().getResponse().get(position).getReportId();

                                    for(int j=0;j<response.body().getResponse().size();j++){
                                        if(response.body().getResponse().get(position).equals(response.body().getResponse().get(j).getReportArea())){
                                            if(response.body().getResponse().get(position).getReportId().
                                                    equals(response.body().getResponse().get(j).getReportId())){
                                                /**
                                                 * don't add the data to list
                                                 * */
                                            }else {
                                                litteringLatitude.add(Double.valueOf(response.body().getResponse().get(j).getReportLatitude()));
                                                litteringLongitude.add(Double.valueOf(response.body().getResponse().get(j).getReportLongitude()));
                                                litteringReportId.add(response.body().getResponse().get(j).getReportId());
                                                litteringStatus.add(response.body().getResponse().get(j).getUserReportStatus());
                                            }
                                        }
                                    }

                                    progressLoader.DismissProgress();
                                    IntentAndFragmentService.intentDisplay(getActivity(), DetailScreenActivity.class,null);
                                }
                            });
                            my_report_list.setAdapter(reportListUserAdapter);
                        }
                    }else {

                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<ReportListMain> call, Throwable t) {
                progressLoader.DismissProgress();
            }
        });
    }

    private void GetMyReport() {
        progressLoader.showProgress(getActivity());

        Call<ReportListMain> userMyReport = myReportListService.reportList(SharedPreferencesUtil.getInstance(getActivity()).getUserId());
        userMyReport.enqueue(new Callback<ReportListMain>() {
            @Override
            public void onResponse(Call<ReportListMain> call, final Response<ReportListMain> response) {
                progressLoader.DismissProgress();
                if(response.isSuccess()){
                    if(response.body().getStatus().equals("success")){
                        if(response.body().getResponse().size() != 0){
                            my_report_list.setVisibility(View.VISIBLE);
                            no_data_layout.setVisibility(View.GONE);

                            reportListUserAdapter = new ReportListUserAdapter(getActivity(),response, new CustomItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    progressLoader.showProgress(getActivity());
                                    /**
                                     * send list of images to load images to view pager
                                     * */
                                    for(int i=0;i<response.body().getResponse().get(position).getReportImages().size();i++){
                                        CommonConstants.viewPagerImages.add(response.body().getImagePath()
                                                +response.body().getResponse().get(position).getReportImages().get(i).getImage());
                                    }

                                    litteringCurrentStatus = response.body().getResponse().get(position).getUserReportStatus();
                                    selectedLitteringLatitude = response.body().getResponse().get(position).getReportLatitude();
                                    selectedLitteringLongitude = response.body().getResponse().get(position).getReportLongitude();
                                    litteringCurrentId = response.body().getResponse().get(position).getReportId();

                                    for(int j=0;j<response.body().getResponse().size();j++){
                                        if(response.body().getResponse().get(position).equals(response.body().getResponse().get(j).getReportArea())){
                                            if(response.body().getResponse().get(position).getReportId().
                                                    equals(response.body().getResponse().get(j).getReportId())){
                                                /**
                                                 * don't add the data to list
                                                 * */
                                            }else {
                                                litteringLatitude.add(Double.valueOf(response.body().getResponse().get(j).getReportLatitude()));
                                                litteringLongitude.add(Double.valueOf(response.body().getResponse().get(j).getReportLongitude()));
                                                litteringReportId.add(response.body().getResponse().get(j).getReportId());
                                                litteringStatus.add(response.body().getResponse().get(j).getUserReportStatus());
                                            }
                                        }
                                    }

                                    progressLoader.DismissProgress();
                                    IntentAndFragmentService.intentDisplay(getActivity(), DetailScreenActivity.class,null);
                                }
                            });
                            my_report_list.setAdapter(reportListUserAdapter);
                        }else {
                            my_report_list.setVisibility(View.GONE);
                            no_data_layout.setVisibility(View.VISIBLE);
                        }
                    }else {

                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<ReportListMain> call, Throwable t) {
                progressLoader.DismissProgress();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_report:
                IntentAndFragmentService.fragmentdisplay(getActivity(),R.id.main_frame,new FileReport(),null,false,false);
                break;
        }
    }
}
