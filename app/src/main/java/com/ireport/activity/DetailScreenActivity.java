package com.ireport.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ireport.R;
import com.ireport.adapter.ImagePagerAdapter;
import com.ireport.apiService.UpdateReportStatus;
import com.ireport.constants.ApiConstants;
import com.ireport.constants.CommonConstants;
import com.ireport.model.responseModel.userModel.updateUserProfile.UpdateUserMain;
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
import static com.ireport.constants.CommonConstants.viewPagerImages;

/**
 * Created by Manoj on 12/6/2016.
 */

public class DetailScreenActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,View.OnClickListener{
    ViewPager littering_image_view;
    TextView current_status;
    Spinner report_status;
    // Google Map
    private GoogleMap googleMap;
    private Marker userMarker,otherLitteringMarker;
    ImagePagerAdapter imagePagerAdapter;
    Button updateStatus;

    UpdateReportStatus updateReportStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_detail_screen);

        /**
         * retrofit initialization and also the service
         * interface of retrofit
         * */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .build();
        updateReportStatus = retrofit.create(UpdateReportStatus.class);

        littering_image_view = (ViewPager) findViewById(R.id.littering_image_view);
        current_status = (TextView) findViewById(R.id.current_status);
        report_status = (Spinner) findViewById(R.id.report_status);
        updateStatus = (Button) findViewById(R.id.update_status);
        updateStatus.setOnClickListener(this);

        imagePagerAdapter = new ImagePagerAdapter(DetailScreenActivity.this, viewPagerImages);
        littering_image_view.setAdapter(imagePagerAdapter);

        if(litteringCurrentStatus.equals("0")){
            current_status.setText("Still There");
        }else if(litteringCurrentStatus.equals("1")){
            current_status.setText("Removal Confirmed");
        }else {
            current_status.setText("Removal Claimed");
        }

        try {
            // Loading map
            initilizeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    @Override
    public void onMapReady(GoogleMap myGoogleMap) {
        googleMap = myGoogleMap;
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);

        LatLng userLocation = new LatLng(Double.valueOf(selectedLitteringLatitude), Double.valueOf(selectedLitteringLongitude));
        userMarker = googleMap.addMarker(new MarkerOptions()
                .position(userLocation)
                .title(SharedPreferencesUtil.getInstance(DetailScreenActivity.this).getUserName())
                .snippet("")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

        for(int i=0;i<litteringLatitude.size();i++){
            LatLng otherPlace = new LatLng(Double.valueOf(litteringLatitude.get(i)),
                    Double.valueOf(litteringLongitude.get(i)));

            otherLitteringMarker = googleMap.addMarker(new MarkerOptions()
                    .position(otherPlace)
                    .title(SharedPreferencesUtil.getInstance(DetailScreenActivity.this).getUserName())
                    .snippet("")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }


        userMarker.showInfoWindow();

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(userLocation) // Sets the center of the map to Mountain View
                .zoom(13)             // Sets the zoom
                .bearing(90)          // Sets the orientation of the camera to east
                .tilt(30)             // Sets the tilt of the camera to 30 degrees
                .build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewPagerImages.clear();
        litteringLatitude.clear();
        litteringLongitude.clear();
        litteringReportId.clear();
        litteringStatus.clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update_status:
                /**
                 * change the littering status
                 *
                 *
                 *
                 * */
                String updateValue = null, updatedBy = null;
                if(report_status.getSelectedItem().toString().equals("Still There")){
                    updateValue = "0";
                }else if(report_status.getSelectedItem().toString().equals("Removal Confirmed")){
                    updateValue = "1";
                }else if(report_status.getSelectedItem().toString().equals("Removal Claimed")){
                    updateValue = "2";
                }

                if(SharedPreferencesUtil.getInstance(DetailScreenActivity.this).getloginType().equals("1")){
                    updatedBy = "1";
                }else {
                    updatedBy = "2";
                }

                updateStatusData(updateValue,updatedBy);
                break;
        }
    }

    private void updateStatusData(String updateValue, String updatedBy) {

        Call<UpdateUserMain> updateReoprtStatus = updateReportStatus.updateStatus(
                SharedPreferencesUtil.getInstance(DetailScreenActivity.this).getUserId(),
                litteringCurrentId,
                updateValue,
                updatedBy);

        updateReoprtStatus.enqueue(new Callback<UpdateUserMain>() {
            @Override
            public void onResponse(Call<UpdateUserMain> call, Response<UpdateUserMain> response) {
                if(response.isSuccess()){
                    if(response.body().getStatus().equals("success")){
                        Toast.makeText(DetailScreenActivity.this,"Status Updated Successfully",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateUserMain> call, Throwable t) {

            }
        });
    }
}
