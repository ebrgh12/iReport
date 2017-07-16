package com.ireport.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.ireport.R;
import com.ireport.activity.user.MainActivity;
import com.ireport.apiService.UpdateProfile;
import com.ireport.constants.ApiConstants;
import com.ireport.model.responseModel.userModel.updateUserProfile.UpdateUserMain;
import com.ireport.servicesAndGeneralInterface.IntentAndFragmentService;
import com.ireport.utils.CheckInternet;
import com.ireport.utils.ProgressLoader;
import com.ireport.utils.SharedPreferencesUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Manoj on 11/23/2016.
 */

public class MyProfile extends Fragment implements View.OnClickListener{

    View view;
    Button saveData;
    EditText screenName,firstName,lastName,houseText;
    Switch emailConfirmation,emailNotification,anonymousReport;

    String emailConfirmationSwitch,emailNotificationSwitch,anonymousReportSwitch,
            emailText,screenNameText,firstNameText,lastNameText,contactNumber = "0",
            houseAddress;

    ProgressLoader progressLoader;
    CheckInternet checkInternet;

    /**
     * service name
     * */
    UpdateProfile updateProfile;

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
        updateProfile = retrofit.create(UpdateProfile.class);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_profile,container,false);

        progressLoader = new ProgressLoader();
        checkInternet = new CheckInternet(getActivity());

        saveData = (Button) view.findViewById(R.id.save_user_data);

        screenName = (EditText) view.findViewById(R.id.screen_name);
        firstName = (EditText) view.findViewById(R.id.first_name);
        lastName = (EditText) view.findViewById(R.id.last_name);
        houseText = (EditText) view.findViewById(R.id.house_address);

        emailConfirmation = (Switch) view.findViewById(R.id.email_confirmation);
        emailNotification = (Switch) view.findViewById(R.id.email_notification);
        anonymousReport = (Switch) view.findViewById(R.id.anonymous);

        emailConfirmation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    emailConfirmationSwitch = "true";
                }else {
                    emailConfirmationSwitch = "fasle";
                }
            }
        });

        emailNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    emailNotificationSwitch = "true";
                }else {
                    emailNotificationSwitch = "false";
                }
            }
        });

        anonymousReport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    anonymousReportSwitch = "true";
                }else {
                    anonymousReportSwitch = "false";
                }
            }
        });

        saveData.setOnClickListener(this);

        emailConfirmationSwitch = SharedPreferencesUtil.getInstance(getActivity()).getemailConfirmation();
        emailNotificationSwitch = SharedPreferencesUtil.getInstance(getActivity()).getemailNotification();
        anonymousReportSwitch = SharedPreferencesUtil.getInstance(getActivity()).getreportAnonymous();

        if(emailConfirmationSwitch.equals("true")){
            emailConfirmation.setChecked(true);
        }else {
            emailConfirmation.setChecked(false);
        }

        if(emailNotificationSwitch.equals("true")){
            emailNotification.setChecked(true);
        }else {
            emailNotification.setChecked(false);
        }

        if(anonymousReportSwitch.equals("true")){
            anonymousReport.setChecked(true);
        }else {
            anonymousReport.setChecked(false);
        }

        PrefillSharedPriffData();

        return view;
    }

    /**
     * pre fill the data*/
    private void PrefillSharedPriffData() {
        emailText = SharedPreferencesUtil.getInstance(getActivity()).getemailId();
        screenNameText = SharedPreferencesUtil.getInstance(getActivity()).getscreenName();
        screenName.setText(SharedPreferencesUtil.getInstance(getActivity()).getscreenName());

        if(SharedPreferencesUtil.getInstance(getActivity()).getUserName() != null){
            String[] userName = SharedPreferencesUtil.getInstance(getActivity()).getUserName().split(" ");
            firstNameText = userName[0];
            lastNameText = userName[1];

            firstName.setText(userName[0]);
            if(userName[1].equals("null")){
                /**
                 * don't add the data to edit text
                 * */
            }else {
                lastName.setText(userName[1]);
            }

        }else {
            firstNameText = "null";
            lastNameText = "null";
        }

        if(SharedPreferencesUtil.getInstance(getActivity()).gethomeAddress() != null){
            houseAddress = SharedPreferencesUtil.getInstance(getActivity()).gethomeAddress();
            if(SharedPreferencesUtil.getInstance(getActivity()).gethomeAddress().equals("null")){
                /**
                 * don't add the data to edit text
                 * */
            }else {
                houseText.setText(SharedPreferencesUtil.getInstance(getActivity()).gethomeAddress());
            }

        }else {
            houseAddress = "null";
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_user_data:
                if(checkInternet.isConnectingToInternet()){
                    UpdateUserData();
                }else {
                    Toast.makeText(getActivity(), "Please turn on your internet connection", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void UpdateUserData() {
        if(screenName.getText().toString() != null && !screenName.getText().toString().isEmpty()){

            progressLoader.showProgress(getActivity());
            String emailNot,emailSwitch,anonymous;

            /**
             * update the data to server
             * */
            if(anonymousReportSwitch.equals("true")){
                /**
                 * if this switch is false the other two will be false
                 * */
                SharedPreferencesUtil.getInstance(getActivity()).setUserSettings("false","false","true");
                emailNot = "0";
                emailSwitch = "0";
                anonymous = "1";
            }else {

                String switch1 = null,switch2 = null;
                if(emailConfirmationSwitch.equals("true")){
                    switch1 = "true";
                    emailNot = "1";
                }else {
                    switch1 = "false";
                    emailNot = "0";
                }

                if(emailNotificationSwitch.equals("true")){
                    switch2 = "true";
                    emailSwitch = "1";
                }else {
                    switch2 = "false";
                    emailSwitch = "0";
                }

                SharedPreferencesUtil.getInstance(getActivity()).setUserSettings(switch1
                        ,switch2,"false");

                anonymous = "0";
            }

            if(firstName.getText().toString() != null && !firstName.getText().toString().isEmpty()){
                firstNameText = firstName.getText().toString();
            }else {
                firstNameText = "null";
            }

            if(lastName.getText().toString() != null && !lastName.getText().toString().isEmpty()){
                lastNameText = lastName.getText().toString();
            }else {
                lastNameText = "null";
            }

            if(houseText.getText().toString() != null && !houseText.getText().toString().isEmpty()){
                houseAddress = houseText.getText().toString();
            }else {
                houseAddress = "null";
            }

            screenNameText = screenName.getText().toString();

            Call<UpdateUserMain> updateProfileCall = updateProfile.updateUserProfile(
                    SharedPreferencesUtil.getInstance(getActivity()).getUserId(),
                    firstNameText,
                    lastNameText,
                    emailText,
                    screenNameText,
                    houseAddress,
                    contactNumber,
                    emailNot,
                    emailSwitch,
                    anonymous);
            updateProfileCall.enqueue(new Callback<UpdateUserMain>() {
                @Override
                public void onResponse(Call<UpdateUserMain> call, Response<UpdateUserMain> response) {
                    progressLoader.DismissProgress();

                    if(response.isSuccess()){
                        if(response.body().getStatus().equals("success")){
                            Toast.makeText(getActivity(), "User Profile Updated Succesfully", Toast.LENGTH_SHORT).show();

                            SharedPreferencesUtil.getInstance(getActivity()).setUserPreferenceData(
                                    SharedPreferencesUtil.getInstance(getActivity()).getUserId(),
                                    firstNameText+" "+lastNameText,
                                    SharedPreferencesUtil.getInstance(getActivity()).getloginType(),
                                    SharedPreferencesUtil.getInstance(getActivity()).getdeviceId(),
                                    emailText,
                                    screenNameText,
                                    houseAddress);

                            IntentAndFragmentService.fragmentdisplay(getActivity(),R.id.main_frame,new MyProfile(),null,false,false);

                        }else {
                            Toast.makeText(getActivity(), "Failed to update user report :"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getActivity(), "Failed to update user report", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateUserMain> call, Throwable t) {
                    progressLoader.DismissProgress();
                    Toast.makeText(getActivity(), "Failed to update user report", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(getActivity(), "Screen name cannot be empty...", Toast.LENGTH_SHORT).show();
        }
    }
}
